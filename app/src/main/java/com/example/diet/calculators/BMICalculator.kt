package com.example.diet.calculators

import com.diet.system.models.*
import com.diet.system.enums.BMICategory
import kotlin.math.round

object BMICalculator {
    fun calculate(patient: Patient): BMIResult {
        val heightM = patient.heightCm / 100.0
        val bmi = patient.weightKg / (heightM * heightM)
        val bmiRounded = round(bmi * 10.0) / 10.0

        val category = when {
            bmi < 18.5 -> BMICategory.UNDERWEIGHT
            bmi < 25.0 -> BMICategory.NORMAL
            bmi < 30.0 -> BMICategory.OVERWEIGHT
            bmi < 35.0 -> BMICategory.OBESE_I
            bmi < 40.0 -> BMICategory.OBESE_II
            else -> BMICategory.OBESE_III
        }

        val idealWeightMin = round(18.5 * heightM * heightM * 10) / 10.0
        val idealWeightMax = round(24.9 * heightM * heightM * 10) / 10.0

        return BMIResult(bmi = bmiRounded, category = category, idealWeightMin = idealWeightMin, idealWeightMax = idealWeightMax)
    }
}