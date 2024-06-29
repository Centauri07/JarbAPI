package me.centauri07.jarbapi.form.field

abstract class FormField<T>(
    val name: String,
    val description: String,
    var required: Boolean = true
) {

    val id: String = System.currentTimeMillis().toString()

    abstract var value: T?
        protected set

    open var inquiryMessage: String = "Enter $name"
        protected set

    open var selectMessage: String = "Would you like to enter $name?"
        protected set

    var acknowledged: Boolean = false

    protected val validators: MutableList<Validator<T>> = mutableListOf()

    abstract fun set(value: Any): Result<FormField<*>>

    open fun next(): FormField<*>? = null

    open fun validate(predicate: T.() -> Boolean, failureMessage: String) = validators.add(Validator(predicate, failureMessage))

    class Validator<T>(val predicate: T.() -> Boolean, val failureMessage: String)

}