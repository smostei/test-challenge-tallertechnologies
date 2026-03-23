package com.santimostei.notes.test_challenge.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.santimostei.notes.test_challenge.data.repositories.LoginRepository
import com.santimostei.notes.test_challenge.data.repositories.LoginRepositoryImpl
import com.santimostei.notes.test_challenge.presentation.viewmodels.LoginViewModel
import com.santimostei.notes.test_challenge.usecase.LoginUseCase

// Custom DI without in order to make challenge instances quickly
object LoginModule {

    private val loginRepository: LoginRepository by lazy {
        LoginRepositoryImpl()
    }

    private val loginUseCase by lazy {
        LoginUseCase(loginRepository)
    }

    fun provideLoginViewModel(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                    return LoginViewModel(loginUseCase) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}