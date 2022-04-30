package me.centauri07.jarbapi.command.executor

import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.Event

/**
 * @author Centauri07
 */
interface Executor<E: Event> {

    fun onCommand(member: Member, arguments: Array<String>, event: E)

}