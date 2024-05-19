package com.lazari.gui.CarrierCrudKotlin.Domain.Model

import jakarta.persistence.*
import java.util.UUID

@Entity(name = "book")
@Table(name = "book")
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),

    title: String,
    author: String,
    publishingCompany: String,
    year: Int,
    genre: String,
    price: Float,
    description: String,
    publishDate:java.util.Date
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
    var publishDate: java.util.Date = publishDate
        private set
}
