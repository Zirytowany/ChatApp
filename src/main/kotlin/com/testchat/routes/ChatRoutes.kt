package com.testchat.routes

import com.testchat.room.MemberAlreadyExistException
import com.testchat.room.RoomController
import com.testchat.session.ChatSession
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import java.lang.Exception

fun Route.chatSocket(roomControler:RoomController){
    webSocket("/chat-socket") {
        val session = call.sessions.get<ChatSession>()
        if(session == null){
           close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return@webSocket
        }
        try{
            roomControler.onJoin(
                username = session.username,
                sessionId = session.sessionId,
                socket=this
            )
            incoming.consumeEach { frame->
                if(frame is Frame.Text){
                    roomControler.sendMessage(
                        senderUsername = session.username,
                        message = frame.readText()
                    )
                }
            }
        }catch(e: MemberAlreadyExistException){
            call.respond(Conflict)
        }catch(e: Exception){
            e.printStackTrace()
        }finally{
            roomControler.tryDisconnect(session.username)
        }
    }
}

fun Route.getAllMessages(roomControler: RoomController){
    get("/messages"){
        call.respond(OK, roomControler.getAllMessages())
    }
}