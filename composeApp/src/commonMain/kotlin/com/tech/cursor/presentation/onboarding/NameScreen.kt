package com.tech.cursor.presentation.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger

// ─────────────────────────────────────────────
// Design Tokens (Tinder palette)
// ─────────────────────────────────────────────


val TinderGradient = Brush.horizontalGradient(
    colors = listOf(TinderRed, TinderOrange)
)

// ─────────────────────────────────────────────
// Reusable Onboarding Text Field
// ─────────────────────────────────────────────
/**
 * A Tinder-style onboarding input field.
 *
 * Usage examples:
 *   OnboardingTextField(value = name, onValueChange = { name = it }, label = "Your name", placeholder = "e.g. Alex")
 *   OnboardingTextField(value = age,  onValueChange = { age  = it }, label = "Your age",  placeholder = "e.g. 24", keyboardType = KeyboardType.Number)
 *   OnboardingTextField(value = city, onValueChange = { city = it }, label = "Your city", placeholder = "e.g. Mumbai")
 */
@Composable
fun OnboardingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onImeAction: () -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) BorderActive else BorderIdle,
        animationSpec = tween(durationMillis = 250),
        label = "borderColor"
    )

    val shadowElevation by animateDpAsState(
        targetValue = if (isFocused) 12.dp else 0.dp,
        animationSpec = tween(durationMillis = 250),
        label = "shadowElevation"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        // Label
        Text(
            text = label.uppercase(), style = TextStyle(
                color = if (isFocused) TinderRed else TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Field container
        Box(
            modifier = Modifier.fillMaxWidth().shadow(
                    elevation = shadowElevation,
                    shape = RoundedCornerShape(14.dp),
                    ambientColor = TinderRed.copy(alpha = 0.3f),
                    spotColor = TinderRed.copy(alpha = 0.3f)
                ).clip(RoundedCornerShape(14.dp)).background(SurfaceDark).border(
                    width = 1.5.dp, color = borderColor, shape = RoundedCornerShape(14.dp)
                ).padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = TextPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.3.sp
                ),
                cursorBrush = SolidColor(TinderRed),
                visualTransformation = visualTransformation,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType, imeAction = imeAction
                ),
                keyboardActions = KeyboardActions(
                    onAny = { onImeAction() }),
                modifier = Modifier.fillMaxWidth().onFocusChanged { isFocused = it.isFocused },
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder, style = TextStyle(
                                color = TextSecondary.copy(alpha = 0.5f),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                    innerTextField()
                })
        }

        // Active underline accent
        if (isFocused) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier.fillMaxWidth(0.4f).height(2.dp).clip(RoundedCornerShape(1.dp))
                    .background(TinderGradient)
            )
        }
    }
}

// ─────────────────────────────────────────────
// Tinder-Style Gradient Button
// ─────────────────────────────────────────────
@Composable
fun TinderButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth().height(56.dp).clip(RoundedCornerShape(28.dp)).background(
                if (enabled) TinderGradient
                else Brush.horizontalGradient(listOf(Color(0xFF444444), Color(0xFF555555)))
            ).then(
                if (enabled) Modifier.shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(28.dp),
                    ambientColor = TinderRed.copy(alpha = 0.5f),
                    spotColor = TinderRed.copy(alpha = 0.5f)
                ) else Modifier
            ).clickable{
                onClick()
        }
            .padding(0.dp)
    ) {
        Text(
            text = text, style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )
        )
    }
}

// ─────────────────────────────────────────────
// Name Screen  (Tinder Style)
// ─────────────────────────────────────────────
@Composable
fun NameScreen(
    initialValue: String = "", onBack: (() -> Unit)? = null, onContinue: (String) -> Unit = {}
) {
    var name by remember { mutableStateOf(initialValue) }

    Box(
        modifier = Modifier.fillMaxSize().background(BackgroundDark)
    ) {
        // Subtle top gradient blob
        Box(
            modifier = Modifier.size(300.dp).offset(x = (-60).dp, y = (-60).dp).background(
                    Brush.radialGradient(
                        colors = listOf(
                            TinderRed.copy(alpha = 0.15f), Color.Transparent
                        )
                    ), shape = RoundedCornerShape(50)
                )
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 28.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Step indicator
            Text(
                text = "STEP 1 OF 6", style = TextStyle(
                    color = TinderRed,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Headline
            Text(
                text = "What's your\nfirst name?", style = TextStyle(
                    color = TextPrimary,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 42.sp,
                    letterSpacing = (-0.5).sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "This is how it'll appear on your profile.", style = TextStyle(
                    color = TextSecondary, fontSize = 14.sp, fontWeight = FontWeight.Normal
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            OnboardingTextField(
                value = name,
                onValueChange = { name = it },
                label = "Your name",
                placeholder = "e.g. Alex",
                imeAction = ImeAction.Done,
                onImeAction = { if (name.isNotBlank()) onContinue(name) },
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(36.dp))


            TinderButton(
                text = "Continue",
                onClick = {
                    Logger.d("Clicked $name")
                    onContinue(name)
                },
                enabled = name.isNotBlank()
            )
        }
    }
}