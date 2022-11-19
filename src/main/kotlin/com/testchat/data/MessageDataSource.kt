package com.testchat.data

import com.testchat.data.model.Message

interface MessageDataSource {

    suspend fun getAllMessages():List<Message>

    suspend fun insertMessage(message: Message)
}