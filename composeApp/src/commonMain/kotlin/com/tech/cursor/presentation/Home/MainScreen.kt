package com.tech.cursor.presentation.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.tech.cursor.presentation.onboarding.viewmodel.OnboardingViewModel

private val BgDark   = Color(0xFF0A0A0D)
private val Flame    = Color(0xFFFD5558)
private val White25  = Color(0x40FFFFFF)

private enum class Tab { HOME, CONNECT, PROFILE }

@Composable
fun MainScreen(navHostController: NavHostController) {
    var tab by remember { mutableStateOf(Tab.HOME) }
    val homeViewModel = remember { HomeViewModel() }

    Column(Modifier.fillMaxSize().background(BgDark)) {
        Box(Modifier.weight(1f)) {
            when (tab) {
                Tab.HOME    -> HomeScreen(viewModel = homeViewModel)
                Tab.CONNECT -> ConnectScreen()
                Tab.PROFILE -> ProfileScreen()
            }
        }

        // Bottom Nav
        Box(Modifier.fillMaxWidth().background(BgDark)) {
            Box(
                Modifier.fillMaxWidth().height(0.5.dp)
                    .background(Color(0x0FFFFFFF))
                    .align(Alignment.TopStart)
            )
            Row(
                Modifier.fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 28.dp, top = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                NavItem(label = "HOME",    isActive = tab == Tab.HOME,
                    icon = { Text("🏠", fontSize = 20.sp) },
                    onClick = { tab = Tab.HOME })

                NavItem(label = "CONNECT", isActive = tab == Tab.CONNECT,
                    hasBadge = true,
                    icon = { Text("👥", fontSize = 20.sp) },
                    onClick = { tab = Tab.CONNECT })

                NavItem(label = "PROFILE", isActive = tab == Tab.PROFILE,
                    icon = { Text("👤", fontSize = 20.sp) },
                    onClick = { tab = Tab.PROFILE })
            }
        }
    }
}

@Composable
private fun NavItem(
    label: String,
    isActive: Boolean,
    hasBadge: Boolean = false,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Column(
        Modifier.clip(RoundedCornerShape(14.dp))
            .background(if (isActive) Color(0x1AFD5558) else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Box {
            icon()
            if (hasBadge) {
                Box(
                    Modifier.size(8.dp).align(Alignment.TopEnd).offset(x = 3.dp, y = (-3).dp)
                        .clip(CircleShape).background(Flame)
                )
            }
        }
        Text(
            label,
            color         = if (isActive) Flame else White25,
            fontSize      = 9.sp,
            fontWeight    = FontWeight.Bold,
            letterSpacing = 0.8.sp
        )
    }
}

@Composable
private fun PlaceholderScreen(name: String) {
    Box(Modifier.fillMaxSize().background(BgDark), contentAlignment = Alignment.Center) {
        Text(name, color = Color(0x40FFFFFF), fontSize = 18.sp)
    }
}