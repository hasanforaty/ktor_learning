package com.foraty.hasan.route

import com.foraty.hasan.model.Article
import com.foraty.hasan.model.articles
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*


fun Route.articleRoute(){
    get("/"){
        call.respondRedirect("articles")
    }
    route("articles"){
        get {
            call.respond(FreeMarkerContent("index.ftl", mapOf("articles" to articles)))
        }
        get("new") {
            call.respond(FreeMarkerContent("new.ftl",null))
        }

        post {
            val formParameter = call.receiveParameters()
            val title = formParameter.getOrFail("title")
            val body  = formParameter.getOrFail("body")
            val newEntry = Article.newEntry(title,body)
            articles.add(newEntry)
            call.respondRedirect("/articles/${newEntry.id}")
        }

        route("{id?}") {
            get {
                val id = call.parameters.getOrFail("id").toInt()
                call.respond(FreeMarkerContent("show.ftl", mapOf("article" to articles.find { id == it.id })))
            }
            get("edit") {
                val id = call.parameters.getOrFail("id").toInt()
                call.respond(FreeMarkerContent("edith.ftl", mapOf("article" to articles.find { id==it.id })))
            }
            post{
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val formParameters = call.receiveParameters()
                when(formParameters.getOrFail("_action")){
                    "update"->{
                        val title = formParameters.getOrFail("title")
                        val body = formParameters.getOrFail("body")
                        articles[id].title = title
                        articles[id].body = body
                        call.respondRedirect("/articles/$id")
                    }
                    "delete"->{
                        articles.removeIf { it.id == id }
                        call.respondRedirect("/articles")
                    }
                }
            }
        }

    }
}