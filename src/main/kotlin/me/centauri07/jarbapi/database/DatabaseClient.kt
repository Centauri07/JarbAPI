package me.centauri07.jarbapi.database

/**
 * @author Centauri07
 */
interface DatabaseClient<T> {

    fun createClient(connectionString: String? = null)

    fun getDatabase(name: String): Database?

    fun hasDatabase(name: String): Boolean

    fun deleteDatabase(name: String)

}