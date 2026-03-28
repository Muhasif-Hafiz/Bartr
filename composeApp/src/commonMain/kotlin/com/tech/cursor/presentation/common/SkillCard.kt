package com.tech.cursor.presentation.common

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class SkillCard(
    val id: String,
    val initial: String,
    val name: String,
    val age: Int,
    val title: String,
    val location: String,
    val district: String,
    val distanceKm: Int,
    val bio: String,
    val offers: List<String>,
    val wants: List<String>,
    val matchPercent: Int,
    val trades: Int,
    val rating: Float,
    val responseRate: Int,
    val experienceYears: Int,
    val isVerified: Boolean,
    val cardGradient: Brush,
    val avatarColor: Color
)