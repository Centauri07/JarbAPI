package me.centauri07.jarbapi.ticket.data

import me.centauri07.jarbapi.ticket.member.TicketMember

/**
 * @author Centauri07
 */
data class TicketData(
    val ticketId: Long,
    val channelRef: Long,
    val members: TicketMember
)