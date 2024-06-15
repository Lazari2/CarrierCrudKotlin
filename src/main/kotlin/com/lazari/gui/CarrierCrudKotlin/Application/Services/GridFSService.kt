package com.lazari.gui.CarrierCrudKotlin.Application.Services
import com.mongodb.client.gridfs.GridFSBucket
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import java.io.InputStream
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria.where


@Service
class GridFSService {

    @Autowired
    private lateinit var gridFsTemplate: GridFsTemplate

    @Autowired
    private lateinit var gridFSBucket: GridFSBucket

    fun storeFile(file: MultipartFile, filename: String): ObjectId {
        val inputStream: InputStream = file.inputStream
        val metadata = org.bson.Document("contentType", file.contentType)
        return gridFsTemplate.store(inputStream, filename, metadata)
    }

    fun getFile(objectId: ObjectId): ByteArray {
        val downloadStream = gridFSBucket.openDownloadStream(objectId)
        val outputStream = ByteArrayOutputStream()
        downloadStream.transferTo(outputStream)
        return outputStream.toByteArray()
    }

    fun deleteFile(objectId: ObjectId) {
        gridFsTemplate.delete(Query(where("_id").`is`(objectId)))
    }
}