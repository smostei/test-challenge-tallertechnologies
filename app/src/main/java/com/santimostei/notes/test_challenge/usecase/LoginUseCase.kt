package com.santimostei.notes.test_challenge.usecase

import com.santimostei.notes.test_challenge.data.repositories.LoginRepository
import com.santimostei.notes.test_challenge.domain.LoginBody
import com.santimostei.notes.test_challenge.domain.Result
import com.santimostei.notes.test_challenge.domain.User
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val repository: LoginRepository
) {

    suspend fun invoke(body: LoginBody): Flow<Result<User>> {
        return repository.login(body)
    }
}