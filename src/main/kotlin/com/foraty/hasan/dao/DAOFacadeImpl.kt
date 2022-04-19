package com.foraty.hasan.dao

import com.foraty.hasan.model.Article
import com.foraty.hasan.model.Articles
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class DAOFacadeImpl : DAOFacade {
    private fun resultRowToArticle(row:ResultRow):Article = Article(
        id = row[Articles.id],
        title = row[Articles.title],
        body = row[Articles.body]
    )
    override suspend fun allArticles(): List<Article> = DatabaseFactory.dbQuery {
        Articles.selectAll().map(::resultRowToArticle)
    }

    override suspend fun article(id: Int): Article?  = DatabaseFactory.dbQuery {
        Articles
            .select { Articles.id eq id }
            .map (::resultRowToArticle)
            .singleOrNull()
    }

    override suspend fun addNewArticle(title: String, body: String): Article? {
        TODO("Not yet implemented")
    }

    override suspend fun editArticle(id: Int, title: String, body: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteArticle(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}