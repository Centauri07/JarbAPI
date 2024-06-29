package me.centauri07.jarbapi.form.listener

import me.centauri07.jarbapi.form.Form
import me.centauri07.jarbapi.form.FormModule
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.hooks.SubscribeEvent

class MessageInputListener(private val formModule: FormModule): ListenerAdapter() {

    @SubscribeEvent
    fun event(e: MessageReceivedEvent) {
        val form = formModule.getTracked(e.author.idLong) ?: return

        if (form.channel.idLong != e.channel.idLong) return

        try { e.message.delete() } catch (_: Exception) { }

        if (e.message.contentRaw == Form.cancelCommand) {
            form.cancel()
            return
        }

        form.process(e.message.contentRaw)
    }

}