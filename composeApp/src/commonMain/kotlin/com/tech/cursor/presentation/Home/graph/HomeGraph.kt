package com.tech.cursor.presentation.Home.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.tech.cursor.presentation.Home.MainScreen
import com.tech.cursor.presentation.navigation.Screens

fun NavGraphBuilder.homeGraph(navHostController: NavHostController) {
    composable<Screens.Home> {
        MainScreen( navHostController)
    }
}