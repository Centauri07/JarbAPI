package me.centauri07.jarbapi.command.annotation

/**
 * @author Centauri07
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Command(
    val name: String,
    val description: String
)
