package com.example.sixtcar.presentation.view

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 *  We use Hilt for inject dependencies in order to reduce coupling and boilerplate code.
 *  HiltAndroidApp tells hilt about the application scope for lifecycle handling.
 */
@HiltAndroidApp
class MainApplication: Application() {
}