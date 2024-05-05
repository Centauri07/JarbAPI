package me.centauri07.jarbapi.component

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.SubscribeEvent
import net.dv8tion.jda.api.interactions.components.buttons.Button

interface Callback<C, E> {

    fun queue(component: C, eventAction: (E) -> Unit)

    fun dequeue(component: C, event: E)

}


object ButtonCallback: Callback<String, ButtonInteractionEvent> {

    private val queue: MutableMap<String, (ButtonInteractionEvent) -> Unit> = mutableMapOf()

    override fun queue(component: String, eventAction: (ButtonInteractionEvent) -> Unit) {
        queue[component] = eventAction
    }

    override fun dequeue(component: String, event: ButtonInteractionEvent) {
        queue[component]?.let {
            it(event)

            queue.remove(component)
        }
    }

    @SubscribeEvent
    fun on(e: ButtonInteractionEvent) {
        e.button.id?.let { dequeue(it, e) }
    }
}

fun Button.callback(eventAction: (ButtonInteractionEvent) -> Unit): Button {
    ButtonCallback.queue(id!!, eventAction)

    return this
}