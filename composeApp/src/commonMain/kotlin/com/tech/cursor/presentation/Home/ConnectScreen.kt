package com.tech.cursor.presentation.Home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tech.cursor.presentation.common.models.ConnectFilter
import com.tech.cursor.presentation.common.models.Connection
import com.tech.cursor.presentation.common.models.OnlineStatus
import com.tech.cursor.presentation.common.models.TradeStatus
import com.tech.cursor.presentation.onboarding.viewmodel.OnboardingViewModel
import kotlinx.coroutines.delay


private object CColor {
    val Bg        = Color(0xFF0A0A0D)
    val Surface   = Color(0x0DFFFFFF)
    val Surface2  = Color(0x08FFFFFF)
    val Border    = Color(0x17FFFFFF)
    val Border2   = Color(0x24FFFFFF)
    val W90       = Color(0xE6FFFFFF)
    val W70       = Color(0xB3FFFFFF)
    val W50       = Color(0x80FFFFFF)
    val W35       = Color(0x59FFFFFF)
    val W20       = Color(0x33FFFFFF)
    val W10       = Color(0x1AFFFFFF)
    val W06       = Color(0x0FFFFFFF)
    val Flame     = Color(0xFFFD5558)
    val Orange    = Color(0xFFFF9A47)
    val Green     = Color(0xFF2ECC71)
    val Blue      = Color(0xFF4A9FFF)
    val FlameGrad = Brush.linearGradient(listOf(Color(0xFFFD5558), Color(0xFFFF9A47)))
}

// ─── ConnectScreen ────────────────────────────────────────────────────────────

