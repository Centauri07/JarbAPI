package me.centauri07.jarbapi.command.permission

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member

/**
 * @author Centauri07
 */
class GuildPermission(private val permission: Permission): me.centauri07.jarbapi.command.permission.Permission {

    override fun hasPermission(member: Member): Boolean {
        return member.hasPermission(permission)
    }

}