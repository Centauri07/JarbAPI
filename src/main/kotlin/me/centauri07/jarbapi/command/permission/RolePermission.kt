package me.centauri07.jarbapi.command.permission

import net.dv8tion.jda.api.entities.Member

/**
 * @author Centauri07
 */
class RolePermission(private val id: Long): Permission {

    override fun hasPermission(member: Member): Boolean {
        if (!member.guild.roles.map { it.idLong }.contains(id)) throw IllegalArgumentException("Role: $id not found in guild: ${member.guild.idLong}")

        return member.roles.map { it.idLong }.contains(id)
    }

}