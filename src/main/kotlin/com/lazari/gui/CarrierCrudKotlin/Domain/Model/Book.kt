package com.lazari.gui.CarrierCrudKotlin.Domain.Model

import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.BookRequest
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "book")
class Book(
    @Id
    val id: String? = null,

    title: String,
    author: String,
    publishingCompany: String,
    year: Int,
    genre: String,
    price: Float,
    description: String,
    publishDate:String,
    coverImageId: String?,
    audioBookId: String?,
    ) {

    var title: String = title
        private set
    var author: String = author
        private set
    var publishingCompany: String = publishingCompany
        private set
    var year: Int = year
        private set
    var genre: String = genre
        private set
    var price: Float = price
        private set
    var description: String = description
        private set
    var publishDate: String = publishDate
        private set
    var coverImageId: String? = coverImageId

    var audioBookId: String? = audioBookId

    fun updateBook(newBookRequest: BookRequest) {
        title = newBookRequest.title
        author = newBookRequest.author
        publishingCompany = newBookRequest.publishingCompany
        year = newBookRequest.year
        genre = newBookRequest.genre
        price = newBookRequest.price
        description = newBookRequest.description
        publishDate = newBookRequest.publishDate
        coverImageId = newBookRequest.coverImageId
        audioBookId = newBookRequest.audioBookId
    }
}
