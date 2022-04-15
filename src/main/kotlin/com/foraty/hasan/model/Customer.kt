package com.foraty.hasan.model

@kotlinx.serialization.Serializable
data class Customer (
    val id:String,
    val firstName:String,
    val lastName:String,
    val email:String
        )


val customerStorage = mutableListOf<Customer>()