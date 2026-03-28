package com.tech.cursor.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDetails(
    val userName: String,
    val age: String,
    val number: String,
    val address: String,
    val skillsHave: List<Skill>,
    val skillsWant: List<Skill>
)