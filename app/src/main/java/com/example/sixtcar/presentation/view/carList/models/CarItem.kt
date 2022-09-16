package com.example.sixtcar.presentation.view.carList.models

/**
 *  Data class known by car adapter.
 */
data class CarItem(
    val id: String,
    val name: String,
    val modelName: String,
    val group: String,
    val color: String,
    val series: String,
    val icon: String?
)
