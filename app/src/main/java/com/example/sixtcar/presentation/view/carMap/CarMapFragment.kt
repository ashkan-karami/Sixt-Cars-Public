package com.example.sixtcar.presentation.view.carMap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.sixtcar.R
import com.example.sixtcar.databinding.FragmentCarMapBinding
import com.example.sixtcar.presentation.base.BaseFragment
import com.example.sixtcar.presentation.util.gone
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CarMapFragment : BaseFragment<FragmentCarMapBinding>(FragmentCarMapBinding::inflate),
    OnMapReadyCallback {

    /**
     *  An instance of ViewModel that will be provided by Hilt.
     */
    private val viewModel by viewModels<CarMapViewModel>()

    private lateinit var mMap: GoogleMap

    /**
     *  onAfterViewCreate will be implemented when inheriting of BaseFragment.
     */
    override fun onAfterViewCreate() {
        observeViewModel()
        initViews()
        viewModel.getCarList()
    }

    /**
     *  onMapReady is a member-function of 'OnMapReadyCallback' and will be called when the map is
     *  added and synced.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap.apply {
            mapType = GoogleMap.MAP_TYPE_NORMAL
            uiSettings.isZoomControlsEnabled = false
        }
        showCarsOnMap()
    }

    /**
     *  All 'flow' in ViewModel will be collected from this function in different scopes.
     */
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.carsStateFlow.filterNotNull().collect {
                hideProgressBar()
                initMap()
            }
        }
        lifecycleScope.launch {
            viewModel.carListError.filterNotNull().collect {
                hideProgressBar()
                showFailure(it)
            }
        }
    }

    private fun initViews(){
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.carListFragment)
        }
    }

    /**
     *  Adding the map to the fragment and synchronizing it.
     */
    private fun initMap() = lifecycleScope.launch {
        val mapFragment = childFragmentManager.findFragmentById(R.id.carMap) as SupportMapFragment
        mapFragment.getMapAsync(this@CarMapFragment)
    }

    private fun hideProgressBar() = lifecycleScope.launch {
        binding.mapProgressBar.gone()
    }

    /**
     *  When the API successfully returns the response, a list of cars will be added to the map in which
     *  a marker will be generated for any car by its lat&lng and its icon will be downloaded as a bitmap.
     *
     *  At the end, when all markers are rendered successfully, the map will be animated to show all markers.
     */
    private fun showCarsOnMap() = CoroutineScope(IO).launch {
        val latLngBuilder = LatLngBounds.Builder()
        viewModel.carsStateFlow.value?.forEach { item ->
            val bitmap: Bitmap = try{
                Glide.with(this@CarMapFragment)
                    .asBitmap()
                    .load(item.icon)
                    .placeholder(R.drawable.ic_car_placeholder)
                    .error(R.drawable.ic_car_placeholder)
                    .submit(100,100)
                    .get()
            }catch (e: Exception){
                e.printStackTrace()
                BitmapFactory.decodeResource(resources, R.drawable.ic_car_placeholder)
            }
            val marker = MarkerOptions()
                .position(LatLng(item.lat,item.lng))
                .title(item.title)
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
            withContext(Main){
                mMap.addMarker(marker)
            }
            latLngBuilder.include(marker.position)
        }
        latLngBuilder.build().also {
            withContext(Main){
                mMap.animateCamera(
                    CameraUpdateFactory
                        .newLatLngBounds(it,0)
                )
            }
        }
    }

    /**
     *  This is primary way to show the failure message. In a real case we use TextView, ImageView and
     *  a button for reloading the page.
     */
    private fun showFailure(message: String) = lifecycleScope.launch {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}