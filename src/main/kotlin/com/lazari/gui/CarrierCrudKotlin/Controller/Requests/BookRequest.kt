package com.lazari.gui.CarrierCrudKotlin.Controller.Requests

import java.util.*

data class BookRequest(
    val title: String,
    val author: String,
    val publishingCompany: String,
    val year: Int,
    val genre: String,
    val price: Float,
    val description: String,
    val publishDate: Date,
                        )
