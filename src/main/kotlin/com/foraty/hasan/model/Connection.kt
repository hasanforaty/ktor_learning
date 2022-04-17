package com.foraty.hasan.model

import io.ktor.server.websocket.*
import java.util.concurrent.atomic.AtomicInteger

class Connection(val serverSession:DefaultWebSocketServerSession) {
    companion object{
        val currentNumber=AtomicInteger(0)
    }
    val name = "user${currentNumber.getAndIncrement()}"
}