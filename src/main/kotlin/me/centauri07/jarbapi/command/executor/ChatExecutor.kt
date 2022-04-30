package me.centauri07.jarbapi.command.executor

import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

/**
 * @author Centauri07
 */
abstract class ChatExecutor: Executor<MessageReceivedEvent> {

    abstract fun execute(member: Member, arguments: Array<String>, event: MessageReceivedEvent)

    override fun onCommand(member: Member, arguments: Array<String>, event: MessageReceivedEvent) = execute(member, arguments, event)

}