package me.centauri07.jarbapi.form

import me.centauri07.jarbapi.BotApplication
import me.centauri07.jarbapi.form.listener.MessageInputListener
import me.centauri07.jarbapi.module.DiscordModule

class FormModule(botApplication: BotApplication) : DiscordModule(botApplication, "form_system") {

    private val ongoingForms: MutableMap<Long, Form> = mutableMapOf()

    init {
        botApplication.registerListener(
            MessageInputListener(this)
        )
    }

    fun track(form: Form): Boolean {
        if (ongoingForms.containsKey(form.user.idLong)) return false
        ongoingForms[form.user.idLong] = form
        return true
    }

    fun removeTrack(user: Long): Boolean = ongoingForms.remove(user) != null

    fun getTracked(user: Long): Form? = ongoingForms[user]

    fun hasTracked(user: Long): Boolean = ongoingForms.containsKey(user)

}