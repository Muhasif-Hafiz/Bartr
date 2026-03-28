package com.tech.cursor.presentation.Home

import com.tech.cursor.presentation.common.models.ProfileToastType
import com.tech.cursor.presentation.common.models.ProfileUiState
import com.tech.cursor.presentation.common.models.UserProfile


import androidx.compose.foundation.*
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

// ─── Tokens ───────────────────────────────────────────────────────────────────

private object PC {
    val Bg        = Color(0xFF0A0A0D)
    val Surface   = Color(0x0DFFFFFF)
    val Surface2  = Color(0x08FFFFFF)
    val Border    = Color(0x17FFFFFF)
    val Border2   = Color(0x24FFFFFF)
    val W90       = Color(0xE6FFFFFF)
    val W70       = Color(0xB3FFFFFF)
    val W50       = Color(0x80FFFFFF)
    val W35       = Color(0x59FFFFFF)
    val W10       = Color(0x1AFFFFFF)
    val W06       = Color(0x0FFFFFFF)
    val Flame     = Color(0xFFFD5558)
    val Orange    = Color(0xFFFF9A47)
    val Green     = Color(0xFF2ECC71)
    val Blue      = Color(0xFF4A9FFF)
    val FlameGrad = Brush.linearGradient(listOf(Color(0xFFFD5558), Color(0xFFFF9A47)))
}

// ─── ProfileScreen ────────────────────────────────────────────────────────────

@Composable
fun ProfileScreen(

) {
  val   viewModel = remember { ProfileViewModel() }
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.toastMessage) {
        if (state.toastMessage != null) { delay(2200); viewModel.clearToast() }
    }

    Box(Modifier.fillMaxSize().background(PC.Bg)) {

        Column(Modifier.fillMaxSize()) {

            // Top bar
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Bartr",
                    style = TextStyle(brush = PC.FlameGrad, fontSize = 28.sp, fontWeight = FontWeight.Black)
                )
                Box(
                    Modifier.size(40.dp).clip(RoundedCornerShape(13.dp))
                        .background(PC.Surface).border(1.dp, PC.Border, RoundedCornerShape(13.dp))
                        .clickable { viewModel.onSettings() },
                    contentAlignment = Alignment.Center
                ) { Text("⚙️", fontSize = 16.sp) }
            }

            Column(
                Modifier.weight(1f).verticalScroll(rememberScrollState())
            ) {
                HeroSection(state.profile, viewModel::onEditPhoto, viewModel::onEditProfile)
                StatsStrip(state.profile)
                SkillsSection(state.profile, viewModel::onEditSkills)
                AccountCentreGroup(viewModel)
                PreferencesGroup(state, viewModel)
                SupportGroup(viewModel)
                LogoutButton(viewModel::onLogoutClick)
                Text(
                    "Bartr v1.0.0 · Made with ❤ in Kashmir",
                    color    = PC.W10,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp, top = 10.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }

        // Toast
        state.toastMessage?.let { msg ->
            val (bg, border, color) = when (state.toastType) {
                ProfileToastType.INFO    -> Triple(Color(0x334A9FFF), Color(0x664A9FFF), PC.Blue)
                ProfileToastType.SUCCESS -> Triple(Color(0x332ECC71), Color(0x662ECC71), PC.Green)
                ProfileToastType.DANGER  -> Triple(Color(0x33FD5558), Color(0x66FD5558), PC.Flame)
            }
            Box(
                Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(bg).border(1.dp, border, RoundedCornerShape(20.dp))
                    .padding(horizontal = 18.dp, vertical = 10.dp)
            ) { Text(msg, color = color, fontSize = 13.sp, fontWeight = FontWeight.Bold) }
        }

        // Logout dialog
        if (state.showLogoutDialog) {
            LogoutDialog(onDismiss = viewModel::onLogoutDismiss, onConfirm = viewModel::onLogoutConfirm)
        }
    }
}

