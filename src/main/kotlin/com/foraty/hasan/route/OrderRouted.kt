package com.foraty.hasan.route

import com.foraty.hasan.model.orderStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.listOrderRoute(){
    route("/order"){
        get {
            if (orderStorage.isNotEmpty()){
                call.respond(orderStorage)
            }else{
                call.respondText(text = "order is empty", status = HttpStatusCode.NotFound)
            }
        }
    }
}


fun Route.getOrderRoute(){
    route("order/{id?}"){
        get {
            val id = call.parameters["id"] ?: return@get call.respondText("Missing id", status = HttpStatusCode.BadRequest)
            val order = orderStorage.find { it.number == id } ?: return@get call.respondText(
                "Not Found",
                status = HttpStatusCode.NotFound
            )
            call.respond(order)
        }

    }
}

fun Route.totalizeOrderRoute() {
    get("/order/{id?}/total") {
        val id = call.parameters["id"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
        val order = orderStorage.find { it.number == id } ?: return@get call.respondText(
            "Not Found",
            status = HttpStatusCode.NotFound
        )
        val total = order.contents.sumOf { it.price * it.amount }
        call.respond(total)
    }
}