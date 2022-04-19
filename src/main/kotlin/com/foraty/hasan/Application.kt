package com.foraty.hasan

import com.foraty.hasan.dao.DatabaseFactory
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.foraty.hasan.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        DatabaseFactory.init()
        configureSerialization()
        configureTemplating()
        configureRouting()
    }.start(wait = true)
}
