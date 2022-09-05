package me.centauri07.jarbapi.ticket

import me.centauri07.jarbapi.ticket.data.TicketData
import me.centauri07.jarbapi.ticket.member.TicketMemberType
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.GuildMessageChannel
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed

/**
 * @author Centauri07
 */
interface Ticket<T> {

    val channel: GuildMessageChannel

    val ticketType: TicketType<*, T>

    val data: TicketData<T>

    fun sendMessage(message: Message)

    fun sendMessage(message: MessageBuilder.() -> Unit) =
        sendMessage(MessageBuilder().apply { message.invoke(this) }.build())

    fun sendMessageEmbed(messageEmbed: MessageEmbed) = sendMessage { setEmbeds(messageEmbed) }
    fun sendMessageEmbed(messageEmbed: EmbedBuilder.() -> Unit) =
        sendMessageEmbed(EmbedBuilder().apply { messageEmbed.invoke(this) }.build())

    fun addMember(memberId: Long, ticketMemberType: TicketMemberType)
    fun removeMember(memberId: Long)

    fun initialize(owner: Member)
    fun close(executor: Member, reason: String)
    fun delete(executor: Member, reason: String)

}