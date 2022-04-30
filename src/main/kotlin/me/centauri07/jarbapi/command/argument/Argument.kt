package me.centauri07.jarbapi.command.argument

import net.dv8tion.jda.api.interactions.commands.OptionType

/**
 * @author Centauri07
 */
data class Argument(val optionType: OptionType, val name: String, val description: String, val required: Boolean)