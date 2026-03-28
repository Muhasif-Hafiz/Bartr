package com.tech.cursor.presentation.onboarding.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.tech.cursor.presentation.navigation.Screens
import com.tech.cursor.presentation.onboarding.OnboardingScreen


fun NavGraphBuilder.onBoardingGraph(navHostController: NavHostController) {
    composable<Screens.OnBoard> {
        OnboardingScreen(navHostController = navHostController, onComplete = {
            navHostController.navigate(Screens.Home)
        })
    }
}