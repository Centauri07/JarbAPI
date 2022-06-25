package me.centauri07.jarbapi.event

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.IEventManager
import java.lang.RuntimeException
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.*

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

            if (method.valueParameters.count() != 1)
                throw RuntimeException("Method ${method.name} needs to only have one parameter.")

            val parameter = method.valueParameters.first().type.classifier as KClass<*>

            if (parameter.isSuperclassOf(GenericEvent::class))
                throw RuntimeException("Method ${method.name} parameter is not an event type.")

            executors.add(ListenerExecutor(parameter, listener, method as KFunction<Unit>))

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

            if (executor.type == event::class) {
                executor.executor.call(executor.parent, executor.type.cast(event))
            }

        }
    }

    override fun getRegisteredListeners(): MutableList<Any> = executors.map(ListenerExecutor::parent).toMutableList()


}