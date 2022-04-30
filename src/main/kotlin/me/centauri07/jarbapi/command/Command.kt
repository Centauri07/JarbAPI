package me.centauri07.jarbapi.command

import me.centauri07.jarbapi.command.argument.Argument
import me.centauri07.jarbapi.command.executor.Executor
import net.dv8tion.jda.api.events.Event

/**
 * @author Centauri07
 */
interface Command<E: Event> {

    val name: String
    val description: String
    val usage: String

    val executor: Executor<E>

    val arguments: MutableList<Argument>

}