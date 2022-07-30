package me.centauri07.jarbapi.command

import com.github.stefan9110.dcm.command.Command
import com.github.stefan9110.dcm.manager.executor.SlashExecutor
import com.github.stefan9110.dcm.manager.executor.reply.InteractionResponse
import me.centauri07.jarbapi.command.annotation.Option
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.valueParameters

/**
 * @author Centauri07
 */
abstract class BaseExecutor(private val parentExecutor: BaseExecutor? = null) : SlashExecutor() {

    abstract val commandAnnotation: me.centauri07.jarbapi.command.annotation.Command
    abstract var executor: KFunction<InteractionResponse>?
    abstract val data: BaseExecutorData

    val subCommands: MutableMap<String, SubCommandExecutor> = mutableMapOf()

    override fun reply(member: Member, args: Array<out String>, event: SlashCommandInteractionEvent): InteractionResponse {

        if (executor == null) return InteractionResponse.deferInteraction()

        val optionsMap = mutableMapOf<String, Any?>()

        executor!!.valueParameters.filter { it.hasAnnotation<Option>() }.forEach {
            val annotation = it.findAnnotation<Option>()!!

            optionsMap[annotation.name] = event.getOptionsByName(annotation.name).firstOrNull()
        }

        return executor!!.call(
            parentExecutor ?: this,
            CommandContext(event.guild!!, event.member!!, event.channel),
            *optionsMap.values.toTypedArray()
        )

    }

    abstract fun build(): Command

}