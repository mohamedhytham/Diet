package com.example.diet.calculators

import com.diet.system.models.*
import com.example.diet.constants.FoodExchangeValues
import com.example.diet.models.FoodExchangeMacros


object FoodExchangeCalculator {
    fun calculateMacros(serves: FoodExchangeServes): FoodExchangeMacros {
        val starchCarb = serves.starch * FoodExchangeValues.STARCH_CARB
        val starchProtein = serves.starch * FoodExchangeValues.STARCH_PROTEIN
        val vegCarb = serves.vegetables * FoodExchangeValues.VEG_CARB
        val vegProtein = serves.vegetables * FoodExchangeValues.VEG_PROTEIN
        val fruitCarb = serves.fruit * FoodExchangeValues.FRUIT_CARB
        val meatProtein = serves.meat * serves.meatType.proteinPer
        val meatFat = serves.meat * serves.meatType.fatPer
        val milkCarb = serves.milk * serves.milkType.carbPer
        val milkProtein = serves.milk * serves.milkType.proteinPer
        val milkFat = serves.milk * serves.milkType.fatPer
        val legCarb = serves.legumes * FoodExchangeValues.LEG_CARB
        val legProtein = serves.legumes * FoodExchangeValues.LEG_PROTEIN
        val fatFat = serves.fat * FoodExchangeValues.FAT_FAT

        val totalCarb = starchCarb + vegCarb + fruitCarb + milkCarb + legCarb
        val totalProtein = starchProtein + vegProtein + meatProtein + milkProtein + legProtein
        val totalFat = meatFat + milkFat + fatFat
        val totalCal = (totalCarb * 4) + (totalProtein * 4) + (totalFat * 9)

        return FoodExchangeMacros(totalCarb = totalCarb, totalProtein = totalProtein, totalFat = totalFat, totalCalories = totalCal)
    }

    fun suggestServes(macros: MacroTargets): FoodExchangeServes {
        return when {
            macros.totalCalories <= 1500 -> FoodExchangeServes(starch = 6, vegetables = 3, fruit = 2, meat = 5, milk = 1, legumes = 1, fat = 3)
            macros.totalCalories <= 1750 -> FoodExchangeServes(starch = 7, vegetables = 3, fruit = 3, meat = 6, milk = 1, legumes = 1, fat = 4)
            macros.totalCalories <= 2000 -> FoodExchangeServes(starch = 8, vegetables = 4, fruit = 3, meat = 6, milk = 1, legumes = 1, fat = 5)
            macros.totalCalories <= 2100 -> FoodExchangeServes(starch = 9, vegetables = 4, fruit = 3, meat = 7, milk = 1, legumes = 1, fat = 4)
            macros.totalCalories <= 2200 -> FoodExchangeServes(starch = 11, vegetables = 4, fruit = 3, meat = 7, milk = 2, legumes = 1, fat = 4)
            macros.totalCalories <= 2275 -> FoodExchangeServes(starch = 11, vegetables = 3, fruit = 3, meat = 7, milk = 2, legumes = 1, fat = 8)
            macros.totalCalories <= 2400 -> FoodExchangeServes(starch = 13, vegetables = 4, fruit = 3, meat = 7, milk = 2, legumes = 1, fat = 6)
            else -> FoodExchangeServes(starch = 13, vegetables = 5, fruit = 3, meat = 7, milk = 2, legumes = 1, fat = 7)
        }
    }
}