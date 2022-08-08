package me.centauri07.jarbapi.database

import org.bson.Document

/**
 * @author Centauri07
 */
interface Database {

    fun createDataSet(name: String)

    fun getDataSet(name: String): DataSet<Document>?

    fun <T: Any> getDataSet(name: String, clazz: Class<T>): DataSet<T>

    fun hasDataSet(name: String): Boolean

    fun deleteDataSet(name: String)

}