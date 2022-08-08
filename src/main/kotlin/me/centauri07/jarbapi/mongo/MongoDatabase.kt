package me.centauri07.jarbapi.mongo

import com.mongodb.client.MongoDatabase
import me.centauri07.jarbapi.database.DataSet
import me.centauri07.jarbapi.database.Database
import org.bson.Document
import org.litote.kmongo.getCollection
import org.litote.kmongo.getCollectionOfName

/**
 * @author Centauri07
 */
class MongoDatabase(private val database: MongoDatabase): Database {

    override fun createDataSet(name: String) = database.createCollection(name)

    override fun getDataSet(name: String): DataSet<Document> = MongoDataSet(database.getCollection(name))

    override fun <T: Any> getDataSet(name: String, clazz: Class<T>): DataSet<T> = MongoDataSet(database.getCollection(name, clazz))

    override fun hasDataSet(name: String): Boolean = database.listCollectionNames().contains(name)

    override fun deleteDataSet(name: String) {
        if (hasDataSet(name)) database.getCollection(name).drop()
    }

}