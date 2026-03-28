package com.tech.cursor

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.tech.cursor.presentation.navigation.NavigationHost
import com.tech.cursor.presentation.onboarding.NameScreen
import com.tech.cursor.presentation.onboarding.OnboardingScreen
import com.tech.cursor.presentation.onboarding.viewmodel.OnboardingViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        var authReady by remember { mutableStateOf(false) }

        NavigationHost(navController = navController)
    }
}