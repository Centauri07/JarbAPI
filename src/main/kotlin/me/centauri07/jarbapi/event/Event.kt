package me.centauri07.jarbapi.event

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

/**
 * @author Centauri07
 */

annotation class EventListener
interface Listener
data class ListenerExecutor(val type: KClass<*>, val parent: Any, val executor: KFunction<Unit>)