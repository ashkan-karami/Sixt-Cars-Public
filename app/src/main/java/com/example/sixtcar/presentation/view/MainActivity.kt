package com.example.sixtcar.presentation.view

import com.example.sixtcar.databinding.ActivityMainBinding
import com.example.sixtcar.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 *  The only activity and the start point of application. All fragments will be attached to this activity.
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onAfterCreate() {

    }
}