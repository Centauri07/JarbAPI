package me.centauri07.jarbapi.command.annotation

import net.dv8tion.jda.api.interactions.commands.OptionType

/**
 * @author Centauri07
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Option(
    val name: String,
    val description: String,
    val type: OptionType,
    val required: Boolean,
    val stringAutocomplete: Array<String> = [],
    val integerAutocomplete: LongArray = [],
    val doubleAutocomplete: DoubleArray = []
)
