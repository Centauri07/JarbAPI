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

    fun registerTicketType(id: String, type: TicketType<*, *>) {
        if (ticketTypeRegistry.containsKey(id)) throw TicketTypeAlreadyExistException("Ticket type with id $id already exist")
        if (ticketTypeRegistry.containsValue(type)) throw TicketTypeAlreadyExistException("Ticket type with class $type already exist")

        ticketTypeRegistry[id] = type
    }

    fun <T: Ticket<TD>, TD> createTicket(owner: Member, ticketData: TicketData<TD>): T {

        val channelAction = getCategory(ticketData.type).createTextChannel("${ticketData.type}-${owner.effectiveName}")

        channelAction.setTopic(ticketData.ticketId)

        channelAction.syncPermissionOverrides()

        val channel = channelAction.complete()

        channel.upsertPermissionOverride(owner).grant(Permission.VIEW_CHANNEL).queue()

        val ticketType = getType<T, TD>(ticketData.type)

        val ticket = ticketType.fromData(ticketData)

        ticketsData.insert(ticketData)

        insertCache(ticketData)

        ticket.initialize()

        return ticket

    }

    fun removeData(uuid: UUID) = ticketsData.delete(uuid.toString())

    fun <T: Ticket<TD>, TD> insertCache(ticketData: TicketData<TD>): T {
        val type = getType<T, TD>(ticketData.type)

        val ticket = type.fromData(ticketData)

        ticketCache[UUID.fromString(ticketData.ticketId)] = ticket

        return ticket
    }

    fun <T: Ticket<TD>, TD> getCache(uuid: UUID, clazz: Class<T>): T? {
        if (!hasCache(uuid)) return null

        return clazz.cast(ticketCache[uuid])
    }

    fun hasCache(uuid: UUID): Boolean = ticketCache.containsKey(uuid)

    fun <T: Ticket<TD>, TD> getType(name: String): TicketType<T, TD> {
        return ticketTypeRegistry[name] as? TicketType<T, TD>
            ?: throw TicketTypeNotFoundException("Ticket type with name $name not found!")
    }

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