package com.tech.cursor.presentation.Home

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tech.cursor.presentation.common.SkillCard
import kotlinx.coroutines.delay
import kotlin.math.abs

// ─── Design Tokens ────────────────────────────────────────────────────────────

object BColor {
    val Bg       = Color(0xFF0A0A0D)
    val Surface5 = Color(0x0DFFFFFF)
    val Border   = Color(0x17FFFFFF)
    val White90  = Color(0xE6FFFFFF)
    val White70  = Color(0xB3FFFFFF)
    val White50  = Color(0x80FFFFFF)
    val White35  = Color(0x59FFFFFF)
    val White20  = Color(0x33FFFFFF)
    val White10  = Color(0x1AFFFFFF)
    val Flame    = Color(0xFFFD5558)
    val Orange   = Color(0xFFFF9A47)
    val Green    = Color(0xFF2ECC71)
    val FlameGrad = Brush.linearGradient(listOf(Color(0xFFFD5558), Color(0xFFFF9A47)))
}

// ─── HomeScreen ───────────────────────────────────────────────────────────────

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onNotificationClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Auto-clear toast after 2 seconds
    LaunchedEffect(uiState.toastMessage) {
        if (uiState.toastMessage != null) {
            delay(2000)
            viewModel.clearToast()
        }
    }

    Box(Modifier.fillMaxSize().background(BColor.Bg)) {
        Column(Modifier.fillMaxSize()) {
            TopBar(onNotificationClick)
            Box(Modifier.weight(1f)) {
                if (uiState.cards.isEmpty()) {
                    EmptyDeck(Modifier.fillMaxSize())
                } else {
                    CardDeck(
                        cards    = uiState.cards,
                        onPass   = viewModel::onPass,
                        onConnect = viewModel::onConnect
                    )
                }
            }
        }

        // Toast
        uiState.toastMessage?.let { msg ->
            Toast(
                message = msg,
                type    = uiState.toastType,
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 72.dp)
            )
        }

        // Match overlay
        uiState.matchedCard?.let { matched ->
            MatchOverlay(
                card          = matched,
                onDismiss     = viewModel::dismissMatch,
                onSendMessage = viewModel::dismissMatch
            )
        }
    }
}

// ─── Top Bar ─────────────────────────────────────────────────────────────────

@Composable
private fun TopBar(onNotificationClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 42.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Bartr",
            style = TextStyle(
                brush      = BColor.FlameGrad,
                fontSize   = 30.sp,
                fontWeight = FontWeight.Black
            )
        )
        Box {
            Box(
                Modifier.size(42.dp).clip(RoundedCornerShape(14.dp))
                    .background(BColor.Surface5)
                    .border(1.dp, BColor.Border, RoundedCornerShape(14.dp))
                    .clickable(onClick = onNotificationClick),
                contentAlignment = Alignment.Center
            ) {
                Text("🔔", fontSize = 18.sp)
            }
            Box(
                Modifier.size(10.dp).align(Alignment.TopEnd).offset(x = 2.dp, y = (-2).dp)
                    .clip(CircleShape).background(BColor.Flame)
                    .border(2.dp, BColor.Bg, CircleShape)
            )
        }
    }
}

// ─── Card Deck ────────────────────────────────────────────────────────────────

@Composable
private fun CardDeck(
    cards: List<SkillCard>,
    onPass: () -> Unit,
    onConnect: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier.weight(1f)
                .padding(horizontal = 16.dp)
                .padding(top = 4.dp)
        ) {
            if (cards.size >= 3) PeekCard(cards[2], index = 2)
            if (cards.size >= 2) PeekCard(cards[1], index = 1)
            DraggableCard(
                card      = cards[0],
                onPass    = onPass,
                onConnect = onConnect,
                modifier  = Modifier.fillMaxSize()
            )
        }
        ProgressDots(total = minOf(cards.size, 3), activeIndex = 0)
        ActionRow(
            onPass      = onPass,
            onConnect   = onConnect,
            onSuperLike = {},
            onBoost     = {},
            onMessage   = {}
        )
    }
}

