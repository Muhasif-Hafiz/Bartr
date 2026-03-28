package com.tech.cursor.presentation.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IndiaFlagIcon(modifier: Modifier = Modifier) {
    Column(modifier = modifier.clip(RoundedCornerShape(3.dp))) {
        Box(
            modifier = Modifier
                .width(26.dp)
                .weight(1f)
                .background(Color(0xFFFF9933))
        )
        Box(
            modifier = Modifier
                .width(26.dp)
                .weight(1f)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            // Simplified Chakra dot
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF000080))
            )
        }
        // Green
        Box(
            modifier = Modifier
                .width(26.dp)
                .weight(1f)
                .background(Color(0xFF138808))
        )
    }
}

@Composable
fun PhoneInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onImeAction: () -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) TinderRed else BorderIdle,
        animationSpec = tween(250), label = "phoneBorder"
    )
    val glowSize by animateDpAsState(
        targetValue = if (isFocused) 3.dp else 0.dp,
        animationSpec = tween(250), label = "phoneGlow"
    )
    val labelColor by animateColorAsState(
        targetValue = if (isFocused) TinderRed else TextSecondary,
        animationSpec = tween(250), label = "phoneLabel"
    )
    val accentWidth by animateFloatAsState(
        targetValue = if (isFocused || value.isNotEmpty()) 0.4f else 0f,
        animationSpec = tween(300), label = "phoneAccent"
    )

    val isValid = value.length == 10

    Column(modifier = modifier.fillMaxWidth()) {

        Text(
            text = "MOBILE NUMBER",
            style = TextStyle(
                color = labelColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.6.sp
            )
        )

        Spacer(Modifier.height(9.dp))

        // Field row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(SurfaceDark)
                .border(
                    width = 1.5.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(14.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ── India prefix ─────────────────────────
            Row(
                modifier = Modifier
                    .height(52.dp)
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                IndiaFlagIcon(
                    modifier = Modifier
                        .width(26.dp)
                        .height(18.dp)
                )
                Text(
                    text = "+91",
                    style = TextStyle(
                        color = Color(0xFFCCCCCC),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            // Divider
            Box(
                modifier = Modifier
                    .width(1.5.dp)
                    .height(28.dp)
                    .background(BorderIdle)
            )

            // ── Number input ─────────────────────────
            BasicTextField(
                value = value,
                onValueChange = { raw ->
                    val digits = raw.filter { it.isDigit() }.take(10)
                    onValueChange(digits)
                },
                singleLine = true,
                cursorBrush = SolidColor(TinderRed),
                textStyle = TextStyle(
                    color = TextPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.5.sp
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onImeAction() }
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .padding(horizontal = 14.dp)
                    .onFocusChanged { isFocused = it.isFocused },
                decorationBox = { inner ->
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (value.isEmpty()) {
                            Text(
                                text = "Enter 10-digit number",
                                style = TextStyle(
                                    color = TextSecondary.copy(alpha = 0.45f),
                                    fontSize = 15.sp,
                                    letterSpacing = 0.sp
                                )
                            )
                        }
                        inner()
                    }
                }
            )
        }

        // Gradient accent bar
        Spacer(Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(accentWidth)
                .height(2.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(TinderGradient)
        )

        // Bottom row: hint + char count
        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (isValid) "Looks good!" else "India (+91) only",
                style = TextStyle(
                    color = if (isValid) Color(0xFF27AE60) else Color(0xFF555555),
                    fontSize = 11.sp
                )
            )
            Text(
                text = "${value.length} / 10",
                style = TextStyle(color = Color(0xFF555555), fontSize = 11.sp)
            )
        }
    }
}

// ── OTP Confirmation Note ──────────────────────────────
@Composable
fun OtpConfirmNote(phone: String) {
    val formatted = "+91 ${phone.take(5)} ${phone.drop(5)}"
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(TinderRed.copy(alpha = 0.07f))
            .border(1.dp, TinderRed.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Text(
            text = "An OTP will be sent to $formatted for verification.",
            style = TextStyle(
                color = TextSecondary,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        )
    }
}

@Composable
fun PhoneScreen(
    onContinue: (phone: String) -> Unit = {},
    initialValue: String = "",
) {
    var phone by remember { mutableStateOf(initialValue) }
    val isValid = phone.length == 10

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Box(
            modifier = Modifier
                .size(260.dp)
                .offset((-70).dp, (-70).dp)
                .background(
                    Brush.radialGradient(listOf(TinderRed.copy(0.17f), Color.Transparent))
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)
        ) {
            Spacer(Modifier.height(56.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(TinderRed.copy(0.12f))
                    .border(1.dp, TinderRed.copy(0.25f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "STEP 4 OF 6",
                    style = TextStyle(
                        color = TinderRed,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )
                )
            }

            Spacer(Modifier.height(18.dp))

            Text(
                text = "What's your\nphone number?",
                style = TextStyle(
                    color = TextPrimary,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 36.sp,
                    letterSpacing = (-0.7).sp
                )
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "We'll send a verification code.\nWe never share your number.",
                style = TextStyle(
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            )

            Spacer(Modifier.height(32.dp))

            // Phone field
            PhoneInputField(
                value = phone,
                onValueChange = { phone = it },
                onImeAction = { if (isValid) onContinue(phone) }
            )


            Spacer(Modifier.weight(1f))

            // CTA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(TinderGradient)
                    .then(
                        if (isValid) Modifier.clickable { onContinue(phone) } else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (!isValid) {
                    Box(Modifier.matchParentSize().background(BackgroundDark.copy(0.5f)))
                }
                Text(
                    text = "Continue",
                    style = TextStyle(
                        color = TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.6.sp
                    )
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "By continuing you agree to our ",
                    style = TextStyle(color = TextSecondary, fontSize = 11.sp)
                )
                Text(
                    text = "Terms of Service",
                    style = TextStyle(color = TinderRed, fontSize = 11.sp)
                )
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhoneScreenPreview() {
    PhoneScreen()
}