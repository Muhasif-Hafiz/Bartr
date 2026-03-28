package com.tech.cursor.presentation.splash


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shadow.Companion.None
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tech.cursor.presentation.navigation.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

// ─── Colors ───────────────────────────────────────────────────────────────────

private val Bg          = Color(0xFF0A0A0D)
private val Flame       = Color(0xFFFD5558)
private val Orange      = Color(0xFFFF9A47)
private val FlameGrad   = Brush.linearGradient(listOf(Color(0xFFFD5558), Color(0xFFFF9A47)))
private val W50         = Color(0x80FFFFFF)
private val W30         = Color(0x4DFFFFFF)
private val W18         = Color(0x2EFFFFFF)
private val W10         = Color(0x1AFFFFFF)
private val OfferBg     = Color(0x26FD5558)
private val OfferBorder = Color(0x59FD5558)
private val OfferText   = Color(0xFFFFA08A)
private val WantBg      = Color(0x1AFF9A47)
private val WantBorder  = Color(0x4DFF9A47)
private val WantText    = Color(0xFFFFB86C)

// ─── Floating pills data ──────────────────────────────────────────────────────

private data class FloatingPill(
    val label: String,
    val isOffer: Boolean,
    val xFraction: Float,   // 0f..1f of screen width
    val yFraction: Float,   // 0f..1f of screen height
    val delayMs: Int
)

private val floatingPills = listOf(
    FloatingPill("UI/UX Design",   true,  0.04f, 0.12f, 1300),
    FloatingPill("React Native",   false, 0.55f, 0.18f, 1450),
    FloatingPill("Photography",    true,  0.03f, 0.72f, 1550),
    FloatingPill("Branding",       false, 0.62f, 0.78f, 1700),
    FloatingPill("Flutter",        true,  0.00f, 0.30f, 1600),
    FloatingPill("Video Editing",  false, 0.58f, 0.62f, 1800),
)

// ─── SplashScreen ─────────────────────────────────────────────────────────────

