package com.foraty.hasan.route

import com.foraty.hasan.dao.dao
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
            call.respond(FreeMarkerContent("index.ftl", mapOf("articles" to dao.allArticles())))
        }
        get("new") {
            call.respond(FreeMarkerContent("new.ftl",null))
        }

        post {
            val formParameter = call.receiveParameters()
            val title = formParameter.getOrFail("title")
            val body  = formParameter.getOrFail("body")

            val article = dao.addNewArticle(title, body)
            call.respondRedirect("/articles/${article?.id}")
        }

        route("{id?}") {
            get {
                val id = call.parameters.getOrFail("id").toInt()
                call.respond(FreeMarkerContent("show.ftl", mapOf("article" to dao.article(id))))
            }
            get("edit") {
                val id = call.parameters.getOrFail("id").toInt()
                call.respond(FreeMarkerContent("edith.ftl", mapOf("article" to dao.article(id))))
            }
            post{
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val formParameters = call.receiveParameters()
                when(formParameters.getOrFail("_action")){
                    "update"->{
                        val title = formParameters.getOrFail("title")
                        val body = formParameters.getOrFail("body")
                        dao.editArticle(id = id, title = title, body = body)
                        call.respondRedirect("/articles/$id")
                    }
                    "delete"->{
                        dao.deleteArticle(id = id)
                        call.respondRedirect("/articles")
                    }
                }
            }
        }

    }
}