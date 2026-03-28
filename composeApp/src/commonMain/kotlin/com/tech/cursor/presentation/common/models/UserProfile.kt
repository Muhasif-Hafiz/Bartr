package com.tech.cursor.presentation.common.models

import com.tech.cursor.presentation.common.dummyProfile

data class UserProfile(
    val id: String,
    val initial: String,
    val name: String,
    val title: String,
    val district: String,
    val memberSince: String,
    val isVerified: Boolean,
    val avatarColor: androidx.compose.ui.graphics.Color,
    val connects: Int,
    val trades: Int,
    val rating: Float,
    val responseRate: Int,
    val offersSkills: List<String>,
    val wantsSkills: List<String>
)

data class ProfileUiState(
    val profile: UserProfile = dummyProfile,
    val notificationsEnabled: Boolean = true,
    val locationEnabled: Boolean = true,
    val profileVisible: Boolean = true,
    val toastMessage: String? = null,
    val toastType: ProfileToastType = ProfileToastType.INFO,
    val showLogoutDialog: Boolean = false
)

enum class ProfileToastType { INFO, SUCCESS, DANGER }