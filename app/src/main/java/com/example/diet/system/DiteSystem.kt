package com.example.diet.system

import android.content.Context
import com.diet.system.models.Patient
import com.example.diet.models.*
import com.example.diet.calculators.BMICalculator
import com.example.diet.calculators.FoodExchangeCalculator
import com.example.diet.calculators.MacroCalculator
import com.example.diet.constants.FoodExchangeValues
import com.example.diet.generators.MealPlanGenerator
import com.example.diet.enums.DietType

class DietSystem {

    fun createDietPlan(
        patient: Patient,
        context: Context,
        dietType: DietType = DietType.STANDARD
    ): DietPlan {
        val bmiResult = BMICalculator.calculate(patient)
        val macroTargets = MacroCalculator.calculate(patient, bmiResult.category, dietType)
        val exchangeServes = FoodExchangeCalculator.suggestServes(macroTargets)
        val exchangeMacros = FoodExchangeCalculator.calculateMacros(exchangeServes)
        val meals = MealPlanGenerator.generate(exchangeServes, dietType, context)

        return DietPlan(patient, bmiResult, macroTargets, exchangeServes, exchangeMacros, meals)
    }
}