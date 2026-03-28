package com.tech.cursor.presentation.onboarding


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import org.jetbrains.compose.ui.tooling.preview.Preview

data class Skill(
    val id: String,
    val name: String,
    val icon: String,
    val category: SkillCategory,
)

enum class SkillCategory(val label: String) {
    TECHNOLOGY("Technology"),
    DESIGN("Design"),
    BUSINESS("Business"),
    SOFT_SKILLS("Soft Skills"),
}

val ALL_SKILLS = listOf(
    // Technology
    Skill("SK001", "Android",          "🤖", SkillCategory.TECHNOLOGY),
    Skill("SK002", "Kotlin",           "🟣", SkillCategory.TECHNOLOGY),
    Skill("SK003", "Jetpack Compose",  "🎨", SkillCategory.TECHNOLOGY),
    Skill("SK004", "iOS / Swift",      "🍎", SkillCategory.TECHNOLOGY),
    Skill("SK005", "React Native",     "⚛️", SkillCategory.TECHNOLOGY),
    Skill("SK006", "Flutter",          "🐦", SkillCategory.TECHNOLOGY),
    Skill("SK007", "Node.js",          "🟢", SkillCategory.TECHNOLOGY),
    Skill("SK008", "Python",           "🐍", SkillCategory.TECHNOLOGY),
    Skill("SK009", "Firebase",         "🔥", SkillCategory.TECHNOLOGY),
    Skill("SK010", "REST APIs",        "🔌", SkillCategory.TECHNOLOGY),
    // Design
    Skill("SK011", "UI Design",        "✏️", SkillCategory.DESIGN),
    Skill("SK012", "Figma",            "🖼️", SkillCategory.DESIGN),
    Skill("SK013", "Motion Design",    "🎬", SkillCategory.DESIGN),
    Skill("SK014", "Brand Identity",   "💡", SkillCategory.DESIGN),
    // Business
    Skill("SK015", "Product Strategy", "📊", SkillCategory.BUSINESS),
    Skill("SK016", "Marketing",        "📣", SkillCategory.BUSINESS),
    Skill("SK017", "Sales",            "🤝", SkillCategory.BUSINESS),
    Skill("SK018", "Finance",          "💰", SkillCategory.BUSINESS),
    // Soft Skills
    Skill("SK019", "Leadership",       "👑", SkillCategory.SOFT_SKILLS),
    Skill("SK020", "Communication",    "💬", SkillCategory.SOFT_SKILLS),
    Skill("SK021", "Problem Solving",  "🧩", SkillCategory.SOFT_SKILLS),
    Skill("SK022", "Team Work",        "🫂", SkillCategory.SOFT_SKILLS),
)

@Composable
fun SkillChip(
    skill: Skill,
    isSelected: Boolean,
    onToggle: () -> Unit,
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) TinderRed else BorderIdle,
        animationSpec = tween(200), label = "chipBorder"
    )
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) TinderRed.copy(alpha = 0.12f) else SurfaceDark,
        animationSpec = tween(200), label = "chipBg"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) TinderRed else Color(0xFFCCCCCC),
        animationSpec = tween(200), label = "chipText"
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
                fontWeight = FontWeight.SemiBold,
                fontFamily = /* your font family */ null
            )
        )
    }
}

@Composable
fun BucketTag(skill: Skill, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(TinderRed.copy(alpha = 0.14f))
            .border(1.dp, TinderRed.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
            .padding(start = 8.dp, end = 6.dp, top = 5.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(text = skill.icon, fontSize = 14.sp)
        Text(
            text = skill.name,
            style = TextStyle(
                color = TinderRed,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        // Remove button
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onRemove() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✕",
                style = TextStyle(color = TinderRed, fontSize = 11.sp)
            )
        }
    }
}

// ── Category Section ───────────────────────────────────
@Composable
fun SkillCategorySection(
    category: SkillCategory,
    skills: List<Skill>,
    selectedIds: Set<String>,
    onToggle: (Skill) -> Unit,
) {
    Column {
        Text(
            text = category.label.uppercase(),
            style = TextStyle(
                color = TinderRed,
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
                SkillChip(
                    skill = skill,
                    isSelected = skill.id in selectedIds,
                    onToggle = { onToggle(skill) }
                )
            }
        }
    }
}

@Composable
fun SkillsScreen(
    initialSkills: Set<Skill> = emptySet(),
    onContinue: (Set<Skill>) -> Unit = {},
) {
    var selectedIds by remember { mutableStateOf(initialSkills.map { it.id }.toSet()) }

    val selectedSkills = remember(selectedIds) {
        ALL_SKILLS.filter { it.id in selectedIds }
    }
    val groupedSkills = remember { ALL_SKILLS.groupBy { it.category } }

    val btnAlpha by animateFloatAsState(
        targetValue = if (selectedIds.isNotEmpty()) 1f else 0.35f,
        animationSpec = tween(200), label = "btnAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Glow blob
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = 160.dp, y = (-50).dp)
                .background(
                    Brush.radialGradient(listOf(TinderRed.copy(0.15f), Color.Transparent))
                )
        )

        Column(modifier = Modifier.fillMaxSize()) {

            // ── Header ─────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = 22.dp)) {
                Spacer(Modifier.height(56.dp))

                // Step pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(TinderRed.copy(0.12f))
                        .border(1.dp, TinderRed.copy(0.25f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "STEP 5 OF 6",
                        style = TextStyle(
                            color = TinderRed,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "What are your\nskills?",
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
                    text = "Tap to add skills to your bucket.",
                    style = TextStyle(color = TextSecondary, fontSize = 13.sp)
                )

                Spacer(Modifier.height(14.dp))

                // ── Skill Bucket ───────────────────────
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
                            text = "Your skill bucket is empty...",
                            style = TextStyle(color = Color(0xFF444444), fontSize = 13.sp),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            selectedSkills.forEach { skill ->
                                BucketTag(
                                    skill = skill,
                                    onRemove = {
                                        selectedIds = selectedIds - skill.id
                                    }
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
                            style = TextStyle(color = TinderRed, fontSize = 11.sp),
                            modifier = Modifier.clickable { selectedIds = emptySet() }
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 22.dp)
            ) {
                SkillCategory.entries.forEach { category ->
                    val skills = groupedSkills[category] ?: return@forEach
                    SkillCategorySection(
                        category = category,
                        skills = skills,
                        selectedIds = selectedIds,
                        onToggle = { skill ->
                            selectedIds = if (skill.id in selectedIds)
                                selectedIds - skill.id
                            else
                                selectedIds + skill.id
                        }
                    )
                }
                Spacer(Modifier.height(16.dp))
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 22.dp, vertical = 18.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(TinderGradient)
                    .then(
                        if (selectedIds.isNotEmpty())
                            Modifier.clickable { onContinue(selectedSkills.toSet()) }
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
                    text = "Continue",
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
fun SkillsScreenPreview() {
    SkillsScreen()
}