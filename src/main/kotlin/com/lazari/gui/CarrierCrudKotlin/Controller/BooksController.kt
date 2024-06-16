package com.lazari.gui.CarrierCrudKotlin.Controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.lazari.gui.CarrierCrudKotlin.Application.Services.BookService
import com.lazari.gui.CarrierCrudKotlin.Application.Services.GridFSService
import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.BookRequest
import com.lazari.gui.CarrierCrudKotlin.Controller.ViewModel.BookViewModel
import com.lazari.gui.CarrierCrudKotlin.Domain.Model.Book
import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository.BooksRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import org.bson.types.ObjectId
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/Book")
class BooksController {

    @Autowired
    lateinit var bookService: BookService

    @Autowired
    lateinit var gridFSService: GridFSService

    @Autowired
    lateinit var booksRepository: BooksRepository

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
                publishDate = bookRequest.publishDate,
                coverImageId =  bookRequest.coverImageId,
                audioBookId = bookRequest.audioBookId
            )
        }
        return if (bookViewModels.isEmpty()) {
            ResponseEntity.badRequest().build()
        } else {
            ResponseEntity.ok(bookViewModels)
        }
    }

//    @GetMapping("/{id}")
//    fun getBookById(@PathVariable("id") id: String): ResponseEntity<BookRequest> {
//        val book = bookService.getBookById(id)
//        return ResponseEntity.ok(book)
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(
        @RequestParam("title") title: String,
        @RequestParam("author") author: String,
        @RequestParam("publishingCompany") publishingCompany: String,
        @RequestParam("year") year: Int,
        @RequestParam("genre") genre: String,
        @RequestParam("price") price: Float,
        @RequestParam("description") description: String,
        @RequestParam("publishDate") publishDate: String,
        @RequestParam("coverImage", required = false) coverImage: MultipartFile?,
        @RequestParam("audioBook", required = false) audioBook: MultipartFile?,
        @RequestParam("coverImageId", required = false) coverImageId: String?,
        @RequestParam("audioBookId", required = false) audioBookId: String?
    ): BookRequest {
        val bookRequest = BookRequest(
            title = title,
            author = author,
            publishingCompany = publishingCompany,
            year = year,
            genre = genre,
            price = price,
            description = description,
            publishDate = publishDate,
            coverImage = coverImage,
            audioBook = audioBook,
            coverImageId = coverImageId,
            audioBookId =  audioBookId,
        )

        return bookService.createBook(bookRequest)
    }

//    @GetMapping("/{id}/cover")
//    fun getCover(@PathVariable id: UUID): ResponseEntity<Any> {
//        val book = booksRepository.findById(id.toString()).orElseThrow { RuntimeException("Book not found") }
//        val coverId = ObjectId(book.coverImageId)
//        val coverBytes = gridFSService.getFile(coverId)
//
//        val headers = HttpHeaders()
//        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg")
//
//        return ResponseEntity(coverBytes, headers, HttpStatus.OK)
//    }
//
//    @PostMapping("/{id}/uploadCover")
//    fun uploadCover(@PathVariable id: UUID, @RequestParam("file") file: MultipartFile): ResponseEntity<Any> {
//        val book = booksRepository.findById(id.toString()).orElseThrow { RuntimeException("Book not found") }
//        val coverId = gridFSService.storeFile(file, file.originalFilename ?: "cover")
//
//        book.coverImageId = coverId.toHexString()
//        booksRepository.save(book)
//
//        return ResponseEntity.status(HttpStatus.CREATED).build()
//    }
}