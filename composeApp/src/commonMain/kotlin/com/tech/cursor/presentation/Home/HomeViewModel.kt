package com.tech.cursor.presentation.Home

import com.tech.cursor.presentation.common.SkillCard
import com.tech.cursor.presentation.common.kashmirCards


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeUiState(
    val cards: List<SkillCard> = kashmirCards,
    val matchedCard: SkillCard? = null,
    val toastMessage: String? = null,
    val toastType: ToastType = ToastType.PASS
)

enum class ToastType { PASS, CONNECT }

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // Called when user swipes left or taps ✕
    fun onPass() {
        val current = _uiState.value.cards.firstOrNull() ?: return
        _uiState.update { state ->
            state.copy(
                cards = state.cards.drop(1),
                toastMessage = "Passed on ${current.name}",
                toastType = ToastType.PASS
            )
        }
    }

    // Called when user swipes right or taps ♥
    fun onConnect() {
        val current = _uiState.value.cards.firstOrNull() ?: return
        val isMatch = current.matchPercent > 80
        _uiState.update { state ->
            state.copy(
                cards = state.cards.drop(1),
                matchedCard = if (isMatch) current else null,
                toastMessage = if (!isMatch) "Connect request sent to ${current.name}" else null,
                toastType = ToastType.CONNECT
            )
        }
    }

    fun onSuperLike() {
        _uiState.update { it.copy(toastMessage = "Super Liked!", toastType = ToastType.CONNECT) }
    }

    fun onBoost() {
        _uiState.update { it.copy(toastMessage = "Boost activated!", toastType = ToastType.CONNECT) }
    }

    fun onMessage() {
        val current = _uiState.value.cards.firstOrNull() ?: return
        _uiState.update { it.copy(toastMessage = "Opening chat with ${current.name}...", toastType = ToastType.CONNECT) }
    }

    fun dismissMatch() {
        _uiState.update { it.copy(matchedCard = null) }
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}