package me.centauri07.jarbapi.command

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel

/**
 * @author Centauri07
 */
data class CommandContext(
    val guild: Guild,
    val member: Member,
    val channel: MessageChannel
)