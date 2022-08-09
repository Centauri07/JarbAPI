package me.centauri07.jarb

import me.centauri07.jarbapi.BotApplication
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File
import java.util.*

fun main() {
    Jarb().enable()
}

/**
 * @author Centauri07
 */
class Jarb: BotApplication() {

    override fun onLoad() {

        setToken("OTQ0MTYwNTI2NDUwMTY3ODA4.Gy_NMp.iUy-gP0bgcV4TXT6W5SaWPry7jtaQ6cfnkJ8i0")
        ofIntents(*EnumSet.allOf(GatewayIntent::class.java).toTypedArray())

        configFolder = File(".")

    }

    override fun onEnable() {



    }


}