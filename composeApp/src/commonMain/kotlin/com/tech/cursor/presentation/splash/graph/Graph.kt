package com.tech.cursor.presentation.splash.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.tech.cursor.presentation.Home.MainScreen
import com.tech.cursor.presentation.navigation.Screens
import com.tech.cursor.presentation.splash.SplashScreen

fun NavGraphBuilder.splashGraph(navHostController: NavHostController) {
    composable<Screens.Splash> {
        SplashScreen( navHostController)
    }
}