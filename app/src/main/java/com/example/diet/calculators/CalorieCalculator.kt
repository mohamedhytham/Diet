package com.example.diet.calculators

import com.diet.system.models.Patient
import com.diet.system.enums.BMICategory
import kotlin.math.max

object CalorieCalculator {
    // baseMultiplier: بيمثل معامل ثبات الوزن لشخص نشاطه عادي (حوالي 25 سعر لكل كيلو)
    // adjustment: بيمثل كمية السعرات اللي هنخصمها (أو نزودها لو بالسالب)
    private data class CalorieRule(val baseMultiplier: Double, val adjustment: Double)

    private val rules = mapOf(
        // الأندر ويت محتاج زيادة، فعملنا الخصم بالسالب عشان يزود 500 سعر (Surplus)
        BMICategory.UNDERWEIGHT to CalorieRule(25.0, -500.0),
        // الطبيعي مش محتاج خصم (ثبات وزن)
        BMICategory.NORMAL to CalorieRule(25.0, 0.0),
        BMICategory.OVERWEIGHT to CalorieRule(25.0, 350.0),
        BMICategory.OBESE_I to CalorieRule(25.0, 500.0),
        BMICategory.OBESE_II to CalorieRule(25.0, 700.0),
        BMICategory.OBESE_III to CalorieRule(25.0, 1000.0)
    )

    fun calculate(patient: Patient, bmiCategory: BMICategory): Pair<Double, Double> {
        // لو مفيش فئة، بنعتبره شخص طبيعي كـ Default
        val rule = rules[bmiCategory] ?: CalorieRule(25.0, 0.0)

        // 1. حساب سعرات الثبات التقريبية
        val maintenanceCalories = patient.weightKg * rule.baseMultiplier

        // 2. خصم السعرات (أو إضافتها في حالة الـ Underweight)
        var netCalories = maintenanceCalories - rule.adjustment

        // 3. حد الأمان الطبي (Safety Check): السعرات مينفعش طبياً تقل عن 1200 عشان الحرق ميتدمرش
        netCalories = max(netCalories, 1200.0)

        // بنحسب الخصم الفعلي اللي حصل (لأن حد الأمان ممكن يكون غير قيمة الخصم)
        val actualDeficit = maintenanceCalories - netCalories

        return Pair(netCalories, actualDeficit)
    }

    fun getMultiplier(bmiCategory: BMICategory): Double = rules[bmiCategory]?.baseMultiplier ?: 25.0
    fun getDeficit(bmiCategory: BMICategory): Double = rules[bmiCategory]?.adjustment ?: 0.0
}