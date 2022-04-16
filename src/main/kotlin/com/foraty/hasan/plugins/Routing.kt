package com.foraty.hasan.plugins

import com.foraty.hasan.route.customerRoute
import com.foraty.hasan.route.getOrderRoute
import com.foraty.hasan.route.listOrderRoute
import com.foraty.hasan.route.totalizeOrderRoute
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        //add customer route to server
        customerRoute()
        listOrderRoute()
        getOrderRoute()
        totalizeOrderRoute()
    }
}
