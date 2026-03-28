package com.tech.cursor.presentation.onboarding.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.tech.cursor.presentation.common.models.OnboardingStep
import com.tech.cursor.presentation.onboarding.Skill
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



data class OnboardingData(
    val name: String = "",
    val age: String = "",
    val address: String = "",
    val district: String = "",
    val pincode: String = "",
    val phoneNumber : String = "",
    val skillsHave: Set<Skill> = emptySet(),
    val skillsWant: Set<Skill> = emptySet()
)


class OnboardingViewModel : ViewModel() {

    private val _onboardingData = MutableStateFlow(OnboardingData())
    val onboardingData: StateFlow<OnboardingData> = _onboardingData.asStateFlow()

    private val _currentStep = MutableStateFlow(OnboardingStep.NAME)
    val currentStep: StateFlow<OnboardingStep> = _currentStep.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val _onBoardingCompleted = MutableStateFlow(false)
    val onBoardingCompleted: StateFlow<Boolean> = _onBoardingCompleted.asStateFlow()


    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun updateName(name: String) {
        _onboardingData.value = _onboardingData.value.copy(name = name)
    }

    fun updateAge(age: String) {
        _onboardingData.value = _onboardingData.value.copy(age = age)
    }

    fun updateAddress(address: String, district: String, pincode: String) {
        _onboardingData.value = _onboardingData.value.copy(
            address = address,
            district = district,
            pincode = pincode
        )
    }
    fun updatePhoneNumber(phoneNumber: String) {
        _onboardingData.value = _onboardingData.value.copy(phoneNumber = phoneNumber)
    }

    fun updateSkillsHave(skills: Set<Skill>) {
        _onboardingData.value = _onboardingData.value.copy(skillsHave = skills)
    }

    fun updateSkillsWant(skills: Set<Skill>) {
        _onboardingData.value = _onboardingData.value.copy(skillsWant = skills)
    }

    fun nextStep() {
        val steps = OnboardingStep.values()
        val currentIndex = steps.indexOf(_currentStep.value)
        if (currentIndex < steps.size - 1) {
            _currentStep.value = steps[currentIndex + 1]
        }
    }

    fun previousStep() {
        val steps = OnboardingStep.values()
        val currentIndex = steps.indexOf(_currentStep.value)
        if (currentIndex > 0) {
            _currentStep.value = steps[currentIndex - 1]
        }
    }

    fun validateCurrentStep(): Boolean {
        return when (_currentStep.value) {
            OnboardingStep.NAME -> _onboardingData.value.name.isNotBlank()
            OnboardingStep.AGE -> {
                val age = _onboardingData.value.age.toIntOrNull()
                age != null && age >= 1 && age <= 100
            }
            OnboardingStep.ADDRESS -> {
                _onboardingData.value.address.isNotBlank() &&
                        _onboardingData.value.district.isNotBlank()
            }
            OnboardingStep.PHONE -> true // Optional step
            OnboardingStep.SKILLS_HAVE -> _onboardingData.value.skillsHave.isNotEmpty()
            OnboardingStep.SKILLS_WANT -> _onboardingData.value.skillsWant.isNotEmpty()
        }
    }

    fun submitOnboarding(onComplete: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                if (validateAllSteps()) {
                    val result = saveOnboardingData(_onboardingData.value)

                    logAllOnboardingData()
                    if (result.isSuccess) {
                        onComplete()
                        _onBoardingCompleted.value = true
                    } else {
                        _error.value = result.exceptionOrNull()?.message ?: "Failed to save data"
                    }
                } else {
                    _error.value = "Please complete all required fields"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logAllOnboardingData() {
        val data = _onboardingData.value
        Logger.d("OnboardingDataLog") {
            """
        Name: ${data.name}
        Age: ${data.age}
        Address: ${data.address}, District: ${data.district}, Pincode: ${data.pincode}
        Phone: ${data.phoneNumber}
        Skills Have: ${data.skillsHave.joinToString { it.name }}
        Skills Want: ${data.skillsWant.joinToString { it.name }}
        """.trimIndent()
        }
    }

    private fun validateAllSteps(): Boolean {
        return OnboardingStep.values().all { step ->
            when (step) {
                OnboardingStep.NAME -> _onboardingData.value.name.isNotBlank()
                OnboardingStep.AGE -> {
                    val age = _onboardingData.value.age.toIntOrNull()
                    age != null && age >= 18 && age <= 100
                }
                OnboardingStep.ADDRESS -> {
                    _onboardingData.value.address.isNotBlank() &&
                            _onboardingData.value.district.isNotBlank()
                }
                OnboardingStep.PHONE -> true
                OnboardingStep.SKILLS_HAVE -> _onboardingData.value.skillsHave.isNotEmpty()
                OnboardingStep.SKILLS_WANT -> _onboardingData.value.skillsWant.isNotEmpty()
            }
        }
    }

    private suspend fun saveOnboardingData(data: OnboardingData): Result<Unit> {
        return try {
            kotlinx.coroutines.delay(1000)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun clearError() {
        _error.value = null
    }
}