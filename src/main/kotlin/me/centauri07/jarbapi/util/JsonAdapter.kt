package me.centauri07.jarbapi.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * @author Centauri07
 */
class JsonAdapter<T>(var clazz: Class<T>, var defaultValue: T?, name: String, parent: File) {

    var model: T? = defaultValue

    companion object val gson: Gson = GsonBuilder().serializeNulls().setPrettyPrinting().create()

    private val file = File(parent, "$name.json")

    init {
        if (!file.parentFile.exists()) file.parentFile.mkdirs()

        if (!file.exists()) {
            file.createNewFile()
            create()
        }

        load()
    }

    fun create() {
        if (defaultValue == null) model = clazz.getDeclaredConstructor().newInstance()
        save()
    }

    fun load() {
        val reader = FileReader(file)
        model = gson.fromJson(reader, clazz)
        reader.close()
    }

    fun save() {
        val writer = FileWriter(file)
        gson.toJson(model, writer)
        writer.close()
    }
}