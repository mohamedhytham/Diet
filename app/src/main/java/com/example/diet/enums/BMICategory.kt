package com.diet.system.enums

enum class BMICategory(val arabicName: String, val range: String) {
    UNDERWEIGHT("نقص الوزن", "BMI < 18.5"),
    NORMAL("وزن طبيعي", "18.5 - 24.9"),
    OVERWEIGHT("زيادة وزن", "25.0 - 29.9"),
    OBESE_I("سمنة درجة أولى", "30.0 - 34.9"),
    OBESE_II("سمنة درجة ثانية", "35.0 - 39.9"),
    OBESE_III("سمنة درجة ثالثة", "BMI ≥ 40")
}