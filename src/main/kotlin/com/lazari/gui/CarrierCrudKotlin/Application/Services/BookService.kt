package com.lazari.gui.CarrierCrudKotlin.Application.Services

import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.BookRequest
import com.lazari.gui.CarrierCrudKotlin.Domain.Model.Book
import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository.BooksRepository
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.bson.types.ObjectId
import org.springframework.web.multipart.MultipartFile
import java.text.SimpleDateFormat
import java.util.*

@Service
class BookService {

    @Autowired
    lateinit var booksRepository: BooksRepository

    @Autowired
    lateinit var gridFSService: GridFSService

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
                coverImageId = Book.coverImageId,
                audioBookId =  Book.audioBookId,
            )
        }.sortedBy { it.title }
    }

//    fun getBookById(id: String): BookRequest {
//        val book = booksRepository.findById(id).orElseThrow { RuntimeException("Book not found") }
//
//        val coverImage = book.coverImageId?.let {
//            gridFSService.getFileById(it)
//        }
//
//        val audioBook = book.audioBookId?.let {
//            gridFSService.getFileById(it)
//        }
//
//        return BookRequest(
//            title = book.title,
//            author = book.author,
//            publishingCompany = book.publishingCompany,
//            year = book.year,
//            genre = book.genre,
//            price = book.price,
//            description = book.description,
//            publishDate = book.publishDate.toString(),
//            coverImage = coverImage,
//            audioBook = audioBook
//        )
//    }

    fun createBook(bookRequest: BookRequest): BookRequest {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val publishDate: Date = dateFormat.parse(bookRequest.publishDate)

        val coverImageId: ObjectId? = bookRequest.coverImage?.let {
            gridFSService.storeFile(it, it.originalFilename ?: "cover")
        }

        val audioBookId: ObjectId? = bookRequest.audioBook?.let {
            gridFSService.storeFile(it, it.originalFilename ?: "audio")
        }

        val newBook = Book(
            title = bookRequest.title,
            author = bookRequest.author,
            publishingCompany = bookRequest.publishingCompany,
            year = bookRequest.year,
            genre = bookRequest.genre,
            price = bookRequest.price,
            description = bookRequest.description,
            publishDate = bookRequest.publishDate,
            coverImageId = coverImageId?.toHexString(),
            audioBookId = audioBookId?.toHexString()
        )

        val savedBook = booksRepository.save(newBook)

        return BookRequest(
            title = savedBook.title,
            author = savedBook.author,
            publishingCompany = savedBook.publishingCompany,
            year = savedBook.year,
            genre = savedBook.genre,
            price = savedBook.price,
            description = savedBook.description,
            publishDate = savedBook.publishDate,
            coverImage = null,
            audioBook = null,
            audioBookId = savedBook.audioBookId,
            coverImageId = savedBook.coverImageId
        )
    }
}
