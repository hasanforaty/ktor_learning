package com.foraty.hasan

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.foraty.hasan.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        configureSerialization()
        configureTemplating()
        configureRouting()
    }.start(wait = true)
}
