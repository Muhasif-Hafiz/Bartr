package com.tech.cursor.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AgeScreen(onContinue: (String) -> Unit = {},
              initialValue: String = "",
              ) {
    var name by remember { mutableStateOf(initialValue) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Subtle top gradient blob
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-60).dp, y = (-60).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            TinderRed.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(50)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "STEP 2 OF 6",
                style = TextStyle(
                    color = TinderRed,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Headline
            Text(
                text = "How old \nare you?",
                style = TextStyle(
                    color = TextPrimary,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 42.sp,
                    letterSpacing = (-0.5).sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "This is how it'll appear on your profile.",
                style = TextStyle(
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            OnboardingTextField(
                value = name,
                onValueChange = { name = it },
                label = "Your age",
                placeholder = "e.g. 18",
                imeAction = ImeAction.Done,
                onImeAction = { if (name.isNotBlank()) onContinue(name) }

            )

            Spacer(modifier = Modifier.height(36.dp))

            // CTA button
            TinderButton(
                text = "Continue",
                onClick = { onContinue(name) },
                enabled = name.isNotBlank()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun preview(){

    AgeScreen ()
}