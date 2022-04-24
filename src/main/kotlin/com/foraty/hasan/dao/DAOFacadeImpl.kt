package com.foraty.hasan.dao

import com.foraty.hasan.model.Article
import com.foraty.hasan.model.Articles
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*

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

    override suspend fun addNewArticle(title: String, body: String): Article? =DatabaseFactory.dbQuery {
        val insertStatement = Articles
            .insert {
                it[Articles.body] = body
                it[Articles.title] = title
            }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToArticle)
    }

    override suspend fun editArticle(id: Int, title: String, body: String): Boolean =DatabaseFactory.dbQuery {
        Articles.update({
            Articles.id eq id
        }) {
            it[Articles.title] = title
            it[Articles.body] = body
        } > 0
    }

    override suspend fun deleteArticle(id: Int): Boolean = DatabaseFactory.dbQuery {
        Articles.deleteWhere {
            Articles.id eq id
        } > 0
    }

}
val dao:DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if (allArticles().isEmpty()){
            addNewArticle("The drive to develop!", "...it's what keeps me going.")
        }
    }
}