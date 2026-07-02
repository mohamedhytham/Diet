package com.example.diet.models

import com.diet.system.models.BMIResult
import com.diet.system.models.FoodExchangeServes
import com.diet.system.models.MacroTargets
import com.diet.system.models.Meal
import com.diet.system.models.Patient

data class DietPlan(
    val patient: Patient,
    val bmiResult: BMIResult,
    val macroTargets: MacroTargets,
    val exchangeServes: FoodExchangeServes,
    val exchangeMacros: FoodExchangeMacros,
    val meals: List<Meal>
)

