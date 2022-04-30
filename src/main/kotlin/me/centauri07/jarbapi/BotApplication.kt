package me.centauri07.jarbapi

import me.centauri07.jarbapi.event.Listener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent

/**
 * @author Centauri07
 */
abstract class BotApplication {

    protected lateinit var jda: JDA

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

    fun registerListener(vararg listeners: Listener) {
        jda.addEventListener(listeners)
    }

    fun enable() {

        jda = JDABuilder.create(token, intents ?: mutableListOf()).build()

        onLoad()

        jda.awaitReady()

        onEnable()

    }

}