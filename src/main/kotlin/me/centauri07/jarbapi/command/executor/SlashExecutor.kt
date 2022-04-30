package me.centauri07.jarbapi.command.executor

import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook

/**
 * @author Centauri07
 */
abstract class SlashExecutor: Executor<SlashCommandInteractionEvent> {

    open fun execute(member: Member, args: Array<String>, event: SlashCommandInteractionEvent, hook: InteractionHook) { }

    override fun onCommand(member: Member, arguments: Array<String>, event: SlashCommandInteractionEvent) {

        execute(member, arguments, event, event.hook)

    }

}