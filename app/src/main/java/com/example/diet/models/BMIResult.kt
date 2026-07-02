package com.diet.system.models

import com.diet.system.enums.BMICategory

data class BMIResult(
    val bmi: Double,
    val category: BMICategory,
    val idealWeightMin: Double,
    val idealWeightMax: Double
)