@Composable
private fun PeekCard(card: SkillCard, index: Int) {
    Box(
        Modifier.fillMaxSize()
            .graphicsLayer {
                scaleX       = if (index == 2) 0.94f else 0.97f
                scaleY       = if (index == 2) 0.94f else 0.97f
                translationY = if (index == 2) 20.dp.toPx() else 10.dp.toPx()
                rotationZ    = if (index == 2) -3f else 1.5f
            }
            .zIndex(index.toFloat())
            .clip(RoundedCornerShape(28.dp))
            .background(card.cardGradient)
            .border(1.dp, BColor.Border, RoundedCornerShape(28.dp))
    )
}

// ─── Draggable Card ───────────────────────────────────────────────────────────

@Composable
private fun DraggableCard(
    card: SkillCard,
    onPass: () -> Unit,
    onConnect: () -> Unit,
    modifier: Modifier = Modifier
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val rotation     = (offsetX / 22f).coerceIn(-20f, 20f)
    val passAlpha    = if (offsetX < -30f) (abs(offsetX) / 120f).coerceIn(0f, 1f) else 0f
    val connectAlpha = if (offsetX >  30f) (offsetX    / 120f).coerceIn(0f, 1f) else 0f

    Box(
        modifier
            .graphicsLayer {
                translationX = offsetX
                translationY = offsetY * 0.35f
                rotationZ    = rotation
            }
            .zIndex(3f)
            .clip(RoundedCornerShape(28.dp))
            .background(card.cardGradient)
            .border(1.dp, BColor.Border, RoundedCornerShape(28.dp))
            .pointerInput(card.id) {
                detectDragGestures(
                    onDragEnd = {
                        when {
                            offsetX >  120f -> onConnect()
                            offsetX < -120f -> onPass()
                            else            -> { offsetX = 0f; offsetY = 0f }
                        }
                    },
                    onDragCancel = { offsetX = 0f; offsetY = 0f },
                    onDrag = { _, dragAmount ->
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                )
            }
    ) {
        // Avatar placeholder (replace with AsyncImage/Coil in production)
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Box(
                Modifier.size(120.dp).clip(CircleShape)
                    .background(card.avatarColor)
                    .border(3.dp, BColor.Border, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    card.initial,
                    fontSize   = 52.sp,
                    fontWeight = FontWeight.Black,
                    color      = BColor.White90
                )
            }
        }

        // Gradient scrim
        Box(
            Modifier.fillMaxSize().background(
                Brush.verticalGradient(
                    0f    to Color.Transparent,
                    0.35f to Color.Transparent,
                    0.62f to Color(0x8C000000),
                    1f    to Color(0xF5000000)
                )
            )
        )

        // NOPE label
        if (passAlpha > 0f) {
            Box(
                Modifier.align(Alignment.TopStart).padding(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, BColor.Flame, RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 5.dp)
                    .graphicsLayer { alpha = passAlpha }
            ) {
                Text(
                    "NOPE",
                    color      = BColor.Flame,
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }
        }

        // CONNECT label
        if (connectAlpha > 0f) {
            Box(
                Modifier.align(Alignment.TopEnd).padding(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, BColor.Green, RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 5.dp)
                    .graphicsLayer { alpha = connectAlpha }
            ) {
                Text(
                    "CONNECT",
                    color      = BColor.Green,
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }
        }

        // Card info at bottom
        CardInfo(card = card, modifier = Modifier.align(Alignment.BottomStart))
    }
}

// ─── Card Info ────────────────────────────────────────────────────────────────

@Composable
private fun CardInfo(card: SkillCard, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxWidth()
            .padding(horizontal = 18.dp)
            .padding(bottom = 14.dp)
    ) {
        // Match badge
        Row(
            Modifier.clip(RoundedCornerShape(20.dp))
                .background(Color(0x33FF9A47))
                .border(1.dp, Color(0x66FF9A47), RoundedCornerShape(20.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("★", color = BColor.Orange, fontSize = 11.sp)
            Text(
                "${card.matchPercent}% SKILL MATCH",
                color         = BColor.Orange,
                fontSize      = 11.sp,
                fontWeight    = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            )
        }
        Spacer(Modifier.height(8.dp))

        // Name + age + verified
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(card.name,        color = Color.White,   fontSize = 23.sp, fontWeight = FontWeight.ExtraBold)
            Text(", ${card.age}",  color = BColor.White70, fontSize = 21.sp)
            if (card.isVerified) {
                Box(
                    Modifier.size(20.dp).clip(CircleShape).background(BColor.Flame),
                    contentAlignment = Alignment.Center
                ) { Text("✓", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold) }
            }
        }

        // Location + district + title
        Text(
            "📍 ${card.location} · ${card.distanceKm}km  ·  ${card.title}",
            color      = BColor.White50,
            fontSize   = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier   = Modifier.padding(top = 2.dp, bottom = 9.dp)
        )

        // Bio
        Text(
            card.bio,
            color      = BColor.White70,
            fontSize   = 13.sp,
            lineHeight = 19.sp,
            fontStyle  = FontStyle.Italic,
            modifier   = Modifier.padding(bottom = 12.dp)
        )

        // Skill pills
        SkillPillRow(label = "OFFERS",          pills = card.offers, isOffer = true)
        Spacer(Modifier.height(8.dp))
        SkillPillRow(label = "WANTS IN RETURN", pills = card.wants,  isOffer = false)
        Spacer(Modifier.height(10.dp))

        Box(Modifier.fillMaxWidth().height(1.dp).background(BColor.White10))
        Spacer(Modifier.height(8.dp))

        // Stats
        Row(Modifier.fillMaxWidth()) {
            StatItem("${card.trades}",          "TRADES", Modifier.weight(1f))
            Box(Modifier.width(1.dp).height(30.dp).background(BColor.White10))
            StatItem("${card.rating}",          "RATING", Modifier.weight(1f))
            Box(Modifier.width(1.dp).height(30.dp).background(BColor.White10))
            StatItem("${card.responseRate}%",   "RESP",   Modifier.weight(1f))
            Box(Modifier.width(1.dp).height(30.dp).background(BColor.White10))
            StatItem("${card.experienceYears}yr","EXP",   Modifier.weight(1f))
        }
    }
}

@Composable
private fun SkillPillRow(label: String, pills: List<String>, isOffer: Boolean) {
    Column {
        Text(
            label,
            color         = BColor.White35,
            fontSize      = 10.sp,
            fontWeight    = FontWeight.Bold,
            letterSpacing = 1.2.sp,
            modifier      = Modifier.padding(bottom = 6.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            pills.take(4).forEach { skill ->
                Box(
                    Modifier.clip(RoundedCornerShape(20.dp))
                        .background(if (isOffer) Color(0x26FD5558) else Color(0x1AFF9A47))
                        .border(
                            1.dp,
                            if (isOffer) Color(0x4DFD5558) else Color(0x40FF9A47),
                            RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        skill,
                        color      = if (isOffer) Color(0xFFFFA08A) else Color(0xFFFFB86C),
                        fontSize   = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun StatItem(value: String, label: String, modifier: Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = Color.White,    fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
        Text(label, color = BColor.White35, fontSize = 9.sp,  fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
    }
}

// ─── Progress Dots ────────────────────────────────────────────────────────────

@Composable
private fun ProgressDots(total: Int, activeIndex: Int) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        repeat(total) { i ->
            val active = i == activeIndex
            Box(
                Modifier.padding(horizontal = 3.dp)
                    .height(6.dp)
                    .width(if (active) 20.dp else 6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        if (active) BColor.FlameGrad
                        else Brush.linearGradient(listOf(BColor.White20, BColor.White20))
                    )
            )
        }
    }
}

// ─── Action Row ──────────────────────────────────────────────────────────────

@Composable
private fun ActionRow(
    onPass: () -> Unit,
    onConnect: () -> Unit,
    onSuperLike: () -> Unit,
    onBoost: () -> Unit,
    onMessage: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp, top = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        ActionBtn(52.dp, 18.dp, Color(0x14FD5558), Color(0x33FD5558), onPass)      { Text("✕", color = BColor.Flame,  fontSize = 20.sp, fontWeight = FontWeight.Bold) }
        ActionBtn(48.dp, 16.dp, Color(0x1AFF9A47), Color(0x40FF9A47), onSuperLike) { Text("★", color = BColor.Orange, fontSize = 20.sp) }
        ActionBtn(66.dp, 22.dp, bgBrush = BColor.FlameGrad, border = Color.Transparent, onClick = onConnect) { Text("♥", color = Color.White, fontSize = 26.sp) }
        ActionBtn(48.dp, 16.dp, BColor.Surface5,  BColor.Border,     onBoost)      { Text("⚡", color = BColor.White50, fontSize = 18.sp) }
        ActionBtn(52.dp, 18.dp, Color(0x1A2ECC71), Color(0x332ECC71), onMessage)   { Text("💬", color = BColor.Green,   fontSize = 18.sp) }
    }
}

@Composable
private fun ActionBtn(
    size: androidx.compose.ui.unit.Dp,
    cornerRadius: androidx.compose.ui.unit.Dp,
    bg: Color = Color.Transparent,
    border: Color,
    onClick: () -> Unit,
    bgBrush: Brush? = null,
    content: @Composable () -> Unit
) {
    Box(
        Modifier.size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .then(if (bgBrush != null) Modifier.background(bgBrush) else Modifier.background(bg))
            .border(1.5.dp, border, RoundedCornerShape(cornerRadius))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) { content() }
}

// ─── Match Overlay ────────────────────────────────────────────────────────────

@Composable
private fun MatchOverlay(
    card: SkillCard,
    onDismiss: () -> Unit,
    onSendMessage: () -> Unit
) {
    Box(
        Modifier.fillMaxSize()
            .background(Color(0xE0000000))
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Flame emoji
            Text("🔥", fontSize = 40.sp)

            // Title
            Text(
                "It's a Match!",
                style = TextStyle(
                    brush      = BColor.FlameGrad,
                    fontSize   = 34.sp,
                    fontWeight = FontWeight.Black
                )
            )
            Text(
                "You both want to trade skills",
                color    = BColor.White70,
                fontSize = 15.sp
            )

            // Avatars
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.size(80.dp).clip(CircleShape)
                        .background(BColor.Surface5)
                        .border(3.dp, BColor.Flame, CircleShape),
                    contentAlignment = Alignment.Center
                ) { Text("👤", fontSize = 36.sp) }

                Text("❤️", fontSize = 28.sp)

                Box(
                    Modifier.size(80.dp).clip(CircleShape)
                        .background(card.avatarColor)
                        .border(3.dp, BColor.Flame, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(card.initial, fontSize = 36.sp, fontWeight = FontWeight.Black, color = BColor.White90)
                }
            }

            Text(
                "${card.name} wants to trade too!",
                color      = Color.White,
                fontSize   = 17.sp,
                fontWeight = FontWeight.Bold
            )

            // Buttons
            Box(
                Modifier.clip(RoundedCornerShape(18.dp))
                    .background(BColor.FlameGrad)
                    .clickable(onClick = onSendMessage)
                    .padding(horizontal = 36.dp, vertical = 14.dp)
            ) {
                Text("Send Message", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
            }

            Box(
                Modifier.clip(RoundedCornerShape(18.dp))
                    .background(BColor.Surface5)
                    .border(1.dp, BColor.Border, RoundedCornerShape(18.dp))
                    .clickable(onClick = onDismiss)
                    .padding(horizontal = 36.dp, vertical = 14.dp)
            ) {
                Text("Keep Swiping", color = BColor.White70, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ─── Toast ────────────────────────────────────────────────────────────────────

@Composable
private fun Toast(message: String, type: ToastType, modifier: Modifier = Modifier) {
    val bg     = if (type == ToastType.PASS) Color(0x33FD5558) else Color(0x332ECC71)
    val border = if (type == ToastType.PASS) Color(0x66FD5558) else Color(0x662ECC71)
    val color  = if (type == ToastType.PASS) BColor.Flame      else BColor.Green

    Box(
        modifier.clip(RoundedCornerShape(20.dp))
            .background(bg)
            .border(1.dp, border, RoundedCornerShape(20.dp))
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(message, color = color, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

// ─── Empty State ──────────────────────────────────────────────────────────────

@Composable
private fun EmptyDeck(modifier: Modifier = Modifier) {
    Box(modifier.padding(horizontal = 16.dp), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("🔥", fontSize = 48.sp)
            Text("You're all caught up!", color = BColor.White70, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                "Check back soon for new skill matches",
                color     = BColor.White35,
                fontSize  = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}