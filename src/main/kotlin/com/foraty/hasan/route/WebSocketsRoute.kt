package com.foraty.hasan.route

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*

fun Route.webSocketRoute(){
    webSocket("/chat"){
        send("you are connected")
        for (frame in incoming){
            frame as? Frame.Text ?: continue
            val receiveText = frame.readText()
            send("you said $receiveText")
        }
    }
}