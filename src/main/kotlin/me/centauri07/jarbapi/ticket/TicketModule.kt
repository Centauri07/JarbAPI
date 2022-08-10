package me.centauri07.jarbapi.ticket

import me.centauri07.jarbapi.BotApplication
import me.centauri07.jarbapi.database.DataSet
import me.centauri07.jarbapi.module.DiscordModule
import me.centauri07.jarbapi.ticket.data.TicketData
import me.centauri07.jarbapi.ticket.exception.TicketTypeAlreadyExistException
import me.centauri07.jarbapi.ticket.exception.TicketTypeNotFoundException
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Category
import net.dv8tion.jda.api.entities.Member
import java.util.*

/**
 * @author Centauri07
 */
class TicketModule(
    botApplication: BotApplication,
    private val moduleData: TicketModuleData,
    private val ticketsData: DataSet<TicketData<*>>,
) : DiscordModule(botApplication, "ticket_system") {

    private val ticketTypeRegistry: MutableMap<String, TicketType<*, *>> = mutableMapOf()

    private val ticketCache: MutableMap<UUID, Ticket<*>> = mutableMapOf()

    override fun onEnable() {
        ticketsData.find().forEach {
            insertCache(it)
        }
    }

    fun getTicketRegistry(): Map<String, TicketType<*, *>> = mutableMapOf()

    fun getTicketCache(): Map<UUID, Ticket<*>> = mutableMapOf()

    fun registerTicketType(type: TicketType<*, *>) {
        if (ticketTypeRegistry.containsKey(type.id)) throw TicketTypeAlreadyExistException("Ticket type with id ${type.id} already exist")
        if (ticketTypeRegistry.containsValue(type)) throw TicketTypeAlreadyExistException("Ticket type with class $type already exist")

        ticketTypeRegistry[type.id] = type
    }

    fun <T: Ticket<TD>, TD> createTicket(owner: Member, type: String, ticketData: TD): T {

        val ticketId = UUID.randomUUID().toString()

        val channel = getCategory(type).createTextChannel("${type}-${owner.effectiveName}")
            .setTopic(ticketId)
            .syncPermissionOverrides()
            .complete()

        val data = TicketData(ticketId, type, channel.idLong, mutableListOf(), ticketData)

        channel.upsertPermissionOverride(owner).grant(Permission.VIEW_CHANNEL).queue()

        val ticket = (getType(data.type) as TicketType<T, TD>).fromData(data)

        ticketsData.insert(data)

        insertCache(data)

        ticket.initialize()

        return ticket

    }

    fun removeData(uuid: UUID) = ticketsData.delete(uuid.toString())

    fun <TD> insertCache(ticketData: TicketData<TD>) {
        val type = getType(ticketData.type) as TicketType<*, TD>

        val ticket = type.fromData(ticketData)

        ticketCache[UUID.fromString(ticketData.ticketId)] = ticket
    }

    fun <T: Ticket<TD>, TD> getCache(uuid: UUID, clazz: Class<T>): T? {
        if (!hasCache(uuid)) return null

        return clazz.cast(ticketCache[uuid])
    }

    fun hasCache(uuid: UUID): Boolean = ticketCache.containsKey(uuid)

    fun getType(name: String): TicketType<*, *> = ticketTypeRegistry[name] ?: throw TicketTypeNotFoundException("Ticket type with name $name not found!")

    private fun getCategory(ticketType: String): Category {

        val categories = moduleData.categories.filter { it.value == ticketType }.keys.mapNotNull {
            botApplication.mainGuild.getCategoryById(it)
        }

        return categories.firstOrNull { it.channels.size < 50 } ?: botApplication.mainGuild
            .createCategory(ticketType + if (categories.isNotEmpty()) categories.count() + 1 else "")
            .complete().also {
                it.upsertPermissionOverride(botApplication.mainGuild.publicRole).deny(Permission.VIEW_CHANNEL).queue()
                moduleData.categories[it.idLong] = ticketType
            }

    }

}