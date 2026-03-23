package com.santimostei.notes.test_challenge.data

import com.santimostei.notes.test_challenge.domain.User

object Users {

    val userList = listOf(
        User(
            username = "santi",
            password = "1234"
        )
    )

    fun User.isValidUser(): Boolean {
        return userList.contains(this)
    }
}