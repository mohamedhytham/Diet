package com.example.diet.calculators
import com.diet.system.enums.BMICategory
import com.diet.system.models.MacroTargets
import com.diet.system.models.Patient
import com.example.diet.constants.FoodExchangeValues
import com.example.diet.enums.DietType

object MacroCalculator {

    fun calculate(
        patient: Patient,
        bmiCategory: BMICategory,
        dietType: DietType = DietType.STANDARD
    ): MacroTargets {
        val multiplier = CalorieCalculator.getMultiplier(bmiCategory)
        val deficit = CalorieCalculator.getDeficit(bmiCategory)
        val gross = patient.weightKg * multiplier
        val total = gross - deficit

        val carbCal = total * dietType.carbPercent / 100.0
        val proteinCal = total * dietType.proteinPercent / 100.0
        val fatCal = total * dietType.fatPercent / 100.0

        return MacroTargets(
            totalCalories = total,
            grossCalories = gross,
            deficit = deficit,
            caloriesPerKg = multiplier,
            carbCalories = carbCal,
            carbGrams = carbCal / FoodExchangeValues.CARB_CAL_PER_G,
            proteinCalories = proteinCal,
            proteinGrams = proteinCal / FoodExchangeValues.PROTEIN_CAL_PER_G,
            fatCalories = fatCal,
            fatGrams = fatCal / FoodExchangeValues.FAT_CAL_PER_G
        )
    }
}