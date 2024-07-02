package com.lazari.gui.CarrierCrudKotlin.Application.Services

import com.lazari.gui.CarrierCrudKotlin.Application.DTO.BookDTO
import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.BookRequest
import com.lazari.gui.CarrierCrudKotlin.Domain.Model.Book
import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository.BooksRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import java.text.SimpleDateFormat
import java.util.*

@Service
class BookService {

    @Autowired
    lateinit var booksRepository: BooksRepository

    @Autowired
    lateinit var gridFSService: GridFSService

    fun getBooks(): List<BookDTO> {
        val books = booksRepository.findAll()
        return books.map { Book ->
            BookDTO(
                id = Book.id,
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

    fun getBookById(id: String): Optional<BookDTO> {
        val bookOptional = booksRepository.findById(ObjectId(id))

        return if (bookOptional.isPresent) {
            val book = bookOptional.get()
            Optional.of(BookDTO(
                id = book.id,
                title = book.title,
                author = book.author,
                publishingCompany = book.publishingCompany,
                year = book.year,
                description = book.description,
                genre = book.genre,
                price = book.price,
                publishDate = book.publishDate,
                coverImageId = book.coverImageId,
                audioBookId = book.audioBookId
            ))
        } else {
            Optional.empty()
        }
    }

    fun deleteBook(id: String): Boolean {
        booksRepository.deleteById(ObjectId(id))
        return true
    }

    fun updateBook(id: String, bookRequest: BookRequest): BookRequest {
        val optionalBook = booksRepository.findById(ObjectId(id))

        if (optionalBook.isPresent) {
            val existingBook = optionalBook.get()

            existingBook.updateBook(bookRequest)

            val updatedBook = booksRepository.save(existingBook)

            return BookRequest(
                title = updatedBook.title,
                author = updatedBook.author,
                publishingCompany = updatedBook.publishingCompany,
                year = updatedBook.year,
                genre = updatedBook.genre,
                price = updatedBook.price,
                description = updatedBook.description,
                publishDate = updatedBook.publishDate,
                coverImageId = updatedBook.coverImageId,
                audioBookId = updatedBook.audioBookId
            )
        } else {
            throw NoSuchElementException("Livro com o ID $id não encontrado")
        }
    }


    fun createBook(bookRequest: BookRequest): BookRequest {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val publishDate: Date = dateFormat.parse(bookRequest.publishDate)

        val newBook = Book(
            title = bookRequest.title,
            author = bookRequest.author,
            publishingCompany = bookRequest.publishingCompany,
            year = bookRequest.year,
            genre = bookRequest.genre,
            price = bookRequest.price,
            description = bookRequest.description,
            publishDate = bookRequest.publishDate,
            coverImageId = bookRequest.coverImageId,
            audioBookId = bookRequest.audioBookId
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
            coverImageId = savedBook.coverImageId,
            audioBookId = savedBook.audioBookId
        )
    }
}
