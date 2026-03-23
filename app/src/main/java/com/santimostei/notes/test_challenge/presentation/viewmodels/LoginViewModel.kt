package com.santimostei.notes.test_challenge.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimostei.notes.test_challenge.domain.LoginBody
import com.santimostei.notes.test_challenge.domain.Result
import com.santimostei.notes.test_challenge.domain.User
import com.santimostei.notes.test_challenge.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val body = LoginBody(username, password)
            loginUseCase.invoke(body).collect { result ->
                _uiState.update { currentState ->
                    when (result) {
                        is Result.Success -> {
                            currentState.copy(
                                user = result.data,
                                isLoading = false,
                                error = null,
                            )
                        }

                        is Result.Loading -> {
                            currentState.copy(isLoading = true)
                        }

                        is Result.Error -> {
                            currentState.copy(
                                user = null,
                                isLoading = false,
                                error = result.throwable.message,
                            )
                        }
                    }
                }
            }
        }
    }

    data class UiState(
        val user: User? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}