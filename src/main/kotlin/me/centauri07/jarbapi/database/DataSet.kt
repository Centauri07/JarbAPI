package me.centauri07.jarbapi.database

/**
 * @author Centauri07
 */
interface DataSet<T> {

    fun insert(data: T)

    fun find(id: Any): T?

    fun find(): List<T>

    fun find(filter: (T) -> Boolean): List<T>

    fun delete(id: Any)

    fun delete(filter: (T) -> Boolean)

    fun edit(id: Any, replacement: T)

    fun edit(filter: (T) -> Boolean, replacement: T)

}