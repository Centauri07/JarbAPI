package me.centauri07.jarbapi

import com.github.stefan9110.dcm.CommandManagerAPI
import me.centauri07.jarbapi.command.CommandExecutor
import me.centauri07.jarbapi.module.DiscordModule
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.hooks.AnnotatedEventManager
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File

/**
 * @author Centauri07
 */
abstract class BotApplication {

    lateinit var jda: JDA

    private lateinit var commandManagerAPI: CommandManagerAPI

    lateinit var mainGuild: Guild

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

    fun registerCommand(command: CommandExecutor) {
        commandManagerAPI.registerCommand(command.build())
    }

    fun addModule(discordModule: DiscordModule) {
        DiscordModule.add(discordModule)
    }

    fun enable() {

        onLoad()

        jda = JDABuilder.createDefault(token).build()

        jda.setEventManager(AnnotatedEventManager())

        commandManagerAPI = CommandManagerAPI.registerAPI(jda, "!")

        jda.awaitReady()

        onEnable()

        commandManagerAPI.setRequiredGuild(mainGuild)

        commandManagerAPI.updateSlashCommands(mainGuild)
    }

}