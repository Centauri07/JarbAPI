package me.centauri07.jarbapi.ticket

import me.centauri07.jarbapi.ticket.data.TicketData
import me.centauri07.jarbapi.ticket.member.TicketMemberType
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder
import net.dv8tion.jda.api.utils.messages.MessageCreateData

/**
 * @author Centauri07
 */
interface Ticket<T> {

    val channel: MessageChannel

    val ticketType: TicketType<*, T>

    val data: TicketData<T>

    fun initialize(owner: Member)
    fun close(executor: Member, reason: String)
    fun delete(executor: Member, reason: String)

    fun addMember(memberId: Long, ticketMemberType: TicketMemberType, permission: List<String>)
    fun removeMember(memberId: Long, ticketMemberType: TicketMemberType)
    fun isMember(memberId: Long, ticketMemberType: TicketMemberType): Boolean

    fun hasPermission(id: Long, ticketMemberType: TicketMemberType, permission: String): Boolean
    fun grant(memberId: Long, ticketMemberType: TicketMemberType, permission: String)
    fun disallow(memberId: Long, ticketMemberType: TicketMemberType, permission: String)

    fun sendMessage(message: MessageCreateData)

    fun sendMessage(message: MessageCreateBuilder.() -> Unit) =
        sendMessage(MessageCreateBuilder().apply { message.invoke(this) }.build())

}