package com.tech.cursor.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Skill(
    val skillId: Int = 0,
    val skillName: String? = "",
    val skillRes: Int
)