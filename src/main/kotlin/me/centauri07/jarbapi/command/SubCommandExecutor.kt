package me.centauri07.jarbapi.command

import com.github.stefan9110.dcm.builder.CommandBuilder
import com.github.stefan9110.dcm.command.CommandArgument
import com.github.stefan9110.dcm.manager.executor.reply.InteractionResponse
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
    override var executor: KFunction<InteractionResponse>?,
    override val commandAnnotation: Command,
    override val data: BaseExecutorData
) : BaseExecutor(parent) {

    override fun build(): com.github.stefan9110.dcm.command.Command {

        val commandBuilder = CommandBuilder.create(data.name)

        commandBuilder.setDescription(data.description)

        executor?.let {
            commandBuilder.addArguments(
                *executor!!.valueParameters.filter { it.hasAnnotation<Option>() }.map {
                    it.findAnnotation<Option>()
                }.map { CommandArgument(it!!.type, it.name, it.description, it.required, when (it.type) {
                    OptionType.STRING -> it.stringAutocomplete.toList()
                    OptionType.INTEGER -> it.integerAutocomplete.toList()
                    OptionType.NUMBER -> it.doubleAutocomplete.toList()
                    else -> null
                }) }.toTypedArray()
            )
        }

        commandBuilder.setCommandExecutor(this)

        subCommands.values.forEach {
            commandBuilder.addSubCommand(it.build())
        }

        return commandBuilder.build(subCommands.isNotEmpty())
    }
}