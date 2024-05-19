package com.lazari.gui.CarrierCrudKotlin.Controller

import com.lazari.gui.CarrierCrudKotlin.Application.Services.BookService
import com.lazari.gui.CarrierCrudKotlin.Controller.ViewModel.BookViewModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/Book")
class BooksController {

    @Autowired
    lateinit var bookService: BookService

    @GetMapping
    fun getAllBooks(): ResponseEntity<List<BookViewModel>> {
        val books = bookService.getBooks()
        val bookViewModels = books.map { bookRequest ->
            BookViewModel(
                title = bookRequest.title,
                author = bookRequest.author,
                publishingCompany = bookRequest.publishingCompany,
                year = bookRequest.year,
                description = bookRequest.description,
                genre = bookRequest.genre,
                price = bookRequest.price,
                publishDate = bookRequest.publishDate
            )
        }
        return if (bookViewModels.isEmpty()) {
            ResponseEntity.badRequest().build()
        } else {
            ResponseEntity.ok(bookViewModels)
        }
    }
}