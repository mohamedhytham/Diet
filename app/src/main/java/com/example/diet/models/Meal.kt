
package com.diet.system.models
import com.diet.system.enums.MealType

data class Meal(
    val mealType: MealType,
    val items: List<MealItem>,
    val exerciseNote: String? = null
)