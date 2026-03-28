package com.tech.cursor.presentation.common.models


enum class OnboardingStep(val stepNumber: Int, val title: String) {
    NAME(1, "What's your name?"),
    AGE(2, "How old are you?"),
    ADDRESS(3, "Where do you live?"),
    PHONE(4, "Your phone number"),
    SKILLS_HAVE(5, "Your skills"),
    SKILLS_WANT(6, "Skills you want to learn");

    companion object {
        fun fromStepNumber(number: Int): OnboardingStep? {
            return values().find { it.stepNumber == number }
        }
    }
}


data class Connection(
    val id: String,
    val initial: String,
    val name: String,
    val title: String,
    val district: String,
    val distanceKm: Int,
    val avatarColor: androidx.compose.ui.graphics.Color,
    val status: OnlineStatus,
    val isVerified: Boolean,
    val isNewMatch: Boolean,
    val offersSkill: String,
    val wantsSkill: String,
    val lastMessage: String,
    val lastTime: String,
    val unreadCount: Int,
    val trades: Int,
    val rating: Float,
    val tradeStatus: TradeStatus
)

enum class OnlineStatus { ONLINE, AWAY, OFFLINE }

enum class TradeStatus { ACTIVE, COMPLETED }

enum class ConnectFilter { ALL, ONLINE, NEW_MATCH, ACTIVE_TRADE, COMPLETED }