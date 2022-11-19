package com.testchat.plugins

import com.testchat.room.RoomController
import com.testchat.routes.chatSocket
import com.testchat.routes.getAllMessages
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val roomControler by inject<RoomController>()
    install(Routing){
        chatSocket(roomControler)
        getAllMessages(roomControler)
    }
}
