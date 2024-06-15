package com.lazari.gui.CarrierCrudKotlin.Application.Services

import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.BookRequest
import com.lazari.gui.CarrierCrudKotlin.Domain.Model.Book
import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository.BooksRepository
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired

@Service
class BookService {

    @Autowired
    lateinit var booksRepository: BooksRepository

    fun getBooks(): List<BookRequest> {
        val books = booksRepository.findAll()
        return books.map { Book ->
            BookRequest(
                title = Book.title,
                author = Book.author,
                publishingCompany = Book.publishingCompany,
                year = Book.year,
                description = Book.description,
                genre = Book.genre,
                price = Book.price,
                publishDate = Book.publishDate,
            )
        }.sortedBy { it.title }
    }
    fun createBook(bookRequest: BookRequest): BookRequest {
        val newBook = booksRepository.save(
            Book(
                title = bookRequest.title,
                author = bookRequest.author,
                publishingCompany = bookRequest.publishingCompany,
                year = bookRequest.year,
                description = bookRequest.description,
                genre = bookRequest.genre,
                price = bookRequest.price,
                publishDate = bookRequest.publishDate,
            )
        )
        return BookRequest(
            title = newBook.title,
            author = newBook.author,
            publishingCompany = newBook.publishingCompany,
            year = newBook.year,
            description = newBook.description,
            genre = newBook.genre,
            price = newBook.price,
            publishDate = newBook.publishDate,
        )
    }
}