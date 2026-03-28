package com.tech.cursor.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens{


    @Serializable
    data object Splash : Screens()

    @Serializable
    data object Login : Screens()

    @Serializable
    data object OnBoard : Screens()

    @Serializable
    data object Home : Screens()


}

