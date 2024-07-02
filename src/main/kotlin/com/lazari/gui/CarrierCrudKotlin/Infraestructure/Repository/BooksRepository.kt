package com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository

import com.lazari.gui.CarrierCrudKotlin.Domain.Model.Book
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository


interface BooksRepository : MongoRepository<Book, ObjectId> {
}