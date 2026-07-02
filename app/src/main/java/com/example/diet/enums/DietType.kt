package com.example.diet.enums

import android.content.Context

enum class DietType(
    val carbPercent: Double,
    val proteinPercent: Double,
    val fatPercent: Double,
    val stringKey: String
) {
    STANDARD(50.0, 20.0, 30.0, "diet_standard"),
    KETO(10.0, 25.0, 65.0, "diet_keto"),
    HIGH_PROTEIN(40.0, 35.0, 25.0, "diet_high_protein"),
    LOW_CARB(25.0, 35.0, 40.0, "diet_low_carb"),
    VEGETARIAN(55.0, 18.0, 27.0, "diet_vegetarian"),
    INTERMITTENT_FASTING(45.0, 25.0, 30.0, "diet_intermittent");

    fun getName(context: Context): String {
        val resId = context.resources.getIdentifier(stringKey, "string", context.packageName)
        return if (resId != 0) {
            context.getString(resId)
        } else {
            // fallback لو مش لاقي
            when (this) {
                STANDARD -> "📊 قياسي"
                KETO -> "🥩 كيتو"
                HIGH_PROTEIN -> "💪 عالي البروتين"
                LOW_CARB -> "🥗 قليل الكارب"
                VEGETARIAN -> "🌱 نباتي"
                INTERMITTENT_FASTING -> "⏰ صيام متقطع"
            }
        }
    }
}