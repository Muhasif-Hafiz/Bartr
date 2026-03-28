package com.tech.cursor.presentation.Login

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tech.cursor.presentation.Home.BColor.Flame
import com.tech.cursor.presentation.Home.BColor.FlameGrad
import com.tech.cursor.presentation.Home.BColor.Orange
import com.tech.cursor.presentation.navigation.Screens


// ─── Color Palette ───────────────────────────────────────────────────────────

private val BgDark       = Color(0xFF0D0D0F)
private val CardDark1    = Color(0xFF1A1A2E)
private val CardDark2    = Color(0xFF16213E)
private val CardDark3    = Color(0xFF0F3460)
private val FlameStart   = Color(0xFFFD5558)
private val FlameEnd     = Color(0xFFFF9A47)
private val GlassWhite10 = Color(0x1AFFFFFF)
private val GlassWhite12 = Color(0x1FFFFFFF)
private val White90      = Color(0xE6FFFFFF)
private val White45      = Color(0x73FFFFFF)
private val White25      = Color(0x40FFFFFF)
private val White15      = Color(0x26FFFFFF)
private val White10      = Color(0x1AFFFFFF)
private val AccentPink   = Color(0xB3FD5558)

private val FlameGradient = Brush.linearGradient(
    colors = listOf(FlameStart, FlameEnd)
)

// ─── Mock profile data ────────────────────────────────────────────────────────

private data class MockProfile(
    val initial: String,
    val name: String,
    val age: Int,
    val job: String,
    val bgBrush: Brush
)

private val profiles = listOf(
    MockProfile(
        initial = "A", name = "Aisha", age = 24, job = "Designer",
        bgBrush = Brush.linearGradient(listOf(Color(0xFF2D1B47), Color(0xFF6B2FA0)))
    ),
    MockProfile(
        initial = "M", name = "Muhasib", age = 26, job = "Developer",
        bgBrush = Brush.linearGradient(listOf(Color(0xFF1A2744), Color(0xFFC0392B)))
    ),
    MockProfile(
        initial = "M", name = "Maya", age = 23, job = "Artist",
        bgBrush = Brush.linearGradient(listOf(Color(0xFF1A3A2A), Color(0xFF2ECC71)))
    )
)

// ─── Login Screen ─────────────────────────────────────────────────────────────

/**
 * Full-screen Google-only login for a Tinder-style app.
 *
 * Usage:
 *   LoginScreen(onGoogleSignIn = { /* launch Google One Tap or OAuth */ })
 *
 * Wiring Google Sign-In (Android):
 *   Use `androidx.credentials:credentials-play-services-auth` (Credential Manager API)
 *   or `com.google.android.gms:play-services-auth` (legacy One Tap).
 *
 * Wiring Google Sign-In (iOS via KMP):
 *   Call into a Swift `expect`/`actual` that uses GoogleSignIn-iOS SDK.
 */
@Composable
fun LoginScreen(
    navController: NavHostController,
    onGoogleSignIn: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
    ) {
        // Radial glow behind the top area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            FlameStart.copy(alpha = 0.22f),
                            Color.Transparent
                        ),
                        radius = 600f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            FlameIcon()

            Spacer(Modifier.height(12.dp))

            // App name with gradient text
            GradientText(
                text = "Bartr",
                brush = FlameGradient,
                fontSize = 40,
                fontWeight = FontWeight.Black
            )

            Text(
                text = "FIND YOUR SKILLMATE",
                color = White45,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.sp
            )

            Spacer(Modifier.height(36.dp))

            CardStack(profiles = profiles)

            Spacer(Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(Modifier.weight(1f))
                Spacer(Modifier.width(10.dp))
                Text(
                    "SIGN IN TO CONTINUE",
                    color = White25,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )
                Spacer(Modifier.width(10.dp))
                Divider(Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            // ── Google Sign-In Button ─────────────────────────────────────────
            GoogleSignInButton{
                onGoogleSignIn()
                navController.navigate(Screens.OnBoard)
            }

            Spacer(Modifier.height(20.dp))

            // ── Terms ─────────────────────────────────────────────────────────
            Text(
                text = "By continuing, you agree to our\nTerms of Service and Privacy Policy",
                color = White25,
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                lineHeight = 17.sp
            )
        }
    }
}

// ─── FlameIcon (SVG-equivalent painted in Canvas / using Unicode fallback) ────

