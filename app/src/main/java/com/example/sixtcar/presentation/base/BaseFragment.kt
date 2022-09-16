package com.example.sixtcar.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 *  In order to make it simple to call inflater, a typealias is defined
 */
typealias FragmentInflater<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

/**
 *  Each fragment-layout will be inflated from the BaseFragment.kt in order to prevent from inflating
 *  in each fragment class.
 */
abstract class BaseFragment<VB: ViewBinding>(
    private val inflate: FragmentInflater<VB>
): Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAfterCreate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onAfterViewCreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * after fragment created, this function will be run.
     */
    protected open fun onAfterCreate() {}

    /**
     * after fragment's view created, this function will be run.
     */
    protected open fun onAfterViewCreate() {}
}