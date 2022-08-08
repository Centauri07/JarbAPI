package me.centauri07.jarbapi.ticket.data

import me.centauri07.jarbapi.ticket.member.TicketMember
import me.centauri07.jarbapi.ticket.member.TicketMemberType
import org.bson.codecs.pojo.annotations.BsonId

/**
 * @author Centauri07
 */
data class TicketData(
    @BsonId val ticketId: String,
    val type: String,
    var channelRef: Long,
    val members: MutableList<TicketMember>
) {

    fun getOwner(): TicketMember? = members.firstOrNull { it.userType == TicketMemberType.OWNER }

    fun isOwner(id: Long): Boolean = members.firstOrNull { it.memberId == id }?.userType?.equals(TicketMemberType.OWNER) ?: false

}