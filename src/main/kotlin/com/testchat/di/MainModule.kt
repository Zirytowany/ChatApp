package com.testchat.di

import com.testchat.data.MessageDataSource
import com.testchat.data.MessageDataSourceImpl
import com.testchat.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module{
    single{
        KMongo.createClient().coroutine.getDatabase("message_db")
    }

    single<MessageDataSource> {
        MessageDataSourceImpl(get())
    }

    single {
        RoomController(get())
    }
}