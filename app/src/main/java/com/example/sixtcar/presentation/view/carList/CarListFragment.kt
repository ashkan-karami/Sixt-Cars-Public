package com.example.sixtcar.presentation.view.carList

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sixtcar.databinding.FragmentCarListBinding
import com.example.sixtcar.presentation.base.BaseFragment
import com.example.sixtcar.presentation.util.gone
import com.example.sixtcar.presentation.view.carList.adapters.CarListAdapter
import com.example.sixtcar.presentation.view.carList.models.CarItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CarListFragment : BaseFragment<FragmentCarListBinding>(FragmentCarListBinding::inflate) {

    private val viewModel by viewModels<CarListViewModel>()

    override fun onAfterViewCreate() {
        observeViewModel()
        viewModel.getCarList()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.carsStateFlow.filterNotNull().collect {
                hideProgressBar()
                showCarList(it)
            }
        }
        lifecycleScope.launch {
            viewModel.carListError.filterNotNull().collect {
                hideProgressBar()
                showFailure(it)
            }
        }
    }

    private fun hideProgressBar() = lifecycleScope.launchWhenResumed {
        binding.listProgressBar.gone()
    }

    private fun showCarList(cars: List<CarItem>) = lifecycleScope.launch {
        val adapter = CarListAdapter { _, position ->
            Log.i("Sixt info==>", "Selected item position=$position")
        }
        binding.carListRecycler.adapter = adapter
        adapter.setData(cars)
    }

    private fun showFailure(message: String) = lifecycleScope.launch {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}