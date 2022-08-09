package me.centauri07.jarbapi.ticket

import me.centauri07.jarbapi.ticket.data.TicketData

/**
 * @author Centauri07
 */
interface TicketType<T: Ticket<TD>, TD> {

    fun fromData(data: TicketData<TD>): T

}