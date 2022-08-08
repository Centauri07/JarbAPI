package me.centauri07.jarbapi.mongo

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import me.centauri07.jarbapi.database.DataSet
import org.litote.kmongo.deleteOneById
import org.litote.kmongo.findOneById
import org.litote.kmongo.updateOneById

/**
 * @author Centauri07
 */
class MongoDataSet<T>(private val collection: MongoCollection<T>): DataSet<T> {

    override fun insert(data: T) {
        collection.insertOne(data)
    }

    override fun find(id: Any): T? = collection.findOneById(id)

    override fun find(): List<T> = collection.find().toList()

    override fun find(filter: (T) -> Boolean): List<T> = collection.find().filter { filter(it) }


    override fun delete(id: Any) {
        collection.deleteOneById(id)
    }

    override fun delete(filter: (T) -> Boolean) {
        // TODO
    }

    override fun edit(id: Any, replacement: T) {
        collection.updateOneById(id, replacement!!)
    }
}