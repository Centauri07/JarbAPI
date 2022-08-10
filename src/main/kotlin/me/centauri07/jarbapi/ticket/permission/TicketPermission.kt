package me.centauri07.jarbapi.ticket.permission

import me.centauri07.jarbapi.ticket.data.TicketData
import net.dv8tion.jda.api.entities.Member

/**
 * @author Centauri07
 */
interface TicketPermission {

    val id: String

    fun hasPermission(ticketData: TicketData<*>, member: Member): Boolean

}