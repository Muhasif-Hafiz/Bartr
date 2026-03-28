package com.tech.cursor.presentation.Login.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.tech.cursor.presentation.Login.LoginScreen
import com.tech.cursor.presentation.navigation.Screens

fun NavGraphBuilder.loginGraph(navHostController: NavHostController) {
    composable<Screens.Login> {

        LoginScreen( navHostController)
    }
}