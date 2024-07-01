package me.centauri07.jarbapi.form

import me.centauri07.jarbapi.component.callback
import me.centauri07.jarbapi.form.field.FormField
import me.centauri07.jarbapi.form.field.GroupFormField
import me.centauri07.jarbapi.module.DiscordModule
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.utils.messages.MessageCreateData

class Form(
    val fields: GroupFormField,
    val user: User,
    val channel: MessageChannel
) {

    private val formModule: FormModule = DiscordModule.get(FormModule::class)
        ?: throw NullPointerException("Module ${FormModule::class.java.name} not found.")

    companion object {
        var cancelCommand = "f!cancel"

        var selectMessage: (field: FormField<*>) -> MessageCreateData =
            {
                MessageCreateData.fromEmbeds(EmbedBuilder().setAuthor(it.name).setDescription(it.selectMessage).build())
            }

        var inquiryMessage: (field: FormField<*>) -> MessageCreateData =
            {
                MessageCreateData.fromEmbeds(
                    EmbedBuilder().setTitle(it.name).setAuthor(it.description).setDescription(it.inquiryMessage).build()
                )
            }
        var failMessage: (field: FormField<*>, failureMessage: String) -> MessageCreateData =
            { form, message ->
                MessageCreateData.fromEmbeds(
                    EmbedBuilder().setTitle(form.name).setDescription(message).build()
                )
            }

    }

    var completeAction: (form: Form) -> Unit = { it.channel.sendMessage("Completed!").queue() }
    var cancelAction: (form: Form) -> Unit = { form -> form.channel.sendMessage("Canceled").queue() }

    var inquiryMessage: (field: FormField<*>) -> MessageCreateData = Form.inquiryMessage
    var failMessage: (field: FormField<*>, failureMessage: String) -> MessageCreateData = Form.failMessage

    var idle: Boolean = false

    private fun next(): FormField<*> =
        fields.next() ?: throw NoFieldFoundException()

    fun process(valueFromMessage: Any? = null) {
        if (idle) return

        var nextField = next()

        if (valueFromMessage != null) {
            val exception = nextField.set(valueFromMessage).exceptionOrNull()

            if (exception != null && exception !is InvalidInputTypeException) {
                channel.sendMessage(failMessage(nextField, exception.message!!)).queue()
                return
            } else if (exception == null) {
                nextField = try {
                    next()
                } catch (_: NoFieldFoundException) {
                    complete()
                    return
                }
            }
        }

        if (nextField.required) {
            channel.sendMessage(inquiryMessage(nextField)).queue()
        } else {
            channel.sendMessage(selectMessage(nextField))
                .setActionRow(
                    listOf(
                        Button.success("y-${nextField.id}", "Yes").callback {
                            nextField.required = true
                            process()
                            it.deferReply(true).queue()
                        },
                        Button.danger("n-${nextField.id}", "No").callback {
                            nextField.acknowledged = true
                            process()
                            it.deferReply().queue()
                        }
                    )
                ).queue()
        }
    }

    fun complete() {
        completeAction(this)
        formModule.removeTrack(user.idLong)
    }


    fun cancel() {
        cancelAction(this)
        formModule.removeTrack(user.idLong)
    }

}