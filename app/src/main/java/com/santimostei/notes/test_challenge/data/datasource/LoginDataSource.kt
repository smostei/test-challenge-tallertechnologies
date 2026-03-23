package com.santimostei.notes.test_challenge.data.datasource

import com.santimostei.notes.test_challenge.domain.User

object LoginDataSource {

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