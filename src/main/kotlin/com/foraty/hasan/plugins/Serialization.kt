package com.foraty.hasan.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun Application.configureSerialization() {
    routing {
        route("api"){
            install(ContentNegotiation) {
                json()
            }
        }

    }

}
