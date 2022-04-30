package me.centauri07.jarbapi.command.permission

import net.dv8tion.jda.api.entities.Member

/**
 * @author Centauri07
 */
interface Permission {

    fun hasPermission(member: Member): Boolean

}