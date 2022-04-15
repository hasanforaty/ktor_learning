package com.foraty.hasan.plugins

import com.foraty.hasan.route.customerRoute
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        //add customer route to server
        customerRoute()
    }
}
