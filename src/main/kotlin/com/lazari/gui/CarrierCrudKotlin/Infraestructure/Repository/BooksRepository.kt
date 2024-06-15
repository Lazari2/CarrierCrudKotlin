package com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository

import com.lazari.gui.CarrierCrudKotlin.Domain.Model.Book
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID


interface BooksRepository : MongoRepository<Book, String> {
}