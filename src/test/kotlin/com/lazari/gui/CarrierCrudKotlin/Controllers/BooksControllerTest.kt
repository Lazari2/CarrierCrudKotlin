package com.lazari.gui.CarrierCrudKotlin.Controllers

import com.lazari.gui.CarrierCrudKotlin.Application.Services.BookService
import com.lazari.gui.CarrierCrudKotlin.Controller.BooksController
import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.BookRequest
import com.lazari.gui.CarrierCrudKotlin.Controller.ViewModel.BookViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
    class BooksControllerTest {

        @Mock
        lateinit var bookService: BookService

        @InjectMocks
        lateinit var booksController: BooksController

        @Test
        fun `getAllBooks should return list of BookViewModel when books are available`() {
            // Arrange
            val bookRequests = listOf(
                BookRequest(
                    title = "Title1",
                    author = "Author1",
                    publishingCompany = "Company1",
                    year = 2021,
                    description = "Description1",
                    genre = "Genre1",
                    price = 10.0f,
                    publishDate = Date()
                )
            )
            Mockito.`when`(bookService.getBooks()).thenReturn(bookRequests)

            // Act
            val response: ResponseEntity<List<BookViewModel>> = booksController.getAllBooks()

            // Assert
            assertEquals(HttpStatus.OK, response.statusCode)
            assertEquals(1, response.body?.size)
            assertEquals("Title1", response.body?.get(0)?.title)
        }

        @Test
        fun `getAllBooks should return bad request when no books are available`() {
            // Arrange
            Mockito.`when`(bookService.getBooks()).thenReturn(emptyList())

            // Act
            val response: ResponseEntity<List<BookViewModel>> = booksController.getAllBooks()

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        }
    }
