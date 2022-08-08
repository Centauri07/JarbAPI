package me.centauri07.jarbapi.database

import org.bson.Document

/**
 * @author Centauri07
 */
interface Database {

    fun createDataSet(name: String)

    fun getDataSet(name: String): DataSet<Document>?

    fun <T: Any> getDataSet(name: String, clazz: Class<T>): DataSet<T>?

    fun getOrCreateDataSet(name: String): DataSet<Document> {
        if (!hasDataSet(name)) createDataSet(name)

        return getDataSet(name)!!
    }

    fun <T: Any> getOrCreateDataSet(name: String, clazz: Class<T>): DataSet<T> {
        if (!hasDataSet(name)) createDataSet(name)

        return getDataSet(name, clazz)!!
    }

    fun hasDataSet(name: String): Boolean

    fun deleteDataSet(name: String)

}