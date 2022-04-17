package com.foraty.hasan.route

import com.foraty.hasan.model.Connection
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.util.*
import kotlin.collections.LinkedHashSet

fun Route.webSocketRoute(){
    val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
    webSocket("/chat"){
        val thisConnection =Connection(this)
        connections += thisConnection
        try {
            send("You are now connected, number of user online : ${connections.size}")
            for (frame in incoming){
                frame as? Frame.Text ?: continue
                val receivedText  = frame.readText()
                val textWithUserName = "[${thisConnection.name}] : $receivedText"
                connections.forEach {
                    it.serverSession.send(textWithUserName)
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            println("removing ${thisConnection.name}")
            connections -=thisConnection
        }
    }
}
