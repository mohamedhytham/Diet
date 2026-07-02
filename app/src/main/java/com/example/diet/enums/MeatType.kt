package com.diet.system.enums

enum class MeatType(
    val arabicName: String,
    val carbPer: Double,
    val proteinPer: Double,
    val fatPer: Double
) {
    LEAN("لحم خالي الدهن", 0.0, 7.0, 3.0),
    MEDIUM_FAT("لحم متوسط الدهون", 0.0, 7.0, 5.0),
    HIGH_FAT("لحم عالي الدهون", 0.0, 7.0, 8.0)
}