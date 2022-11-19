package com.testchat.room

import java.lang.Exception

class MemberAlreadyExistException: Exception(
    "There is already a member with that username in the room"
)