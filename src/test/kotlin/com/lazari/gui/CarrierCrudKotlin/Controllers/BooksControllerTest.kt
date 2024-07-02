package com.lazari.gui.CarrierCrudKotlin.Controllers

import com.lazari.gui.CarrierCrudKotlin.Application.DTO.BookDTO
import com.lazari.gui.CarrierCrudKotlin.Application.Services.BookService
import com.lazari.gui.CarrierCrudKotlin.Controller.BooksController
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

@ExtendWith(MockitoExtension::class)
    class BooksControllerTest {

        @Mock
        lateinit var bookService: BookService

        @InjectMocks
        lateinit var booksController: BooksController

        @Test
        fun `getAllBooks should return list of BookViewModel when books are available`() {

            val bookDTO = listOf(
                BookDTO(
                    id = "asdasd",
                    title = "Title1",
                    author = "Author1",
                    publishingCompany = "Company1",
                    year = 2021,
                    description = "Description1",
                    genre = "Genre1",
                    price = 10.0f,
                    publishDate = "adasdas",
                    audioBookId = "12312421fs",
                    coverImageId = "sadasdas"
                )
            )
            Mockito.`when`(bookService.getBooks()).thenReturn(bookDTO)

            val response: ResponseEntity<List<BookViewModel>> = booksController.getAllBooks()

            assertEquals(HttpStatus.OK, response.statusCode)
            assertEquals(1, response.body?.size)
            assertEquals("Title1", response.body?.get(0)?.title)
            assertEquals(2021, response.body?.get(0)?.year)
        }

        @Test
        fun `getAllBooks should return bad request when no books are available`() {
            Mockito.`when`(bookService.getBooks()).thenReturn(emptyList())

            val response: ResponseEntity<List<BookViewModel>> = booksController.getAllBooks()

            assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        }

        @Test
        fun `getBookById should return a book when the book is available`() {

            val bookDTO = listOf(
                BookDTO(
                    id = "asdasd",
                    title = "Title1",
                    author = "Author1",
                    publishingCompany = "Company1",
                    year = 2021,
                    description = "Description1",
                    genre = "Genre1",
                    price = 10.0f,
                    publishDate = "adasdas",
                    audioBookId = "12312421fs",
                    coverImageId = "sadasdas"
                )
            )
            Mockito.`when`(bookService.getBooks()).thenReturn(bookDTO)

            val response: ResponseEntity<List<BookViewModel>> = booksController.getAllBooks()

            assertEquals(HttpStatus.OK, response.statusCode)
            assertEquals(1, response.body?.size)
            assertEquals("Title1", response.body?.get(0)?.title)
            assertEquals(2021, response.body?.get(0)?.year)
        }
    }
