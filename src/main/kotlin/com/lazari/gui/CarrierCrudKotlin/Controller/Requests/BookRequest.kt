package com.lazari.gui.CarrierCrudKotlin.Controller.Requests

import org.springframework.data.annotation.Id
import org.springframework.web.multipart.MultipartFile
import java.util.*

data class BookRequest(
    val title: String,
    val author: String,
    val publishingCompany: String,
    val year: Int,
    val genre: String,
    val price: Float,
    val description: String,
    val publishDate: String,
    val coverImageId: String?,
    val audioBookId: String?
)