
package com.diet.system.models
import com.diet.system.enums.MeatType
import com.diet.system.enums.MilkType

data class FoodExchangeServes(
    val starch: Int,
    val vegetables: Int,
    val fruit: Int,
    val meat: Int,
    val meatType: MeatType = MeatType.MEDIUM_FAT,
    val milk: Int,
    val milkType: MilkType = MilkType.LOW_FAT,
    val legumes: Int,
    val fat: Int
)