package com.lazari.gui.CarrierCrudKotlin.Controller.ViewModel


data class BookViewModel(
    val id: String?,
    val title: String,
    val author: String,
    val publishingCompany: String,
    val year: Int,
    val genre: String,
    val price: Float,
    val description: String,
    val publishDate: String,
    val coverImageUrl: String?,
    val audioBookUrl: String?
)