@Composable
fun ConnectScreen(

) {
    val viewModel = remember { ConnectViewModel() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.toastMessage) {
        if (uiState.toastMessage != null) {
            delay(2200)
            viewModel.clearToast()
        }
    }

    Box(Modifier.fillMaxSize().background(CColor.Bg)) {
        Column(Modifier.fillMaxSize()) {

            ConnectTopBar()

            LazyColumn(Modifier.weight(1f)) {
                item { HeaderSection(uiState) }
                item { StatsStrip(uiState) }
                item { SearchBar(uiState.searchQuery, viewModel::onSearchQuery) }
                item { FilterRow(uiState.activeFilter, viewModel::onFilterChange) }
                item {
                    Text(
                        "RECENT CONNECTIONS",
                        color         = CColor.W35,
                        fontSize      = 10.sp,
                        fontWeight    = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        modifier      = Modifier.padding(start = 22.dp, bottom = 10.dp)
                    )
                }
                if (uiState.filtered.isEmpty()) {
                    item {
                        Box(
                            Modifier.fillMaxWidth().padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No connections found", color = CColor.W35, fontSize = 14.sp)
                        }
                    }
                } else {
                    items(uiState.filtered, key = { it.id }) { conn ->
                        ConnectionCard(
                            connection   = conn,
                            onMessage    = { viewModel.onMessageClick(conn) },
                            onTrade      = { viewModel.onTradeClick(conn) },
                            onProfile    = { viewModel.onProfileClick(conn) }
                        )
                    }
                    item {
                        Text(
                            "You've seen all your connections · Discover more →",
                            color     = CColor.W35,
                            fontSize  = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier  = Modifier.padding(24.dp).fillMaxWidth()
                        )
                    }
                }
            }
        }

        // Toast
        uiState.toastMessage?.let { msg ->
            Box(
                Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0x334A9FFF))
                    .border(1.dp, Color(0x664A9FFF), RoundedCornerShape(20.dp))
                    .padding(horizontal = 18.dp, vertical = 10.dp)
            ) {
                Text(msg, color = CColor.Blue, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ─── Top Bar ─────────────────────────────────────────────────────────────────

@Composable
private fun ConnectTopBar() {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Bartr",
            style = TextStyle(brush = CColor.FlameGrad, fontSize = 28.sp, fontWeight = FontWeight.Black)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            IconBtn("🔍") {}
            IconBtn("⚙️") {}
        }
    }
}

@Composable
private fun IconBtn(icon: String, onClick: () -> Unit) {
    Box(
        Modifier.size(40.dp).clip(RoundedCornerShape(13.dp))
            .background(CColor.Surface)
            .border(1.dp, CColor.Border, RoundedCornerShape(13.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) { Text(icon, fontSize = 16.sp) }
}

// ─── Header Section ───────────────────────────────────────────────────────────

@Composable
private fun HeaderSection(uiState: ConnectUiState) {
    Column(Modifier.padding(start = 22.dp, end = 22.dp, bottom = 14.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("My Connections", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
            Box(
                Modifier.clip(RoundedCornerShape(20.dp)).background(CColor.FlameGrad)
                    .padding(horizontal = 10.dp, vertical = 3.dp)
            ) {
                Text("${uiState.totalConnections}", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(
            "People you've matched & traded skills with",
            color = CColor.W50, fontSize = 13.sp, fontWeight = FontWeight.Medium
        )
    }
}

// ─── Stats Strip ──────────────────────────────────────────────────────────────

@Composable
private fun StatsStrip(uiState: ConnectUiState) {
    Row(
        Modifier.padding(horizontal = 22.dp).padding(bottom = 18.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CColor.Surface)
            .border(1.dp, CColor.Border, RoundedCornerShape(16.dp))
    ) {
        StatCell("${uiState.totalConnections}",       "CONNECTED",   Modifier.weight(1f))
        Box(Modifier.width(1.dp).height(50.dp).align(Alignment.CenterVertically).background(CColor.Border))
        StatCell("${uiState.totalTrades}",            "TRADES DONE", Modifier.weight(1f))
        Box(Modifier.width(1.dp).height(50.dp).align(Alignment.CenterVertically).background(CColor.Border))
        StatCell("${uiState.avgRating}", "AVG RATING",  Modifier.weight(1f))
        Box(Modifier.width(1.dp).height(50.dp).align(Alignment.CenterVertically).background(CColor.Border))
        StatCell("${uiState.onlineCount}",            "ACTIVE NOW",  Modifier.weight(1f))
    }
}

@Composable
private fun StatCell(value: String, label: String, modifier: Modifier) {
    Column(
        modifier.padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            value,
            style      = TextStyle(brush = CColor.FlameGrad, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        )
        Text(label, color = CColor.W35, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp)
    }
}

// ─── Search Bar ───────────────────────────────────────────────────────────────

@Composable
private fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    Box(
        Modifier.padding(horizontal = 22.dp).padding(bottom = 14.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CColor.Surface)
            .border(1.dp, CColor.Border, RoundedCornerShape(14.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("🔍", fontSize = 15.sp, color = CColor.W35)
            Box(Modifier.weight(1f)) {
                if (query.isEmpty()) {
                    Text(
                        "Search connections by name or skill...",
                        color = CColor.W35, fontSize = 14.sp
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
                    cursorBrush = SolidColor(CColor.Flame),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// ─── Filter Row ───────────────────────────────────────────────────────────────

@Composable
private fun FilterRow(active: ConnectFilter, onFilter: (ConnectFilter) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 22.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(bottom = 14.dp)
    ) {
        val filters = listOf(
            ConnectFilter.ALL          to "All",
            ConnectFilter.ONLINE       to "Online",
            ConnectFilter.NEW_MATCH    to "New Match",
            ConnectFilter.ACTIVE_TRADE to "Active Trade",
            ConnectFilter.COMPLETED    to "Completed"
        )
        items(filters) { (filter, label) ->
            val isActive = active == filter
            Box(
                Modifier.clip(RoundedCornerShape(20.dp))
                    .background(
                        if (isActive) Brush.linearGradient(
                            listOf(Color(0x33FD5558), Color(0x33FF9A47))
                        ) else Brush.linearGradient(listOf(CColor.Surface, CColor.Surface))
                    )
                    .border(
                        1.dp,
                        if (isActive) Color(0x66FD5558) else CColor.Border,
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { onFilter(filter) }
                    .padding(horizontal = 15.dp, vertical = 7.dp)
            ) {
                Text(
                    label,
                    color      = if (isActive) CColor.Orange else CColor.W50,
                    fontSize   = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.3.sp
                )
            }
        }
    }
}

// ─── Connection Card ──────────────────────────────────────────────────────────

@Composable
private fun ConnectionCard(
    connection: Connection,
    onMessage: () -> Unit,
    onTrade: () -> Unit,
    onProfile: () -> Unit
) {
    val cardBg = if (connection.isNewMatch) Color(0x0AFD5558) else CColor.Surface2
    val cardBorder = if (connection.isNewMatch) Color(0x40FD5558) else CColor.Border

    Column(
        Modifier.padding(horizontal = 16.dp).padding(bottom = 10.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(cardBg)
            .border(1.dp, cardBorder, RoundedCornerShape(18.dp))
    ) {
        // Main row
        Row(
            Modifier.fillMaxWidth().padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            // Avatar + status dot
            Box(Modifier.size(54.dp)) {
                Box(
                    Modifier.fillMaxSize().clip(CircleShape)
                        .background(connection.avatarColor)
                        .border(2.dp, CColor.Border2, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(connection.initial, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = CColor.W90)
                }
                // Status dot
                val dotColor = when (connection.status) {
                    OnlineStatus.ONLINE  -> CColor.Green
                    OnlineStatus.AWAY    -> CColor.Orange
                    OnlineStatus.OFFLINE -> CColor.W35
                }
                Box(
                    Modifier.size(13.dp).align(Alignment.BottomEnd)
                        .clip(CircleShape).background(CColor.Bg)
                        .padding(2.dp).clip(CircleShape).background(dotColor)
                )
            }

            // Name + message
            Column(Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(bottom = 2.dp)
                ) {
                    Text(
                        connection.name,
                        color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (connection.isVerified) {
                        Box(
                            Modifier.size(16.dp).clip(CircleShape).background(CColor.Flame),
                            contentAlignment = Alignment.Center
                        ) { Text("✓", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.ExtraBold) }
                    }
                    if (connection.isNewMatch) {
                        Box(
                            Modifier.clip(RoundedCornerShape(10.dp)).background(CColor.FlameGrad)
                                .padding(horizontal = 7.dp, vertical = 2.dp)
                        ) {
                            Text("NEW", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold)
                        }
                    }
                }
                Text(
                    "📍 ${connection.district} · ${connection.distanceKm}km · ${connection.title}",
                    color = CColor.W50, fontSize = 11.sp, fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    connection.lastMessage,
                    color = if (connection.unreadCount > 0) CColor.W70 else CColor.W50,
                    fontSize = 12.sp,
                    fontWeight = if (connection.unreadCount > 0) FontWeight.SemiBold else FontWeight.Normal,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }

            // Time + unread badge
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(connection.lastTime, color = CColor.W35, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                if (connection.unreadCount > 0) {
                    Box(
                        Modifier.size(18.dp).clip(CircleShape).background(CColor.FlameGrad),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "${connection.unreadCount}",
                            color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }

        // Trade info strip
        Box(Modifier.fillMaxWidth().height(1.dp).background(CColor.W06))
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 15.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Offers pill
            Box(
                Modifier.clip(RoundedCornerShape(20.dp))
                    .background(Color(0x1AFD5558))
                    .border(1.dp, Color(0x33FD5558), RoundedCornerShape(20.dp))
                    .padding(horizontal = 9.dp, vertical = 3.dp)
            ) {
                Text("↑ ${connection.offersSkill}", color = Color(0xFFFFA08A), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Text("⇄", color = CColor.W35, fontSize = 11.sp)
            // Wants pill
            Box(
                Modifier.clip(RoundedCornerShape(20.dp))
                    .background(Color(0x1AFF9A47))
                    .border(1.dp, Color(0x33FF9A47), RoundedCornerShape(20.dp))
                    .padding(horizontal = 9.dp, vertical = 3.dp)
            ) {
                Text("↓ ${connection.wantsSkill}", color = Color(0xFFFFB86C), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.weight(1f))
            val tradeLabel = when (connection.tradeStatus) {
                TradeStatus.ACTIVE    -> "🔄 Active"
                TradeStatus.COMPLETED -> "✅ Done"
            }
            Text(
                "$tradeLabel · ${connection.trades} trades · ★ ${connection.rating}",
                color = CColor.W35, fontSize = 10.sp, fontWeight = FontWeight.SemiBold
            )
        }

        // Action buttons
        Box(Modifier.fillMaxWidth().height(1.dp).background(CColor.W06))
        Row(Modifier.fillMaxWidth()) {
            ActionBtn("💬", "Message", CColor.Blue,   Modifier.weight(1f), onMessage)
            Box(Modifier.width(1.dp).height(40.dp).align(Alignment.CenterVertically).background(CColor.W06))
            ActionBtn("⚡", "Trade",   CColor.Orange, Modifier.weight(1f), onTrade)
            Box(Modifier.width(1.dp).height(40.dp).align(Alignment.CenterVertically).background(CColor.W06))
            ActionBtn("👤", "Profile", CColor.W50,    Modifier.weight(1f), onProfile)
        }
    }
}

@Composable
private fun ActionBtn(
    icon: String,
    label: String,
    color: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier.clickable(onClick = onClick).padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 14.sp)
        Spacer(Modifier.width(5.dp))
        Text(label, color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.3.sp)
    }
}