// ─── Hero ─────────────────────────────────────────────────────────────────────

@Composable
private fun HeroSection(profile: UserProfile, onEditPhoto: () -> Unit, onEditProfile: () -> Unit) {
    Column(
        Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar with gradient ring
        Box(
            Modifier.size(90.dp)
                .clip(CircleShape)
                .background(PC.FlameGrad)
                .padding(2.5.dp)
        ) {
            Box(
                Modifier.fillMaxSize().clip(CircleShape)
                    .background(profile.avatarColor)
                    .border(2.dp, PC.Bg, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(profile.initial, fontSize = 36.sp, fontWeight = FontWeight.Black, color = PC.W90)
            }
            // Online dot
            Box(
                Modifier.size(14.dp).align(Alignment.BottomEnd)
                    .clip(CircleShape).background(PC.Bg).padding(2.dp)
                    .clip(CircleShape).background(PC.Green)
            )
        }

        Spacer(Modifier.height(14.dp))

        // Name + verified
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(profile.name, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            if (profile.isVerified) {
                Box(
                    Modifier.size(18.dp).clip(CircleShape).background(PC.Flame),
                    contentAlignment = Alignment.Center
                ) { Text("✓", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold) }
            }
        }
        Spacer(Modifier.height(3.dp))
        Text(profile.title,   color = PC.W50,  fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(4.dp))
        Text("📍 ${profile.district} · Member since ${profile.memberSince}", color = PC.W35, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(16.dp))

        // Edit profile button
        Box(
            Modifier.clip(RoundedCornerShape(12.dp))
                .background(PC.Surface)
                .border(1.dp, PC.Border2, RoundedCornerShape(12.dp))
                .clickable { onEditProfile() }
                .padding(horizontal = 28.dp, vertical = 9.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("✎", color = PC.W70, fontSize = 14.sp)
                Text("Edit Profile", color = PC.W70, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ─── Stats ────────────────────────────────────────────────────────────────────

@Composable
private fun StatsStrip(profile: UserProfile) {
    Row(
        Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(PC.Surface)
            .border(1.dp, PC.Border, RoundedCornerShape(16.dp))
    ) {
        listOf(
            "${profile.connects}"     to "CONNECTS",
            "${profile.trades}"       to "TRADES",
            "${profile.rating}"       to "RATING",
            "${profile.responseRate}%" to "RESPONSE"
        ).forEachIndexed { i, (value, label) ->
            if (i > 0) Box(Modifier.width(1.dp).height(50.dp).align(Alignment.CenterVertically).background(PC.Border))
            Column(
                Modifier.weight(1f).padding(vertical = 12.dp, horizontal = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(value, style = TextStyle(brush = PC.FlameGrad, fontSize = 19.sp, fontWeight = FontWeight.ExtraBold))
                Text(label, color = PC.W35, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp)
            }
        }
    }
}

// ─── Skills ───────────────────────────────────────────────────────────────────

@Composable
private fun SkillsSection(profile: UserProfile, onEdit: () -> Unit) {
    Column(
        Modifier.padding(horizontal = 16.dp).padding(bottom = 10.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(PC.Surface)
            .border(1.dp, PC.Border, RoundedCornerShape(16.dp))
    ) {
        Row(
            Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp, top = 13.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("MY SKILLS", color = PC.W50, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
            Text("Edit →", color = PC.Orange, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onEdit() })
        }
        Text("OFFERS", color = PC.W35, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp, modifier = Modifier.padding(start = 15.dp, bottom = 7.dp))
        Row(Modifier.padding(start = 15.dp, end = 15.dp, bottom = 12.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            profile.offersSkills.take(4).forEach { skill ->
                Box(
                    Modifier.clip(RoundedCornerShape(20.dp))
                        .background(Color(0x1FFD5558)).border(1.dp, Color(0x33FD5558), RoundedCornerShape(20.dp))
                        .padding(horizontal = 11.dp, vertical = 4.dp)
                ) { Text(skill, color = Color(0xFFFFA08A), fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            }
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(PC.Border))
        Text("WANTS", color = PC.W35, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp, modifier = Modifier.padding(start = 15.dp, top = 8.dp, bottom = 7.dp))
        Row(Modifier.padding(start = 15.dp, end = 15.dp, bottom = 13.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            profile.wantsSkills.take(4).forEach { skill ->
                Box(
                    Modifier.clip(RoundedCornerShape(20.dp))
                        .background(Color(0x1AFF9A47)).border(1.dp, Color(0x33FF9A47), RoundedCornerShape(20.dp))
                        .padding(horizontal = 11.dp, vertical = 4.dp)
                ) { Text(skill, color = Color(0xFFFFB86C), fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            }
        }
    }
}

// ─── Menu Group Helper ────────────────────────────────────────────────────────

@Composable
private fun MenuGroup(label: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        Modifier.padding(horizontal = 16.dp).padding(bottom = 10.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(PC.Surface)
            .border(1.dp, PC.Border, RoundedCornerShape(16.dp))
    ) {
        Text(label, color = PC.W35, fontSize = 9.sp, fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp, modifier = Modifier.padding(start = 15.dp, top = 11.dp, bottom = 7.dp))
        content()
    }
}

@Composable
private fun MenuItem(
    icon: String,
    iconBg: Color,
    title: String,
    sub: String,
    badge: String? = null,
    showDivider: Boolean = true,
    trailing: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    if (showDivider) Box(Modifier.fillMaxWidth().height(1.dp).background(PC.W06))
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 15.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            Modifier.size(34.dp).clip(RoundedCornerShape(10.dp)).background(iconBg),
            contentAlignment = Alignment.Center
        ) { Text(icon, fontSize = 16.sp) }
        Column(Modifier.weight(1f)) {
            Text(title, color = PC.W90, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(1.dp))
            Text(sub,   color = PC.W35, fontSize = 11.sp)
        }
        badge?.let {
            Box(
                Modifier.clip(RoundedCornerShape(10.dp)).background(PC.FlameGrad).padding(horizontal = 8.dp, vertical = 2.dp)
            ) { Text(it, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold) }
        }
        trailing?.invoke() ?: Text("›", color = PC.W35, fontSize = 14.sp)
    }
}

@Composable
private fun ToggleSwitch(enabled: Boolean, onToggle: () -> Unit) {
    Box(
        Modifier.width(40.dp).height(22.dp)
            .clip(RoundedCornerShape(11.dp))
            .background(if (enabled) PC.FlameGrad else Brush.linearGradient(listOf(PC.W10, PC.W10)))
            .clickable(onClick = onToggle),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            Modifier.padding(start = if (enabled) 21.dp else 3.dp)
                .size(16.dp).clip(CircleShape).background(Color.White)
        )
    }
}

// ─── Account Centre ───────────────────────────────────────────────────────────

@Composable
private fun AccountCentreGroup(viewModel: ProfileViewModel) {
    MenuGroup("ACCOUNT CENTRE") {
        MenuItem("👤", Color(0x1F4A9FFF), "Personal Information",  "Name, phone, email, district", showDivider = false, onClick = viewModel::onPersonalInfo)
        MenuItem("⚡", Color(0x1FFF9A47), "Skill Portfolio",        "Offers, wants, experience level", onClick = viewModel::onSkillPortfolio)
        MenuItem("✓",  Color(0x1F2ECC71), "Verification & Trust",   "ID, Aadhaar, skill certificates", badge = "VERIFIED", onClick = viewModel::onVerification)
        MenuItem("💳", Color(0x1FFD5558), "Payment & Wallet",       "UPI, bank account, trade credits", onClick = viewModel::onPaymentWallet)
    }
}

// ─── Preferences ──────────────────────────────────────────────────────────────

@Composable
private fun PreferencesGroup(state: ProfileUiState, viewModel: ProfileViewModel) {
    MenuGroup("PREFERENCES") {
        MenuItem("🔔", Color(0x1FFF9A47), "Push Notifications", "New matches, messages, trades", showDivider = false,
            trailing = { ToggleSwitch(state.notificationsEnabled, viewModel::onToggleNotifications) },
            onClick = viewModel::onToggleNotifications)
        MenuItem("📍", Color(0x1F4A9FFF), "Show My Location",   "Visible to potential matches",
            trailing = { ToggleSwitch(state.locationEnabled, viewModel::onToggleLocation) },
            onClick = viewModel::onToggleLocation)
        MenuItem("👁", Color(0x1F6B2FA0), "Profile Visibility",  "Who can see your profile",
            trailing = { ToggleSwitch(state.profileVisible, viewModel::onToggleVisibility) },
            onClick = viewModel::onToggleVisibility)
        MenuItem("🎯", Color(0x1F2ECC71), "Discovery Settings",  "Distance, skill filters, districts",
            onClick = viewModel::onDiscoverySettings)
    }
}

// ─── Support ─────────────────────────────────────────────────────────────────

@Composable
private fun SupportGroup(viewModel: ProfileViewModel) {
    MenuGroup("SUPPORT & LEGAL") {
        MenuItem("❓", Color(0x1F4A9FFF), "Help Centre",     "FAQs, guides, how trading works", showDivider = false, onClick = viewModel::onHelpCentre)
        MenuItem("💬", Color(0x1FFF9A47), "Send Feedback",   "Report a bug or suggest a feature", onClick = viewModel::onSendFeedback)
        MenuItem("🔒", PC.Surface2,       "Privacy Policy",  "How we use your data",  onClick = viewModel::onPrivacyPolicy)
        MenuItem("📄", PC.Surface2,       "Terms of Service","Rules of the Bartr community", onClick = viewModel::onTermsOfService)
    }
}

// ─── Logout Button ────────────────────────────────────────────────────────────

@Composable
private fun LogoutButton(onClick: () -> Unit) {
    Row(
        Modifier.padding(horizontal = 16.dp).padding(top = 4.dp, bottom = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0x12FD5558))
            .border(1.dp, Color(0x33FD5558), RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("🚪", fontSize = 16.sp)
        Spacer(Modifier.width(8.dp))
        Text("Log Out", color = PC.Flame, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 0.3.sp)
    }
}

// ─── Logout Dialog ────────────────────────────────────────────────────────────

@Composable
private fun LogoutDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            Modifier.clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF141417))
                .border(1.dp, PC.Border2, RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            // Handle bar
            Box(Modifier.width(36.dp).height(4.dp).clip(RoundedCornerShape(2.dp)).background(PC.W10).align(Alignment.CenterHorizontally))
            Spacer(Modifier.height(20.dp))
            Text("Log out of Bartr?", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(6.dp))
            Text(
                "You'll need to sign in again to access your matches and connections. Your data stays safe.",
                color = PC.W50, fontSize = 13.sp, lineHeight = 19.sp
            )
            Spacer(Modifier.height(24.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                // Cancel
                Box(
                    Modifier.weight(1f).clip(RoundedCornerShape(13.dp))
                        .background(PC.Surface).border(1.dp, PC.Border2, RoundedCornerShape(13.dp))
                        .clickable(onClick = onDismiss).padding(vertical = 13.dp),
                    contentAlignment = Alignment.Center
                ) { Text("Cancel", color = PC.W70, fontSize = 14.sp, fontWeight = FontWeight.Bold) }
                // Confirm
                Box(
                    Modifier.weight(1f).clip(RoundedCornerShape(13.dp))
                        .background(Brush.linearGradient(listOf(PC.Flame, Color(0xB3FD5558))))
                        .clickable(onClick = onConfirm).padding(vertical = 13.dp),
                    contentAlignment = Alignment.Center
                ) { Text("Yes, Log Out", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold) }
            }
        }
    }
}