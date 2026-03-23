package com.santimostei.notes.test_challenge.presentation.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimostei.notes.test_challenge.domain.User
import com.santimostei.notes.test_challenge.presentation.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        LoginTextField(
            type = "Username",
            value = username,
            onValueChange = {
                username = it
            }
        )

        Spacer(Modifier.height(24.dp))

        LoginTextField(
            type = "Password",
            value = password,
            onValueChange = {
                password = it
            }
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.login(username, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Action")
        }

        Spacer(Modifier.height(24.dp))

        when {
            uiState.isLoading -> {
                Text(text = "Loading...")
            }

            uiState.error != null -> {
                Text(text = uiState.error.orEmpty())
            }

            uiState.user != null -> {
                uiState.user?.let {
                    onLoginSuccess(it)
                }
            }
        }
    }
}

@Composable
private fun LoginTextField(
    type: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Text(
        text = type
    )

    TextField(
        value = value,
        placeholder = {
            Text("$type...")
        },
        onValueChange = {
            onValueChange(it)
        },
    )
}