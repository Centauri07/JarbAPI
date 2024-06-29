package me.centauri07.jarbapi.form

class NoFieldFoundException: Exception("There are no fields left.")
class UnknownInputReader(tClass: Class<*>): Exception("Reader for class ${tClass.name} is not recognized.")
class InputFormatException(info: String): Exception(info)
class InputValidationException(info: String): Exception(info)
class InvalidInputTypeException: Exception("Input doesn't correspond to the field type.")