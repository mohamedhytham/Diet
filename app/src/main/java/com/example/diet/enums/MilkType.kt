package com.diet.system.enums

enum class MilkType(
    val arabicName: String,
    val carbPer: Double,
    val proteinPer: Double,
    val fatPer: Double
) {
    SKIM("حليب خالي الدسم", 12.0, 8.0, 0.0),
    LOW_FAT("حليب قليل الدسم", 12.0, 8.0, 5.0),
    WHOLE("حليب كامل الدسم", 12.0, 8.0, 8.0)
}