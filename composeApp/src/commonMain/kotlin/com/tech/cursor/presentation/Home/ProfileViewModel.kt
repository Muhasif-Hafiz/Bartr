package com.tech.cursor.presentation.Home

import androidx.lifecycle.ViewModel
import com.tech.cursor.presentation.common.models.ProfileToastType
import com.tech.cursor.presentation.common.models.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onEditProfile()        { showToast("Edit profile opened", ProfileToastType.INFO) }
    fun onEditPhoto()          { showToast("Photo upload coming soon!", ProfileToastType.INFO) }
    fun onEditSkills()         { showToast("Edit skills opened", ProfileToastType.INFO) }
    fun onPersonalInfo()       { showToast("Personal info opened", ProfileToastType.INFO) }
    fun onSkillPortfolio()     { showToast("Skill portfolio opened", ProfileToastType.INFO) }
    fun onVerification()       { showToast("Verification opened", ProfileToastType.INFO) }
    fun onPaymentWallet()      { showToast("Payment settings opened", ProfileToastType.INFO) }
    fun onDiscoverySettings()  { showToast("Discovery settings opened", ProfileToastType.INFO) }
    fun onHelpCentre()         { showToast("Help centre opened", ProfileToastType.INFO) }
    fun onSendFeedback()       { showToast("Feedback form opened", ProfileToastType.INFO) }
    fun onPrivacyPolicy()      { showToast("Privacy policy opened", ProfileToastType.INFO) }
    fun onTermsOfService()     { showToast("Terms opened", ProfileToastType.INFO) }
    fun onSettings()           { showToast("Settings opened", ProfileToastType.INFO) }

    fun onToggleNotifications() {
        _uiState.update { it.copy(notificationsEnabled = !it.notificationsEnabled) }
        showToast(if (_uiState.value.notificationsEnabled) "Notifications on" else "Notifications off", ProfileToastType.SUCCESS)
    }

    fun onToggleLocation() {
        _uiState.update { it.copy(locationEnabled = !it.locationEnabled) }
        showToast(if (_uiState.value.locationEnabled) "Location on" else "Location off", ProfileToastType.SUCCESS)
    }

    fun onToggleVisibility() {
        _uiState.update { it.copy(profileVisible = !it.profileVisible) }
        showToast(if (_uiState.value.profileVisible) "Profile visible" else "Profile hidden", ProfileToastType.SUCCESS)
    }

    fun onLogoutClick()        { _uiState.update { it.copy(showLogoutDialog = true) } }
    fun onLogoutDismiss()      { _uiState.update { it.copy(showLogoutDialog = false) } }
    fun onLogoutConfirm()      {
        _uiState.update { it.copy(showLogoutDialog = false) }
        showToast("Logging you out...", ProfileToastType.DANGER)
        // TODO: call AuthRepository.logout() + navigate to login
    }

    fun clearToast()           { _uiState.update { it.copy(toastMessage = null) } }

    private fun showToast(msg: String, type: ProfileToastType) {
        _uiState.update { it.copy(toastMessage = msg, toastType = type) }
    }
}