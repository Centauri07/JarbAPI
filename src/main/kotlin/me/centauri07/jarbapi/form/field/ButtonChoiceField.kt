package me.centauri07.jarbapi.form.field

import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction

class ButtonChoiceField(name: String, description: String, required: Boolean = true, choices: List<Button>) :
    ChoiceFormField<String>(name, description, required, choices.map { it.id!! }) {

    override var messageModifier: (MessageCreateAction) -> Unit = { it.setActionRow(choices) }

}