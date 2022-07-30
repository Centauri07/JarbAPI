package me.centauri07.jarb.command

import com.github.stefan9110.dcm.manager.executor.reply.InteractionResponse
import me.centauri07.jarbapi.command.CommandContext
import me.centauri07.jarbapi.command.CommandExecutor
import me.centauri07.jarbapi.command.annotation.Option
import me.centauri07.jarbapi.command.annotation.Command
import me.centauri07.jarbapi.command.annotation.Executor
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.interactions.commands.OptionMapping
import net.dv8tion.jda.api.interactions.commands.OptionType

/**
 * @author Centauri07
 */
@Command("punishment", "punish a member")
class TestCommand: CommandExecutor() {

    @Command("temp.ban", "Ban a member temporarily")
    fun tempBan(
        commandContext: CommandContext,
        @Option("member", "member to be banned", OptionType.USER, true) who: OptionMapping
    ): InteractionResponse {

        return InteractionResponse.of("${who.asUser.name} has been banned temporarily for 7 days")

    }

    @Command("perm.ban", "Ban a member permanently")
    fun permBan(
        commandContext: CommandContext,
        @Option("member", "member to be banned", OptionType.USER, true) who: OptionMapping
    ): InteractionResponse {

        return InteractionResponse.of("${who.asUser.name} has been banned permanently")

    }

    @Command("kick", "Kick a member")
    fun kick(
        commandContext: CommandContext,
        @Option("member", "member to be kicked", OptionType.USER, true) who: OptionMapping
    ): InteractionResponse {

        return InteractionResponse.of("${who.asUser.name} has been kicked")

    }

    @Command("temp.mute", "Mute a member temporarily")
    fun tempMute(
        commandContext: CommandContext,
        @Option("member", "member to be banned", OptionType.USER, true) who: OptionMapping
    ): InteractionResponse {

        return InteractionResponse.of("${who.asUser.name} has been muted temporarily for 7 days")

    }

    @Command("perm.mute", "Mute a member permanently")
    fun permMute(
        commandContext: CommandContext,
        @Option("member", "member to be banned", OptionType.USER, true) who: OptionMapping
    ): InteractionResponse {

        return InteractionResponse.of("${who.asUser.name} has been muted permanently")

    }

}