@Composable
private fun FlameIcon() {
    // A simple composed flame using two rounded boxes — replace with
    // a proper SVG/vector asset (res/drawable/ic_flame.xml) in production.
    // Icon
    val iconScale  = remember { Animatable(0.3f) }
    val iconAlpha  = remember { Animatable(0f) }
    val iconRotate = remember { Animatable(-15f) }

    // Ring pulse
    val ringScale  = rememberInfiniteTransition(label = "ring")
    val ringAlpha  = ringScale.animateFloat(
        initialValue = 0.3f, targetValue = 0.08f,
        animationSpec = infiniteRepeatable(tween(2000, easing = EaseInOut), RepeatMode.Reverse),
        label = "ringAlpha"
    )

    // Wordmark
    val wordAlpha  = remember { Animatable(0f) }
    val wordSlide  = remember { Animatable(20f) }

    // Tagline
    val tagAlpha   = remember { Animatable(0f) }
    val tagSlide   = remember { Animatable(8f) }

    // Loading bar
    val loadAlpha  = remember { Animatable(0f) }
    val loadWidth  = remember { Animatable(0f) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(56.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp, 52.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 50.dp, topEnd = 50.dp,
                        bottomStart = 20.dp, bottomEnd = 20.dp
                    )
                )
                .background(FlameGradient)
        )

    }
}

// ─── Gradient Text helper ──────────────────────────────────────────────────────

@Composable
private fun GradientText(
    text: String,
    brush: Brush,
    fontSize: Int,
    fontWeight: FontWeight
) {
    Text(
        text = text,
        style = androidx.compose.ui.text.TextStyle(
            brush = brush,
            fontSize = fontSize.sp,
            fontWeight = fontWeight
        )
    )
}

// ─── Stacked profile cards ────────────────────────────────────────────────────

@Composable
private fun CardStack(profiles: List<MockProfile>) {
    Box(
        modifier = Modifier
            .width(220.dp)
            .height(160.dp),
        contentAlignment = Alignment.Center
    ) {
        // Left card
        ProfileMiniCard(
            profile = profiles[0],
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(y = 8.dp)
                .rotate(-7f)
                .zIndex(1f)
                .size(130.dp, 148.dp)
        )
        // Right card
        ProfileMiniCard(
            profile = profiles[2],
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(y = 10.dp)
                .rotate(6f)
                .zIndex(2f)
                .size(130.dp, 148.dp)
        )
        // Center card (featured)
        Box(modifier = Modifier.zIndex(3f)) {
            ProfileMiniCard(
                profile = profiles[1],
                modifier = Modifier.size(148.dp, 158.dp),
                isCenter = true
            )
            // "LIKE" badge on center card
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 4.dp, y = 8.dp)
                    .rotate(10f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(FlameGradient)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    "❤ LIKE",
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
private fun ProfileMiniCard(
    profile: MockProfile,
    modifier: Modifier = Modifier,
    isCenter: Boolean = false
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(if (isCenter) 20.dp else 16.dp))
            .background(profile.bgBrush)
            .border(
                width = 1.dp,
                color = GlassWhite12,
                shape = RoundedCornerShape(if (isCenter) 20.dp else 16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Avatar circle with initial
            Box(
                modifier = Modifier
                    .size(if (isCenter) 52.dp else 44.dp)
                    .clip(CircleShape)
                    .background(White15),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = profile.initial,
                    color = White90,
                    fontSize = if (isCenter) 22.sp else 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(Modifier.height(6.dp))
            Text(
                text = profile.name,
                color = White90,
                fontSize = if (isCenter) 13.sp else 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${profile.age} · ${profile.job}",
                color = White45,
                fontSize = if (isCenter) 11.sp else 10.sp
            )
        }
    }
}

// ─── Divider line ─────────────────────────────────────────────────────────────

@Composable
private fun Divider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(0.5.dp)
            .background(White15)
    )
}

// ─── Google Sign-In Button ────────────────────────────────────────────────────

@Composable
private fun GoogleSignInButton(onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(if (pressed) GlassWhite10 else White10)
            .border(
                width = 1.dp,
                color = GlassWhite12,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable {
                pressed = true
                onClick()
            }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Spacer(Modifier.width(10.dp))
        Text(
            text = "Continue with Google",
            color = White90,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.1.sp
        )
    }
}


@Composable
private fun GoogleLogo() {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "G",
            color = Color(0xFF4285F4),
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }

}

@Preview
@Composable
fun preview(){
    LoginScreen(rememberNavController())
}