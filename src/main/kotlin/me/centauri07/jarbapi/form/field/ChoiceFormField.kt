package me.centauri07.jarbapi.form.field

import me.centauri07.jarbapi.form.InputValidationException
import me.centauri07.jarbapi.form.InvalidInputException
import me.centauri07.jarbapi.form.InvalidInputTypeException

open class ChoiceFormField<T>(name: String, description: String, required: Boolean = true, val choices: List<T>) :
    FormField<T>(name, description, required) {

    override var value: T? = null

    override var inquiryPrompt: String = "Choose from the following options"

    override fun set(value: Any): Result<FormField<*>> {
        choices.forEach { println(it) }
        println(value)

        try {
            value as T
        } catch (e: ClassCastException) {
            return Result.failure(InvalidInputTypeException())
        }

        if (!choices.contains(value)) return Result.failure(InvalidInputException("Chosen value isn't in the choices."))

        validators.firstOrNull { !it.predicate(value) }?.let {
            return Result.failure(InputValidationException(it.failureMessage))
        }

        this.value = value
        acknowledged = true

        return Result.success(this)
    }

}