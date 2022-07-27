package me.centauri07.jarbapi.command

import com.github.stefan9110.dcm.builder.CommandBuilder
import com.github.stefan9110.dcm.manager.executor.reply.InteractionResponse
import me.centauri07.jarbapi.command.annotation.Command
import kotlin.reflect.KFunction

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

        subCommands.values.forEach {
            commandBuilder.addSubCommand(it.build())
        }

        commandBuilder.setCommandExecutor(this)

        println("parent: ${data.name} | children: ${subCommands.keys}")

        return commandBuilder.build(subCommands.isNotEmpty())
    }
}