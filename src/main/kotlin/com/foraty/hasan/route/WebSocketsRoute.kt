package com.foraty.hasan.route

import com.foraty.hasan.model.History
import com.foraty.hasan.model.User
import com.foraty.hasan.model.histories
import com.foraty.hasan.model.users
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.util.*

fun Route.webSocketRoute(){
    val connections = Collections.synchronizedMap<User?,DefaultWebSocketServerSession?>(LinkedHashMap())
    webSocket("/chat"){

        send("Login:")
        var username:String?=null
        var password:String?=null
        send("Pls enter User name")
        for (frame in incoming){
            frame as? Frame.Text ?: continue
            username  = frame.readText()
            break
        }
        send("Pls enter password")
        for (frame in incoming){
            frame as? Frame.Text ?: continue
            password  = frame.readText()
            break
        }
        if (username!=null&& password!=null){
            val thisUser = User(userName = username, Password = password)
            users.add(thisUser)
            connections[thisUser] = this
            try {
                send("You are now Connected , online:${connections.size}")
                for (frame in incoming){
                    frame as? Frame.Text ?: continue
                    val receivedText= frame.readText()
                    val sendingText = "[${username}] : $receivedText"
                    val history = histories.find { it.user==thisUser } ?: History(thisUser)
                    history.history.add(receivedText)
                    histories.add(history)
                    if (receivedText=="""/history"""){
                        history.history.forEach {
                            this.send(it)
                        }
                    }else{
                        connections.forEach {
                            it.value.send(sendingText)
                        }
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                println("removing ${thisUser.userName}")
                connections.remove(thisUser)
            }
        }


    }
}
