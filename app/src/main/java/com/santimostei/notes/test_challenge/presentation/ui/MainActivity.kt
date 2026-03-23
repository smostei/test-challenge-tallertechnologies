package com.santimostei.notes.test_challenge.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.santimostei.notes.test_challenge.di.LoginModule
import com.santimostei.notes.test_challenge.presentation.ui.composables.HomeScreen
import com.santimostei.notes.test_challenge.presentation.ui.composables.LoginScreen
import com.santimostei.notes.test_challenge.presentation.viewmodels.LoginViewModel
import com.santimostei.notes.test_challenge.ui.theme.TestchallengeTheme

class MainActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels {
        LoginModule.provideLoginViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestchallengeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(
                                viewModel = viewModel,
                                onLoginSuccess = {
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("home") {
                            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                            HomeScreen(uiState.user?.username.orEmpty())
                        }
                    }
                }
            }
        }
    }
}