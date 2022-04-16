package com.foraty.hasan.route

import com.foraty.hasan.model.Customer
import com.foraty.hasan.model.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.customerRoute(){
    route("customer"){
        get {
            if (customerStorage.isNotEmpty()){
                call.respond(customerStorage)
            }else{
                call.respondText(text = "customer is empty", status = HttpStatusCode.NotFound)
            }
        }
        get("{id?}") {
            val id = call.parameters["id"]?: return@get call.respondText(text = "Missing id", status = HttpStatusCode.BadRequest)
            customerStorage.find { it.id == id }?.let {
                return@get call.respond(it)
            }
            return@get call.respondText(text = "customer not Found", status = HttpStatusCode.NotFound)

        }
        post {
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respondText(text = "customer stored successfully", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = call.parameters["id"]?: return@delete call.respondText(text = "Missing id", status = HttpStatusCode.BadRequest)
            customerStorage.find { it.id == id }?.let {
                customerStorage.remove(it)
                return@delete call.respondText(text = "Customer removed Successfully", status = HttpStatusCode.Accepted)
            }
            return@delete call.respondText(text = "customer not Found", status = HttpStatusCode.NotFound)
        }
    }

}