@Composable
fun SplashScreen(navHostController: NavHostController) {

    // ── Animation states ──────────────────────────────────────────────────────

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

    // Pill alphas
    val pillAlphas = floatingPills.map { remember { Animatable(0f) } }
    val pillOffsetY = floatingPills.map {
        rememberInfiniteTransition(label = "pill").animateFloat(
            initialValue = 0f, targetValue = -10f,
            animationSpec = infiniteRepeatable(
                tween(5000 + (0..2000).random(), easing = EaseInOut), RepeatMode.Reverse
            ),
            label = "pillY"
        )
    }

    // Orb animation
    val orbAnim = rememberInfiniteTransition(label = "orb")
    val orb1X   = orbAnim.animateFloat(0f, 20f,  infiniteRepeatable(tween(6000, easing = EaseInOut), RepeatMode.Reverse), label = "o1x")
    val orb1Y   = orbAnim.animateFloat(0f, 30f,  infiniteRepeatable(tween(6000, easing = EaseInOut), RepeatMode.Reverse), label = "o1y")
    val orb2X   = orbAnim.animateFloat(0f, -25f, infiniteRepeatable(tween(7000, easing = EaseInOut), RepeatMode.Reverse), label = "o2x")
    val orb2Y   = orbAnim.animateFloat(0f, -20f, infiniteRepeatable(tween(7000, easing = EaseInOut), RepeatMode.Reverse), label = "o2y")

    // Loading counter
    var loadPercent by remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Icon pop
        launch {
            delay(300)
            launch { iconAlpha.animateTo(1f,  tween(400)) }
            launch { iconRotate.animateTo(0f, spring(dampingRatio = Spring.DampingRatioMediumBouncy)) }
            iconScale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium))
        }
        // Wordmark
        launch {
            delay(650)
            launch { wordSlide.animateTo(0f, spring(dampingRatio = Spring.DampingRatioLowBouncy)) }
            wordAlpha.animateTo(1f, tween(500))
        }
        // Tagline
        launch {
            delay(1000)
            launch { tagSlide.animateTo(0f, tween(500, easing = EaseOut)) }
            tagAlpha.animateTo(1f, tween(500))
        }
        // Loader appears
        launch {
            delay(1200)
            loadAlpha.animateTo(1f, tween(400))
            loadWidth.animateTo(100f, tween(2200, easing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)))
        }
        // Percent counter
        launch {
            delay(1400)
            repeat(100) { i ->
                loadPercent = i + 1
                delay(20)
            }
        }
        // Pills
        floatingPills.forEachIndexed { i, pill ->
            launch {
                delay(pill.delayMs.toLong())
                pillAlphas[i].animateTo(1f, spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessMediumLow))
            }
        }
        // Navigate when done
        delay(4000)

      navHostController.navigate(Screens.Login)
    }

    // ── UI ────────────────────────────────────────────────────────────────────

    Box(
        Modifier.fillMaxSize().background(Bg),
        contentAlignment = Alignment.Center
    ) {
        // Grid pattern
        Box(
            Modifier.fillMaxSize().drawBehind {
                val cellSize = 40.dp.toPx()
                val lineColor = Color(0x07FFFFFF)
                var x = 0f
                while (x <= size.width) {
                    drawLine(lineColor, Offset(x, 0f), Offset(x, size.height), strokeWidth = 1f)
                    x += cellSize
                }
                var y = 0f
                while (y <= size.height) {
                    drawLine(lineColor, Offset(0f, y), Offset(size.width, y), strokeWidth = 1f)
                    y += cellSize
                }
            }
        )

        // Orb 1 — top left
        Box(
            Modifier.size(320.dp)
                .align(Alignment.TopStart)
                .offset((-60 + orb1X.value).dp, (-80 + orb1Y.value).dp)
                .background(
                    Brush.radialGradient(listOf(Color(0x2EFD5558), Color.Transparent)),
                    CircleShape
                )
        )

        // Orb 2 — bottom right
        Box(
            Modifier.size(260.dp)
                .align(Alignment.BottomEnd)
                .offset((-40 + orb2X.value).dp, (-60 + orb2Y.value).dp)
                .background(
                    Brush.radialGradient(listOf(Color(0x24FF9A47), Color.Transparent)),
                    CircleShape
                )
        )

        // Floating pills
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val w = maxWidth
            val h = maxHeight
            floatingPills.forEachIndexed { i, pill ->
                Box(
                    Modifier
                        .offset(
                            x = w * pill.xFraction,
                            y = h * pill.yFraction + pillOffsetY[i].value.dp
                        )
                        .alpha(pillAlphas[i].value)
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (pill.isOffer) OfferBg else WantBg)
                        .border(1.dp, if (pill.isOffer) OfferBorder else WantBorder, RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Text(
                        pill.label,
                        color      = if (pill.isOffer) OfferText else WantText,
                        fontSize   = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // ── Centre content ────────────────────────────────────────────────────
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Spacer(Modifier.weight(1f))

            // Logo icon with ring pulse
            Box(contentAlignment = Alignment.Center) {
                // Outer ring (pulse)
                Box(
                    Modifier.size(116.dp)
                        .scale(1f + ringAlpha.value * 0.08f)
                        .alpha(ringAlpha.value)
                        .clip(RoundedCornerShape(36.dp))
                        .background(Brush.linearGradient(listOf(Flame, Orange)))
                )
                // Icon box
                Box(
                    Modifier
                        .size(100.dp)
                        .scale(iconScale.value)
                        .alpha(iconAlpha.value)
                        .graphicsLayer { rotationZ = iconRotate.value }
                        .clip(RoundedCornerShape(28.dp))
                        .background(FlameGrad),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "B",
                        fontSize   = 52.sp,
                        fontWeight = FontWeight.Black,
                        color      = Color.White,

                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            // Wordmark
            Text(
                "Bartr",
                style    = TextStyle(
                    brush      = FlameGrad,
                    fontSize   = 52.sp,
                    fontWeight = FontWeight.Black
                ),
                modifier = Modifier
                    .alpha(wordAlpha.value)
                    .offset(y = wordSlide.value.dp)
            )

            Spacer(Modifier.height(10.dp))

            // Tagline
            Text(
                "Trade Skills · Build Together",
                color      = W50,
                fontSize   = 15.sp,
                fontWeight = FontWeight.SemiBold,
                modifier   = Modifier
                    .alpha(tagAlpha.value)
                    .offset(y = tagSlide.value.dp)
            )

            Spacer(Modifier.weight(1f))

            // Loading bar
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .alpha(loadAlpha.value)
                    .padding(bottom = 70.dp)
            ) {
                Box(
                    Modifier.width(140.dp).height(3.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(W10)
                ) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(loadWidth.value / 100f)
                            .clip(RoundedCornerShape(2.dp))
                            .background(FlameGrad)
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    if (loadPercent >= 100) "Ready!" else "$loadPercent%",
                    color      = W30,
                    fontSize   = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }

        // Version tag
        Text(
            "v1.0.0 · Made in Kashmir",
            color      = W18,
            fontSize   = 11.sp,
            fontWeight = FontWeight.SemiBold,
            modifier   = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 28.dp)
                .alpha(tagAlpha.value)
        )
    }
}

// Helper shape alias
private val CircleShape = RoundedCornerShape(50)

@Preview
@Composable
fun pre(){
    MaterialTheme{
        SplashScreen(navHostController = rememberNavController())
    }
}