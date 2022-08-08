package me.centauri07.jarbapi.module

import me.centauri07.jarbapi.BotApplication
import java.io.File
import java.lang.RuntimeException
import kotlin.reflect.KClass
import kotlin.reflect.cast

/**
 * @author Centauri07
 */
abstract class DiscordModule(protected val botApplication: BotApplication, moduleName: String) {

    companion object {
        private val modules: MutableMap<KClass<*>, DiscordModule> = mutableMapOf()

        fun add(module: DiscordModule) {
            if (modules.containsKey(module::class)) {
                throw RuntimeException("Module already instantiated")
            }

            modules[module::class] = module

            module.onEnable()
        }

        fun remove(module: DiscordModule) {
            modules[module::class]?.onDisable()

            modules.remove(module::class)
        }

        fun <T: DiscordModule> get(clazz: KClass<T>): T? = modules[clazz]?.let { clazz.cast(it) }
    }

    val configFolder: File = File(botApplication.configFolder, moduleName)

    open fun onEnable() {}
    open fun onDisable() {}

}