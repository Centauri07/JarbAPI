package me.centauri07.jarbapi

import me.centauri07.dc.internal.DiscordCommandAPI
import me.centauri07.jarbapi.command.CommandExecutor
import me.centauri07.jarbapi.module.DiscordModule
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.AnnotatedEventManager
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File

/**
 * @author Centauri07
 */
abstract class BotApplication {

    lateinit var jda: JDA

    private lateinit var discordCommandAPI: DiscordCommandAPI

    lateinit var mainGuild: Guild

    val rootFolder: File = File(".")
    lateinit var dataFolder: File
    lateinit var configFolder: File

    abstract fun onLoad()
    abstract fun onEnable()

    private var token: String? = null
    fun setToken(token: String) { this.token = token }

    protected var intents: MutableList<GatewayIntent>? = null

    /**
     * Replaces the current intent with the given intent
     *
     * @param intents the intents to be replaced with
     */
    fun ofIntents(vararg intents: GatewayIntent) {
        require(intents.isNotEmpty()) {
            "intents cannot be empty!"
        }

        this.intents = mutableListOf(*intents)
    }

    /**
     * Adds intent to the current intent
     *
     * @param intents the intents to be added
     */
    fun addIntent(vararg intents: GatewayIntent) {
        require(intents.isNotEmpty()) {
            "intents cannot be empty!"
        }

        if (this.intents == null) this.intents = mutableListOf()

        this.intents!!.addAll(intents)
    }

    fun registerListener(vararg listeners: Any) {
        jda.addEventListener(*listeners)
    }

    fun registerSlashCommand(command: CommandExecutor) {
        discordCommandAPI.registerCommand(command.build(SlashCommandInteractionEvent::class.java))
    }

    fun registerMessageCommand(command: CommandExecutor) {
        discordCommandAPI.registerCommand(command.build(MessageReceivedEvent::class.java))
    }

    fun addModule(discordModule: DiscordModule) {
        DiscordModule.add(discordModule)
    }

    fun enable() {

        onLoad()

        jda = JDABuilder.createDefault(token).build()

        jda.setEventManager(AnnotatedEventManager())

        discordCommandAPI = DiscordCommandAPI()
        discordCommandAPI.initialize(jda, "!")

        jda.awaitReady()

        onEnable()
    }

}