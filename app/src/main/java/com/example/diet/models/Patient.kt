package com.diet.system.models

import com.diet.system.enums.Gender

data class Patient(
    val name: String,
    val age: Int,
    val gender: Gender,
    val weightKg: Double,
    val heightCm: Double
)