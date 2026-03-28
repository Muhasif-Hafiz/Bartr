package com.tech.cursor.presentation.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

// ── Purple accent tokens (want-to-learn palette) ───────
private val PurpleAccent   = Color(0xFFA855F7)
private val PurpleDark     = Color(0xFF7C3AED)
private val PurpleGradient = Brush.horizontalGradient(listOf(PurpleAccent, PurpleDark))

val WANT_SKILLS = listOf(
    // Technology
    Skill("WK001", "Android",          "🤖", SkillCategory.TECHNOLOGY),
    Skill("WK002", "Kotlin",           "🟣", SkillCategory.TECHNOLOGY),
    Skill("WK003", "Jetpack Compose",  "🎨", SkillCategory.TECHNOLOGY),
    Skill("WK004", "iOS / Swift",      "🍎", SkillCategory.TECHNOLOGY),
    Skill("WK005", "React Native",     "⚛️", SkillCategory.TECHNOLOGY),
    Skill("WK006", "Flutter",          "🐦", SkillCategory.TECHNOLOGY),
    Skill("WK007", "Node.js",          "🟢", SkillCategory.TECHNOLOGY),
    Skill("WK008", "Python",           "🐍", SkillCategory.TECHNOLOGY),
    Skill("WK009", "Machine Learning", "🧠", SkillCategory.TECHNOLOGY),
    Skill("WK010", "DevOps",           "⚙️", SkillCategory.TECHNOLOGY),
    // Design
    Skill("WK011", "UI Design",        "✏️", SkillCategory.DESIGN),
    Skill("WK012", "Figma",            "🖼️", SkillCategory.DESIGN),
    Skill("WK013", "3D Design",        "🧊", SkillCategory.DESIGN),
    Skill("WK014", "Motion Design",    "🎬", SkillCategory.DESIGN),
    Skill("WK015", "Photography",      "📷", SkillCategory.DESIGN),
    // Business
    Skill("WK016", "Product Strategy", "📊", SkillCategory.BUSINESS),
    Skill("WK017", "Marketing",        "📣", SkillCategory.BUSINESS),
    Skill("WK018", "Finance",          "💰", SkillCategory.BUSINESS),
    Skill("WK019", "Public Speaking",  "🎤", SkillCategory.BUSINESS),
    // Soft Skills
    Skill("WK020", "Leadership",       "👑", SkillCategory.SOFT_SKILLS),
    Skill("WK021", "Communication",    "💬", SkillCategory.SOFT_SKILLS),
    Skill("WK022", "Problem Solving",  "🧩", SkillCategory.SOFT_SKILLS),
    Skill("WK023", "Negotiation",      "🫱", SkillCategory.SOFT_SKILLS),
)

