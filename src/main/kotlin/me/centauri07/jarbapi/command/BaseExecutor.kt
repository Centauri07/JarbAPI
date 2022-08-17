package me.centauri07.jarbapi.command

import me.centauri07.dc.api.argument.Argument
import me.centauri07.dc.api.command.Command
import me.centauri07.dc.api.executor.Executor
import me.centauri07.dc.api.response.Response
import me.centauri07.jarbapi.command.annotation.Option
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.valueParameters

/**
 * @author Centauri07
 */
abstract class BaseExecutor(private val parentExecutor: BaseExecutor? = null) : Executor {

    abstract val commandAnnotation: me.centauri07.jarbapi.command.annotation.Command
    abstract var executor: KFunction<Response>?
    abstract val data: BaseExecutorData

    val subCommands: MutableMap<String, SubCommandExecutor> = mutableMapOf()

    override fun onCommand(executor: Member, arguments: List<Argument>, event: Event): Response {

        if (this.executor == null) return Response.of("Cannot find executor of ${data.name}")

        val optionsMap = mutableMapOf<String, Argument?>()

        this.executor!!.valueParameters.filter { it.hasAnnotation<Option>() }.forEach {
            val annotation = it.findAnnotation<Option>()!!

            optionsMap[annotation.name] = arguments.firstOrNull { argument -> argument.name == annotation.name }
        }

        val guild: Guild
        val channel: MessageChannel

        when (event) {
            is SlashCommandInteractionEvent -> {
                guild = event.guild!!
                channel = event.channel
            }
            is MessageReceivedEvent -> {
                guild = event.guild
                channel = event.channel
            }
            else -> throw IllegalArgumentException("base executor doesn't support ${event::class.java}")
        }

        return this.executor!!.call(
            parentExecutor ?: this,
            CommandContext(guild, executor, channel),
            *optionsMap.values.toTypedArray()
        )

    }

    abstract fun build(type: Class<*>): Command

}