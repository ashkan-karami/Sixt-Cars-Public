package com.example.sixtcar

import com.example.sixtcar.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
open class BaseAcceptanceTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()
}