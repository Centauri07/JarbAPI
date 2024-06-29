package me.centauri07.jarbapi.form.field

import me.centauri07.jarbapi.form.InputFormatException

object ByteReader : InputReader<Byte>(Byte::class.java) {
    override fun read(input: String): Result<Byte> =
        input.toByteOrNull()?.let { Result.success(it) } ?: Result.failure(InputFormatException("Invalid format."))
}

object ShortReader : InputReader<Short>(Short::class.java) {
    override fun read(input: String): Result<Short> =
        input.toShortOrNull()?.let { Result.success(it) } ?: Result.failure(InputFormatException("Invalid format."))
}

object IntReader : InputReader<Int>(Int::class.java) {
    override fun read(input: String): Result<Int> =
        input.toIntOrNull()?.let { Result.success(it) } ?: Result.failure(InputFormatException("Invalid format."))
}

object LongReader : InputReader<Long>(Long::class.java) {
    override fun read(input: String): Result<Long> =
        input.toLongOrNull()?.let { Result.success(it) } ?: Result.failure(InputFormatException("Invalid format."))
}

object FloatReader : InputReader<Float>(Float::class.java) {
    override fun read(input: String): Result<Float> =
        input.toFloatOrNull()?.let { Result.success(it) } ?: Result.failure(InputFormatException("Invalid format."))
}

object DoubleReader : InputReader<Double>(Double::class.java) {
    override fun read(input: String): Result<Double> =
        input.toDoubleOrNull()?.let { Result.success(it) } ?: Result.failure(InputFormatException("Invalid format."))
}

object BooleanReader : InputReader<Boolean>(Boolean::class.java) {
    override fun read(input: String): Result<Boolean> =
        input.lowercase().toBooleanStrictOrNull()?.let { Result.success(it) }
            ?: Result.failure(InputFormatException("Invalid format."))
}

object CharReader : InputReader<Char>(Char::class.java) {
    override fun read(input: String): Result<Char> =
        if (input.length > 1) Result.failure(InputFormatException("Invalid format.")) else Result.success(input[0])
}

object StringReader : InputReader<String>(String::class.java) {
    override fun read(input: String): Result<String> = Result.success(input)

}