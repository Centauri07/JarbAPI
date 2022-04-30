package me.centauri07.jarbapi.command

import me.centauri07.jarbapi.command.argument.Argument
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

/**
 * @author Centauri07
 */
abstract class SubCommand(override val name: String, override val arguments: MutableList<Argument>): Command<SlashCommandInteractionEvent> {



}