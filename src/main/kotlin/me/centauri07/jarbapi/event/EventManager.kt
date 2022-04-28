package me.centauri07.jarbapi.event

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.IEventManager
import java.lang.RuntimeException
import kotlin.reflect.KFunction
import kotlin.reflect.full.createType
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.javaMethod

/**
 * @author Centauri07
 */
object EventManager: IEventManager {

    private val executors: MutableList<ListenerExecutor> = mutableListOf()

    override fun register(listener: Any) {

        if (listener !is Listener)
            throw RuntimeException("Class ${listener.javaClass.name} does not implement ${Listener::class.java.name}")

        for (method in listener::class.memberFunctions) {

            if (!method.hasAnnotation<EventListener>()) continue

            if (method.returnType != Unit::class.createType()) continue

            if (method.javaMethod?.parameterCount != 1)
                throw RuntimeException("Method ${method.name} needs to only have one parameter.")

            if (method.javaMethod!!.parameterTypes[0] is GenericEvent)
                throw RuntimeException("Method ${method.name} parameter is not an event type.")

            executors.add(ListenerExecutor(method.parameters[0]::class, listener, method as KFunction<Unit>))

        }

    }

    override fun unregister(listener: Any) {
        if (listener !is Listener)
            throw RuntimeException("Class ${listener.javaClass.name} does not implement ${Listener::class.java.name}")

        executors.removeIf {
            it.parent == listener
        }
    }

    override fun handle(event: GenericEvent) {
        for (executor in executors) {
            if (event::class.java != executor.type) return

            executor.executor.call(executor.type.cast(event))
        }
    }

    override fun getRegisteredListeners(): MutableList<Any> = executors.map(ListenerExecutor::parent).toMutableList()


}