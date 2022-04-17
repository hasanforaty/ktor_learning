package com.foraty.hasan.plugins

import com.foraty.hasan.route.webSocketRoute
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    routing {
        webSocketRoute()
    }
}
