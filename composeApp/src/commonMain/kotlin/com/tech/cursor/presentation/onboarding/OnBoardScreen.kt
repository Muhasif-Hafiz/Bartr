// com/tech/cursor/presentation/onboarding/OnboardingScreen.kt
package com.tech.cursor.presentation.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import co.touchlab.kermit.Logger
import com.tech.cursor.presentation.common.models.OnboardingStep
import com.tech.cursor.presentation.navigation.Screens
import com.tech.cursor.presentation.onboarding.viewmodel.OnboardingViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    navHostController: NavHostController,
    onComplete: () -> Unit,

) {
    val viewModel = remember { OnboardingViewModel() }
    val steps = OnboardingStep.values()
    val pagerState = rememberPagerState { steps.size }
    val currentStep by viewModel.currentStep.collectAsState()
    val onboardingData by viewModel.onboardingData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val onBoardingCompleted by viewModel.onBoardingCompleted.collectAsState()


    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(currentStep) {
        val targetPage = currentStep.stepNumber - 1
        if (pagerState.currentPage != targetPage) {
            pagerState.animateScrollToPage(targetPage)
        }
    }
    LaunchedEffect(onBoardingCompleted) {
        if (onBoardingCompleted) {
            navHostController.navigate(Screens.Home) {
            }
        }
    }




    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxSize(), userScrollEnabled = false
        ) { page ->
            AnimatedContent(
                targetState = steps[page], transitionSpec = {
                    fadeIn() + slideInHorizontally() togetherWith fadeOut() + slideOutHorizontally()
                }, label = "onboarding_screen"
            ) { step ->
                when (step) {
                    OnboardingStep.NAME -> NameScreen(
                        initialValue = onboardingData.name, onContinue = { name ->
                            viewModel.updateName(name)
                            if (viewModel.validateCurrentStep()) {
                                viewModel.nextStep()
                            }
                        })

                    OnboardingStep.AGE -> AgeScreen(
                        initialValue = onboardingData.age,
                        onContinue = { age ->
                            viewModel.updateAge(age)
                            if (viewModel.validateCurrentStep()) {
                                viewModel.nextStep()
                            }
                        },
                    )

                    OnboardingStep.ADDRESS -> AddressScreen(
                        initialAddress = onboardingData.address,
                        initialDistrict = onboardingData.district,
                        initialPinCode = onboardingData.pincode,
                        onContinue = { address, district, pincode ->
                            viewModel.updateAddress(address, district, pincode)
                            if (viewModel.validateCurrentStep()) {
                                viewModel.nextStep()
                            }
                        },
                    )

                    OnboardingStep.PHONE -> PhoneScreen(
                        initialValue = onboardingData.phoneNumber,
                        onContinue = { phone ->
                            if (viewModel.validateCurrentStep()) {
                                viewModel.updatePhoneNumber(phone)
                                viewModel.nextStep()
                            }
                        },
                    )

                    OnboardingStep.SKILLS_HAVE -> SkillsScreen(
                        initialSkills = onboardingData.skillsHave,

                        onContinue = { skills ->
                            viewModel.updateSkillsHave(skills)
                            if (viewModel.validateCurrentStep()) {
                                viewModel.nextStep()
                            }
                        },

                        )

                    OnboardingStep.SKILLS_WANT -> SkillsWantScreen(
                        initialSkills = onboardingData.skillsWant,
                        onFinish = { skills ->
                            viewModel.updateSkillsWant(skills.toSet())
                            if (viewModel.validateCurrentStep()) {
                                viewModel.submitOnboarding(onComplete)
                                navHostController.navigate(Screens.Home)
                            }
                        },

                        )
                }
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().background(BackgroundDark.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = TinderRed, modifier = Modifier.size(48.dp)
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        )
    }
}