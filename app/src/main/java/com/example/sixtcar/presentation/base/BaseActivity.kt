package com.example.sixtcar.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.ViewBinding

/**
 *  A typealias named ActivityInflater is defined to make LayoutInflater easy to use.
 */
typealias ActivityInflater<T> = (LayoutInflater) -> T

/**
 *  THough the application is a single-activity app, the layout will be inflated from the Base-Activity
 *  in order to prevent inflating it in each Activity.
 */
abstract class BaseActivity<VB : ViewBinding>(
    private val inflate: ActivityInflater<VB>
) : AppCompatActivity() {

    private var _binding: VB? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(binding.root)
        onAfterCreate()
    }

    /**
     * after activity created, this function will be run.
     */
    protected open fun onAfterCreate() {}
}