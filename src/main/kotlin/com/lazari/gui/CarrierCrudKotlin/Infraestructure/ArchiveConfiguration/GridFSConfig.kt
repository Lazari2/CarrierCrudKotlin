package com.lazari.gui.CarrierCrudKotlin.Infraestructure.ArchiveConfiguration

import com.mongodb.client.gridfs.GridFSBuckets
import com.mongodb.client.gridfs.GridFSBucket
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory

@Configuration
class GridFSConfig {

    @Bean
    fun gridFSBucket(mongoDbFactory: MongoDatabaseFactory): GridFSBucket {
        val db = mongoDbFactory.mongoDatabase
        return GridFSBuckets.create(db)
    }
}