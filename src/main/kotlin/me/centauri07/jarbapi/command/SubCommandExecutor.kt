package me.centauri07.jarbapi.command

import me.centauri07.dc.api.command.builder.CommandBuilder
import me.centauri07.dc.api.command.option.CommandOption
import me.centauri07.dc.api.response.Response
import me.centauri07.jarbapi.command.annotation.Command
import me.centauri07.jarbapi.command.annotation.Option
import net.dv8tion.jda.api.interactions.commands.OptionType
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.valueParameters

/**
 * @author Centauri07
 */
class SubCommandExecutor(
    parent: BaseExecutor,
    override var executor: KFunction<Response>?,
    override val commandAnnotation: Command,
    override val data: BaseExecutorData
) : BaseExecutor(parent) {

    override fun build(type: Class<*>): me.centauri07.dc.api.command.Command {

        val commandBuilder = CommandBuilder(data.name, data.description, type)

        executor?.valueParameters?.filter { it.hasAnnotation<Option>() }
            ?.map { it.findAnnotation<Option>() }
            ?.map { CommandOption(it!!.type, it.name, it.description, it.required) }
            ?.forEach { commandBuilder.addCommandOption(it) }

        subCommands.values.forEach {
            commandBuilder.addSubCommands(it.build(type))
        }

        commandBuilder.setExecutor(this)

        return commandBuilder.build()
    }
}