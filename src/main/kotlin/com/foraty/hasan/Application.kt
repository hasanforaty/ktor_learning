package com.foraty.hasan

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.foraty.hasan.plugins.*

fun main() {
    embeddedServer(Netty, port = 8081) {
        configureSerialization()
        configureSockets()
        configureRouting()
    }.start(wait = true)
}
