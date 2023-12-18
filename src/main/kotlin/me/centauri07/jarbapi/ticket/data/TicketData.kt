package me.centauri07.jarbapi.ticket.data

import me.centauri07.jarbapi.ticket.member.TicketMember

/**
 * @author Centauri07
 */
data class TicketData<T>(
    val ticketId: String,
    val type: String,
    val guild: Long,
    var channel: Long,
    val members: MutableList<TicketMember>,
    val ticketTypeData: T
)