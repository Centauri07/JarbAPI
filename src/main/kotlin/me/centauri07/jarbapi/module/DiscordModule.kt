package me.centauri07.jarbapi.module

import me.centauri07.jarbapi.BotApplication
import java.lang.RuntimeException
import kotlin.reflect.KClass
import kotlin.reflect.cast

/**
 * @author Centauri07
 */
abstract class DiscordModule(protected val botApplication: BotApplication) {

    companion object {
        @PublishedApi internal val modules: MutableMap<KClass<*>, DiscordModule> = mutableMapOf()

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

        inline fun <reified T: DiscordModule> get(): T? = modules[T::class]?.let { T::class.cast(it) }
    }

    abstract fun onEnable()
    abstract fun onDisable()

}