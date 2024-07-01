package me.centauri07.jarbapi.form

import me.centauri07.jarbapi.form.field.ButtonChoiceField
import me.centauri07.jarbapi.module.DiscordModule
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.hooks.SubscribeEvent

object FormListener : ListenerAdapter() {

    private val formModule: FormModule = DiscordModule.get(FormModule::class)
        ?: throw NullPointerException("Module ${FormModule::class.java.name} not found.")

    @SubscribeEvent
    fun event(e: MessageReceivedEvent) {
        val form = formModule.getTracked(e.author.idLong) ?: return

        if (form.channel.idLong != e.channel.idLong) return

        try {
            e.message.delete().queue()
        } catch (_: Exception) {
        }

        if (e.message.contentRaw == Form.cancelCommand) {
            form.cancel()
            return
        }

        form.process(e.message.contentRaw)
    }

    @SubscribeEvent
    fun event(e: ButtonInteractionEvent) {
        val form = formModule.getTracked(e.user.idLong) ?: return

        if (form.channel.idLong != e.channel.idLong) return

        if (form.next() !is ButtonChoiceField) return

        e.editComponents(e.message.actionRows.map { it.asDisabled() }).queue {
            form.process(e.button.id!!)

        }
    }

}