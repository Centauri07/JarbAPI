package me.centauri07.jarbapi.ticket

import me.centauri07.jarbapi.ticket.data.TicketData
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.GuildMessageChannel
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed

/**
 * @author Centauri07
 */
interface Ticket<T> {

    val data: TicketData<T>

    val channel: GuildMessageChannel

    fun sendMessage(message: Message)

    fun sendMessage(message: MessageBuilder.() -> Unit) =
        sendMessage(MessageBuilder().apply { message.invoke(this) }.build())

    fun sendMessageEmbed(messageEmbed: MessageEmbed) = sendMessage { setEmbeds(messageEmbed) }
    fun sendMessageEmbed(messageEmbed: EmbedBuilder.() -> Unit) =
        sendMessageEmbed(EmbedBuilder().apply { messageEmbed.invoke(this) }.build())

    fun initialize()

    fun close()

    fun delete()

}