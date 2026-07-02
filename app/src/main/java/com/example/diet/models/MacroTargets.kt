package com.diet.system.models

data class MacroTargets(
    val totalCalories: Double,
    val grossCalories: Double,
    val deficit: Double,
    val caloriesPerKg: Double,
    val carbCalories: Double,
    val carbGrams: Double,
    val proteinCalories: Double,
    val proteinGrams: Double,
    val fatCalories: Double,
    val fatGrams: Double
)