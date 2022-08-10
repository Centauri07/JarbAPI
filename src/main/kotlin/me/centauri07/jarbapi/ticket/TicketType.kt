package me.centauri07.jarbapi.ticket

import me.centauri07.jarbapi.database.DataSet
import me.centauri07.jarbapi.ticket.data.TicketData
import java.util.*

/**
 * @author Centauri07
 */
abstract class TicketType<T: Ticket<TD>, TD>(
    val id: String,
    private val ticketsData: DataSet<TicketData<TD>>,
) {

    private val ticketsCache: MutableMap<UUID, T> = mutableMapOf()

    init {
        ticketsData.find().forEach(::insertCache)
    }

    abstract fun fromData(ticketData: TicketData<TD>): T

    fun insertData(ticketData: TicketData<TD>) = ticketsData.insert(ticketData)
    fun deleteData(uuid: UUID) = ticketsData.delete(uuid)
    fun updateData(uuid: UUID) = ticketsCache[uuid]?.let { ticketsData.edit(uuid.toString(), it.data) }
    fun hasData(uuid: UUID): Boolean = ticketsData.find(uuid.toString()) != null

    fun insertCache(ticketData: TicketData<TD>) = ticketsCache.put(UUID.fromString(ticketData.ticketId), fromData(ticketData))
    fun deleteCache(uuid: UUID) = ticketsCache.remove(uuid)
    fun getCache(uuid: UUID): T? = ticketsCache[uuid]
    fun hasCache(uuid: UUID): Boolean = ticketsCache.containsKey(uuid)

}