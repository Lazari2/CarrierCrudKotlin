package com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository

import com.lazari.gui.CarrierCrudKotlin.Domain.Model.Book
import org.springframework.data.jpa.repository.JpaRepository


interface BooksRepository : JpaRepository<Book, String> {
}