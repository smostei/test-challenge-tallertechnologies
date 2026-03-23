package com.santimostei.notes.test_challenge.data.repositories

import com.santimostei.notes.test_challenge.data.datasource.LoginDataSource.isValidUser
import com.santimostei.notes.test_challenge.domain.LoginBody
import com.santimostei.notes.test_challenge.domain.Result
import com.santimostei.notes.test_challenge.domain.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl : LoginRepository {

    override suspend fun login(body: LoginBody): Flow<Result<User>> = flow {
        emit(Result.Loading)

        // Simulating network time for response
        delay(2000L)
        val user = User(body.username, body.password)

        if (user.isValidUser()) {
            emit(Result.Success(user))
        } else {
            emit(Result.Error(Throwable("User not found")))
        }
    }.catch {
        emit(Result.Error(it))
    }
}