// ── Want Skill Chip ────────────────────────────────────
@Composable
fun WantSkillChip(
    skill: Skill,
    isSelected: Boolean,
    onToggle: () -> Unit,
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) PurpleAccent else BorderIdle,
        animationSpec = tween(200), label = "wantChipBorder"
    )
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) PurpleAccent.copy(alpha = 0.12f) else SurfaceDark,
        animationSpec = tween(200), label = "wantChipBg"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) PurpleAccent else Color(0xFFCCCCCC),
        animationSpec = tween(200), label = "wantChipText"
    )

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(bgColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(24.dp))
            .clickable { onToggle() }
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(text = skill.icon, fontSize = 16.sp)
        Text(
            text = skill.name,
            style = TextStyle(
                color = textColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun WantBucketTag(skill: Skill, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(PurpleAccent.copy(alpha = 0.14f))
            .border(1.dp, PurpleAccent.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
            .padding(start = 8.dp, end = 6.dp, top = 5.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(text = skill.icon, fontSize = 14.sp)
        Text(
            text = skill.name,
            style = TextStyle(
                color = PurpleAccent,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onRemove() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "✕", style = TextStyle(color = PurpleAccent, fontSize = 11.sp))
        }
    }
}

// ── Skills Want Screen ─────────────────────────────────
@Composable
fun SkillsWantScreen(
    initialSkills: Set<Skill> = emptySet(),
    onFinish: (List<Skill>) -> Unit = {},
) {
    var selectedIds by remember { mutableStateOf(initialSkills.map { it.id }.toSet()) }

    val selectedSkills = remember(selectedIds) {
        WANT_SKILLS.filter { it.id in selectedIds }
    }
    val groupedSkills = remember { WANT_SKILLS.groupBy { it.category } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Purple glow blob (top-left for visual distinction from Skills screen)
        Box(
            modifier = Modifier
                .size(220.dp)
                .offset((-60).dp, (-60).dp)
                .background(
                    Brush.radialGradient(listOf(PurpleAccent.copy(0.15f), Color.Transparent))
                )
        )

        Column(modifier = Modifier.fillMaxSize()) {

            // ── Header ─────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = 22.dp)) {
                Spacer(Modifier.height(56.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(PurpleAccent.copy(0.12f))
                        .border(1.dp, PurpleAccent.copy(0.28f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "STEP 6 OF 6",
                        style = TextStyle(
                            color = PurpleAccent,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Skills you\nwant to learn?",
                    style = TextStyle(
                        color = TextPrimary,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 34.sp,
                        letterSpacing = (-0.6).sp
                    )
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Tap skills you'd love to pick up.",
                    style = TextStyle(color = TextSecondary, fontSize = 13.sp)
                )

                Spacer(Modifier.height(14.dp))

                // ── Wish Bucket ────────────────────────
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF0D0D18))
                        .border(1.5.dp, BorderIdle, RoundedCornerShape(16.dp))
                        .padding(10.dp)
                        .defaultMinSize(minHeight = 52.dp)
                ) {
                    if (selectedSkills.isEmpty()) {
                        Text(
                            text = "Your wish bucket is empty...",
                            style = TextStyle(color = Color(0xFF444444), fontSize = 13.sp),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            selectedSkills.forEach { skill ->
                                WantBucketTag(
                                    skill = skill,
                                    onRemove = { selectedIds = selectedIds - skill.id }
                                )
                            }
                        }
                    }
                }

                // Count + Clear row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${selectedIds.size} skill${if (selectedIds.size != 1) "s" else ""} selected",
                        style = TextStyle(color = Color(0xFF555555), fontSize = 11.sp)
                    )
                    if (selectedIds.isNotEmpty()) {
                        Text(
                            text = "Clear all",
                            style = TextStyle(color = PurpleAccent, fontSize = 11.sp),
                            modifier = Modifier.clickable { selectedIds = emptySet() }
                        )
                    }
                }
            }

            // ── Scrollable Skill List ──────────────────
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 22.dp)
            ) {
                SkillCategory.entries.forEach { category ->
                    val skills = groupedSkills[category] ?: return@forEach

                    // Category label in purple
                    Text(
                        text = category.label.uppercase(),
                        style = TextStyle(
                            color = PurpleAccent,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.4.sp
                        ),
                        modifier = Modifier.padding(top = 16.dp, bottom = 10.dp)
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        skills.forEach { skill ->
                            WantSkillChip(
                                skill = skill,
                                isSelected = skill.id in selectedIds,
                                onToggle = {
                                    selectedIds = if (skill.id in selectedIds)
                                        selectedIds - skill.id
                                    else
                                        selectedIds + skill.id
                                }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            // ── Finish CTA ─────────────────────────────
            Box(
                modifier = Modifier
                    .padding(horizontal = 22.dp, vertical = 18.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(PurpleGradient)
                    .then(
                        if (selectedIds.isNotEmpty())
                            Modifier.clickable { onFinish(selectedSkills) }
                        else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (selectedIds.isEmpty()) {
                    Box(
                        Modifier
                            .matchParentSize()
                            .background(BackgroundDark.copy(0.5f))
                    )
                }
                Text(
                    text = "Finish Setup",
                    style = TextStyle(
                        color = TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.6.sp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SkillsWantScreenPreview() {
    SkillsWantScreen()
}