package com.lazari.gui.CarrierCrudKotlin.Controller

import com.lazari.gui.CarrierCrudKotlin.Application.Services.BookService
import com.lazari.gui.CarrierCrudKotlin.Application.Services.GridFSService
import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.BookRequest
import com.lazari.gui.CarrierCrudKotlin.Controller.ViewModel.BookViewModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.multipart.MultipartFile
import java.text.ParseException
import java.text.SimpleDateFormat

@RestController
@RequestMapping("/Book")
class BooksController {

    @Autowired
    lateinit var bookService: BookService

    @Autowired
    lateinit var gridFSService: GridFSService

    @GetMapping
    fun getAllBooks(): ResponseEntity<List<BookViewModel>> {
        val books = bookService.getBooks()
        val bookViewModels = books.map { bookDTO ->
            BookViewModel(
                id = bookDTO.id,
                title = bookDTO.title,
                author = bookDTO.author,
                publishingCompany = bookDTO.publishingCompany,
                year = bookDTO.year,
                description = bookDTO.description,
                genre = bookDTO.genre,
                price = bookDTO.price,
                publishDate = bookDTO.publishDate,
                coverImageUrl =  "/Book/cover/${bookDTO.coverImageId}",
                audioBookUrl = "/Book/audio/${bookDTO.audioBookId}"
            )
        }
        return if (bookViewModels.isEmpty()) {
            ResponseEntity.badRequest().build()
        } else {
            ResponseEntity.ok(bookViewModels)
        }
    }
    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: String): ResponseEntity<BookViewModel> {
        val bookOptional = bookService.getBookById(id)

        return if (bookOptional.isPresent) {
            val bookDTO = bookOptional.get()
            val bookViewModel = BookViewModel(
                id = bookDTO.id,
                title = bookDTO.title,
                author = bookDTO.author,
                publishingCompany = bookDTO.publishingCompany,
                year = bookDTO.year,
                description = bookDTO.description,
                genre = bookDTO.genre,
                price = bookDTO.price,
                publishDate = bookDTO.publishDate,
                coverImageUrl = "/Book/cover/${bookDTO.coverImageId}",
                audioBookUrl = "/Book/audio/${bookDTO.audioBookId}"
            )
            ResponseEntity.ok(bookViewModel)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/cover/{id}", produces = [MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE])
    fun getCoverImage(@PathVariable id: String): ResponseEntity<ByteArray> {
        val imageBytes = gridFSService.getFile(ObjectId(id))
        return ResponseEntity.ok(imageBytes)
    }

    @GetMapping("/audio/{id}", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun getAudioBook(@PathVariable id: String): ResponseEntity<ByteArray> {
        val audioBytes = gridFSService.getFile(ObjectId(id))
        return ResponseEntity.ok(audioBytes)
    }


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
        @RequestParam("coverImage", required = true) coverImage: MultipartFile?,
        @RequestParam("audioBook", required = false) audioBook: MultipartFile?
    ): BookRequest {
        var coverImageIdFinal: String? = null
        var audioBookIdFinal: String? = null

        val timestamp = System.currentTimeMillis()
        val coverImageFilename = "$title-cover-$timestamp.${coverImage?.contentType?.split("/")?.last()}"
        val audioBookFilename = "$title-audio-$timestamp.${audioBook?.contentType?.split("/")?.last()}"
        try {
            val publishDateFormatted = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(publishDate)
        } catch (e: ParseException) {
            throw IllegalArgumentException("Formato de data inválido. Use o formato yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        }

        if (coverImage != null && !coverImage.isEmpty) {
            val savedCoverImageId = gridFSService.storeFile(coverImage, coverImageFilename)
            coverImageIdFinal = savedCoverImageId.toString()
        }

        if (audioBook != null && !audioBook.isEmpty) {
            val savedAudioBookId = gridFSService.storeFile(audioBook, audioBookFilename)
            audioBookIdFinal = savedAudioBookId.toString()
        }
        val bookRequest = BookRequest(

            title = title,
            author = author,
            publishingCompany = publishingCompany,
            year = year,
            genre = genre,
            price = price,
            description = description,
            publishDate = publishDate,
            coverImageId = coverImageIdFinal,
            audioBookId = audioBookIdFinal
        )

        println("BookRequest: $bookRequest")

        return bookService.createBook(bookRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteBookByID(@PathVariable id: String): Boolean {
        return bookService.deleteBook(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateBook(
        @PathVariable("id") id: String,
        @RequestParam("title") title: String,
        @RequestParam("author") author: String,
        @RequestParam("publishingCompany") publishingCompany: String,
        @RequestParam("year") year: Int,
        @RequestParam("genre") genre: String,
        @RequestParam("price") price: Float,
        @RequestParam("description") description: String,
        @RequestParam("publishDate") publishDate: String,
        @RequestParam("coverImage", required = false) coverImage: MultipartFile?,
        @RequestParam("audioBook", required = false) audioBook: MultipartFile?
    ): BookRequest {
        var coverImageIdFinal: String? = null
        var audioBookIdFinal: String? = null

        val timestamp = System.currentTimeMillis()
        val coverImageFilename = "$title-cover-$timestamp.${coverImage?.contentType?.split("/")?.last()}"
        val audioBookFilename = "$title-audio-$timestamp.${audioBook?.contentType?.split("/")?.last()}"

        try {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(publishDate)
        } catch (e: ParseException) {
            throw IllegalArgumentException("Formato de data inválido. Use o formato yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        }

        if (coverImage != null && !coverImage.isEmpty) {
            val savedCoverImageId = gridFSService.storeFile(coverImage, coverImageFilename)
            coverImageIdFinal = savedCoverImageId.toString()
        }

        if (audioBook != null && !audioBook.isEmpty) {
            val savedAudioBookId = gridFSService.storeFile(audioBook, audioBookFilename)
            audioBookIdFinal = savedAudioBookId.toString()
        }

        val bookRequest = BookRequest(
            title = title,
            author = author,
            publishingCompany = publishingCompany,
            year = year,
            genre = genre,
            price = price,
            description = description,
            publishDate = publishDate,
            coverImageId = coverImageIdFinal,
            audioBookId = audioBookIdFinal
        )
        return bookService.updateBook(id, bookRequest)
    }

}