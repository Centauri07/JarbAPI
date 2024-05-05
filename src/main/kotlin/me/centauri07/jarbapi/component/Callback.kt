package me.centauri07.jarbapi.component

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.SubscribeEvent
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.modals.Modal

open class Callback<C, E> {

    private val queue: MutableMap<C, (E) -> Unit> = mutableMapOf()

    fun queue(component: C, eventAction: (E) -> Unit) {
        queue[component] = eventAction
    }

    fun dequeue(component: C, event: E) {
        queue[component]?.let {
            it(event)
            queue.remove(component)
        }
    }

}


object ButtonCallback: Callback<String, ButtonInteractionEvent>() {

    @SubscribeEvent
    fun on(e: ButtonInteractionEvent) {
        e.button.id?.let { dequeue(it, e) }
    }

}

fun Button.callback(eventAction: (ButtonInteractionEvent) -> Unit): Button {
    ButtonCallback.queue(id!!, eventAction)

    return this
}

object ModalCallback: Callback<String, ModalInteractionEvent>() {

    @SubscribeEvent
    fun on(e: ModalInteractionEvent) {
        dequeue(e.modalId, e)
    }

}

fun Modal.callback(eventAction: (ModalInteractionEvent) -> Unit): Modal {
    ModalCallback.queue(id, eventAction)

    return this
}