package me.centauri07.jarbapi.form.field

import me.centauri07.jarbapi.form.InputValidationException
import me.centauri07.jarbapi.form.InvalidInputTypeException
import me.centauri07.jarbapi.form.UnknownInputReader

class InputField<T>(tClass: Class<T>, name: String, description: String, required: Boolean = true) :
    FormField<T>(name, description, required) {

    private var reader: InputReader<T> =
        InputReaderRegistry.get(tClass) ?: throw UnknownInputReader(tClass)

    override var value: T? = null

    override fun set(value: Any): Result<FormField<T>> {
        if (value !is String) return Result.failure(InvalidInputTypeException())

        val readValue = reader.read(value).getOrElse { return Result.failure(it) }

        validators.firstOrNull { !it.predicate(readValue) }?.let {
            return Result.failure(InputValidationException(it.failureMessage))
        }

        this.value = readValue

        acknowledged = true

        return Result.success(this)
    }

}

abstract class InputReader<T>(val tClass: Class<T>) {
    abstract fun read(input: String): Result<T>
}

object InputReaderRegistry {
    private val readers = mutableMapOf<Class<*>, InputReader<*>>()

    init {
        add(ByteReader)
        add(ShortReader)
        add(IntReader)
        add(LongReader)
        add(FloatReader)
        add(DoubleReader)
        add(BooleanReader)
        add(CharReader)

        add(StringReader)
    }

    fun add(reader: InputReader<*>) = readers.put(reader.tClass, reader)

    fun <T> get(clazz: Class<T>): InputReader<T>? = readers[clazz] as InputReader<T>?
}