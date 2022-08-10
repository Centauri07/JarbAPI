package me.centauri07.jarbapi.ticket

import me.centauri07.jarbapi.ticket.data.TicketData

/**
 * @author Centauri07
 */
interface TicketType<T: Ticket<TD>, TD> {

    val id: String

    fun fromData(data: TicketData<TD>): T

}