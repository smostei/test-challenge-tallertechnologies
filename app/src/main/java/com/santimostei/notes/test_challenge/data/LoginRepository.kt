package com.santimostei.notes.test_challenge.data

import com.santimostei.notes.test_challenge.domain.LoginBody
import com.santimostei.notes.test_challenge.domain.Result
import com.santimostei.notes.test_challenge.domain.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(
        body: LoginBody
    ): Flow<Result<User>>
}