package com.tech.cursor.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tech.cursor.presentation.Home.graph.homeGraph
import com.tech.cursor.presentation.Login.graph.loginGraph
import com.tech.cursor.presentation.onboarding.graph.onBoardingGraph
import com.tech.cursor.presentation.splash.graph.splashGraph

@Composable
fun NavigationHost(
    startDestination: Screens = Screens.Splash,
    navController: NavHostController,
) {
    NavHost(
        modifier = Modifier.background(Color.Transparent),
        navController = navController, startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { fadeOut(animationSpec = tween(300)) },
    ) {
        onBoardingGraph(navController)
        loginGraph(navController)
        homeGraph(navController)
        splashGraph(navController)

    }
}