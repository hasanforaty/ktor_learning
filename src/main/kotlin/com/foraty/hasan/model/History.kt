package com.foraty.hasan.model

import java.util.LinkedList
import java.util.UUID

data class History (
    val user:User,
        ){
        val history: LinkedList<String> = LinkedList()
}