package me.centauri07.jarbapi.mongo

import com.mongodb.client.MongoClient
import me.centauri07.jarbapi.database.Database
import me.centauri07.jarbapi.database.DatabaseClient
import org.litote.kmongo.KMongo

/**
 * @author Centauri07
 */
class MongoDatabaseClient: DatabaseClient<MongoClient> {

    private lateinit var client: MongoClient

    override fun createClient(connectionString: String?) {
        client = if (connectionString != null) KMongo.createClient(connectionString) else KMongo.createClient()
    }

    override fun getDatabase(name: String): Database = MongoDatabase(client.getDatabase(name))

    override fun hasDatabase(name: String): Boolean = client.listDatabaseNames().contains(name)

    override fun deleteDatabase(name: String) {
        if (hasDatabase(name)) client.getDatabase(name).drop()
    }

}