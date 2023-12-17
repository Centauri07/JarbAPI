package me.centauri07.jarbapi.ticket

import me.centauri07.jarbapi.ticket.data.TicketData
import java.util.*

/**
 * @author Centauri07
 */
abstract class TicketType<T: Ticket<TD>, TD>(
    val id: String,
) {
    private val ticketsCache: MutableMap<UUID, T> = mutableMapOf()

    abstract fun fromData(ticketData: TicketData<TD>): T

    abstract fun insertData(ticketData: TicketData<TD>)
    abstract fun deleteData(uuid: UUID)
    abstract fun updateData(uuid: UUID)
    abstract fun hasData(uuid: UUID): Boolean

    fun insertCache(ticketData: TicketData<TD>) = ticketsCache.put(UUID.fromString(ticketData.ticketId), fromData(ticketData))
    fun deleteCache(uuid: UUID) = ticketsCache.remove(uuid)
    fun getCache(uuid: UUID): T? = ticketsCache[uuid]
    fun hasCache(uuid: UUID): Boolean = ticketsCache.containsKey(uuid)

}