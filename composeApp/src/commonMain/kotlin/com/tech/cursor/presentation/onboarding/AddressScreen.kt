package com.tech.cursor.presentation.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cursor.composeapp.generated.resources.Res
import cursor.composeapp.generated.resources.ic_arrow_down
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

// ── All J&K Districts ──────────────────────────────────
val JK_DISTRICTS = listOf(
    "Anantnag", "Bandipora", "Baramulla", "Budgam", "Doda",
    "Ganderbal", "Jammu", "Kathua", "Kishtwar", "Kulgam",
    "Kupwara", "Poonch", "Pulwama", "Rajouri", "Ramban",
    "Reasi", "Samba", "Shopian", "Srinagar", "Udhampur"
)

// ── District Dropdown ──────────────────────────────────
@Composable
fun DistrictDropdown(
    selectedDistrict: String,
    onDistrictSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredDistricts = remember(searchQuery) {
        if (searchQuery.isBlank()) JK_DISTRICTS
        else JK_DISTRICTS.filter {
            it.contains(searchQuery, ignoreCase = true)
        }
    }

    val borderColor by animateColorAsState(
        targetValue = if (expanded) TinderRed else BorderIdle,
        animationSpec = tween(250), label = "distBorder"
    )
    val labelColor by animateColorAsState(
        targetValue = if (expanded) TinderRed else TextSecondary,
        animationSpec = tween(250), label = "distLabel"
    )
    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(250), label = "chevron"
    )
    val accentWidth by animateFloatAsState(
        targetValue = if (expanded || selectedDistrict.isNotEmpty()) 0.4f else 0f,
        animationSpec = tween(300), label = "distAccent"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        // Label
        Row {
            Text(
                text = "DISTRICT",
                style = TextStyle(
                    color = labelColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.6.sp
                )
            )
            Text(
                text = " *",
                style = TextStyle(color = TinderRed, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            )
        }

        Spacer(Modifier.height(8.dp))

        // Selector box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(SurfaceDark)
                .border(1.5.dp, borderColor, RoundedCornerShape(14.dp))
                .clickable {
                    expanded = !expanded
                    if (!expanded) searchQuery = ""
                }
                .padding(horizontal = 14.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedDistrict.ifEmpty { "Select your district" },
                    style = TextStyle(
                        color = if (selectedDistrict.isEmpty()) TextSecondary.copy(0.45f)
                        else TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_down),
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(chevronRotation)
                )
            }
        }

        // Gradient accent bar
        Spacer(Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(accentWidth)
                .height(2.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(TinderGradient)
        )

        // Dropdown list
        if (expanded) {
            Spacer(Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.5.dp, TinderRed, RoundedCornerShape(14.dp))
                    .background(SurfaceDark)
            ) {
                Column {
                    // Search field
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        singleLine = true,
                        cursorBrush = SolidColor(TinderRed),
                        textStyle = TextStyle(
                            color = TextPrimary,
                            fontSize = 13.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0F1929))
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        decorationBox = { inner ->
                            if (searchQuery.isEmpty()) {
                                Text(
                                    "Search district...",
                                    style = TextStyle(
                                        color = TextSecondary.copy(0.45f),
                                        fontSize = 13.sp
                                    )
                                )
                            }
                            inner()
                        }
                    )

                    Divider(color = BorderIdle, thickness = 0.5.dp)

                    LazyColumn(modifier = Modifier.heightIn(max = 180.dp)) {
                        if (filteredDistricts.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "No districts found",
                                        style = TextStyle(color = TextSecondary, fontSize = 13.sp)
                                    )
                                }
                            }
                        } else {
                            items(filteredDistricts) { district ->
                                var hovered by remember { mutableStateOf(false) }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            if (district == selectedDistrict)
                                                TinderRed.copy(0.15f)
                                            else Color.Transparent
                                        )
                                        .clickable {
                                            onDistrictSelected(district)
                                            expanded = false
                                            searchQuery = ""
                                        }
                                        .padding(horizontal = 14.dp, vertical = 12.dp)
                                ) {
                                    Text(
                                        text = district,
                                        style = TextStyle(
                                            color = if (district == selectedDistrict)
                                                TinderRed else TextPrimary,
                                            fontSize = 14.sp,
                                            fontWeight = if (district == selectedDistrict)
                                                FontWeight.SemiBold else FontWeight.Normal
                                        )
                                    )
                                }
                                if (district != filteredDistricts.last()) {
                                    Divider(color = BorderIdle.copy(0.4f), thickness = 0.5.dp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddressScreen(
     initialAddress : String,
    initialPinCode : String,
     initialDistrict : String,

    onContinue: (
    address: String,
    district: String,
    pincode: String) -> Unit = { _, _, _ -> }) {
    var address by remember { mutableStateOf(initialAddress) }
    var selectedDistrict by remember { mutableStateOf(initialDistrict) }
    var pincode by remember { mutableStateOf(initialPinCode) }

    val isValid = address.isNotBlank() && selectedDistrict.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Ambient glow blob
        Box(
            modifier = Modifier
                .size(260.dp)
                .offset((-70).dp, (-70).dp)
                .background(
                    Brush.radialGradient(listOf(TinderRed.copy(0.18f), Color.Transparent))
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)
        ) {
            Spacer(Modifier.height(60.dp))

            // Step pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(TinderRed.copy(0.12f))
                    .border(1.dp, TinderRed.copy(0.25f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "STEP 3 OF 6",
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
                text = "Where do\nyou live?",
                style = TextStyle(
                    color = TextPrimary,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 36.sp,
                    letterSpacing = (-0.8).sp
                )
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Help us find people near you.",
                style = TextStyle(color = TextSecondary, fontSize = 13.sp)
            )

            Spacer(Modifier.height(28.dp))

            // Address field (mandatory)
            OnboardingTextField(
                value = address,
                onValueChange = { address = it },
                label = "Address *",
                placeholder = "House no., Street, Area",
                imeAction = ImeAction.Next
            )

            Spacer(Modifier.height(20.dp))

            // District dropdown (mandatory)
            DistrictDropdown(
                selectedDistrict = selectedDistrict,
                onDistrictSelected = { selectedDistrict = it }
            )

            Spacer(Modifier.height(20.dp))

            // Pincode (optional)
            OnboardingTextField(
                value = pincode,
                onValueChange = { if (it.length <= 6) pincode = it },
                label = "Pincode",
                placeholder = "e.g. 190001",
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
                onImeAction = { if (isValid) onContinue(address, selectedDistrict, pincode) }
            )

            Spacer(Modifier.weight(1f))

            // CTA
            val btnAlpha by animateFloatAsState(
                targetValue = if (isValid) 1f else 0.35f,
                animationSpec = tween(200), label = "btnAlpha"
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(TinderGradient)
                    .then(
                        if (isValid) Modifier.clickable { onContinue(address, selectedDistrict, pincode) }
                        else Modifier
                    )
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                if (!isValid) {
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

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddressScreenPreview() {
    AddressScreen("", "", "")
}