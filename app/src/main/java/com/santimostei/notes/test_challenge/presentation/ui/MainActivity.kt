package com.santimostei.notes.test_challenge.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimostei.notes.test_challenge.data.LoginRepositoryImpl
import com.santimostei.notes.test_challenge.presentation.viewmodels.MainViewModel
import com.santimostei.notes.test_challenge.ui.theme.TestchallengeTheme
import com.santimostei.notes.test_challenge.usecase.LoginUseCase

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestchallengeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    LoginScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(it)
                    )
                }
            }
        }
    }
}

@Composable
private fun LoginScreen(
    viewModel: MainViewModel,
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
            onValueChange = {
                username = it
            }
        )

        Spacer(Modifier.height(24.dp))

        LoginTextField(
            type = "Password",
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
                // Here we need to navigate to welcome screen
                // (can be another composable like LoginScreen in the same ui state or navigate to another screen)
            }
        }
    }
}

@Composable
private fun LoginTextField(
    type: String,
    onValueChange: (String) -> Unit,
) {
    Text(
        text = type
    )

    TextField(
        value = "",
        placeholder = {
            Text("$type...")
        },
        onValueChange = {
            onValueChange(it)
        },
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    TestchallengeTheme {
        LoginScreen(MainViewModel(LoginUseCase(LoginRepositoryImpl())))
    }
}