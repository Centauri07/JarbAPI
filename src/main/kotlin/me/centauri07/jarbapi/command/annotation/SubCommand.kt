package me.centauri07.jarbapi.command.annotation

/**
 * @author Centauri07
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class SubCommand(
    val name: String,
    val description: String
)