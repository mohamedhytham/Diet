package com.example.diet.generators

import android.content.Context
import com.diet.system.enums.MealType
import com.diet.system.models.FoodExchangeServes
import com.diet.system.models.Meal
import com.diet.system.models.MealItem
import com.example.diet.enums.DietType
import kotlin.math.max

object MealPlanGenerator {

    fun generate(serves: FoodExchangeServes, dietType: DietType, context: Context): List<Meal> {
        val language = context.resources.configuration.locale.language

        return when (dietType) {
            DietType.STANDARD -> generateStandardMeals(serves, language)
            DietType.KETO -> generateKetoMeals(serves, language)
            DietType.HIGH_PROTEIN -> generateHighProteinMeals(serves, language)
            DietType.LOW_CARB -> generateLowCarbMeals(serves, language)
            DietType.VEGETARIAN -> generateVegetarianMeals(serves, language)
            DietType.INTERMITTENT_FASTING -> generateIntermittentFastingMeals(serves, language)
        }
    }

    private fun getTranslatedText(ar: String, en: String, lang: String): String {
        return if (lang == "ar") ar else en
    }

    // ==========================================
    // 1. النظام القياسي
    // ==========================================
    private fun generateStandardMeals(s: FoodExchangeServes, lang: String): List<Meal> {
        return listOf(
            Meal(MealType.BREAKFAST, listOf(
                MealItem(getTranslatedText("خبز / عيش توست", "Bread / Toast", lang), getTranslatedText("${maxOf(1, (s.starch * 0.25).toInt()) * 2} شريحة", "${maxOf(1, (s.starch * 0.25).toInt()) * 2} Slices", lang)),
                MealItem(getTranslatedText("جبنة قريش / مثلثات", "Cheese", lang), getTranslatedText("${maxOf(1, (s.meat * 0.30).toInt())} قطعة", "${maxOf(1, (s.meat * 0.30).toInt())} Pieces", lang)),
                MealItem(getTranslatedText("خضار (خيار وطماطم)", "Vegetables", lang), getTranslatedText("كمية حرة", "Free quantity", lang)),
                MealItem(getTranslatedText("زيت زيتون", "Olive Oil", lang), getTranslatedText("معلقة صغيرة", "1 Teaspoon", lang))
            )),
            Meal(MealType.MORNING_SNACK, listOf(
                MealItem(getTranslatedText("شوفان", "Oats", lang), getTranslatedText("${maxOf(3, (s.starch * 0.15 * 3).toInt())} معالق", "${maxOf(3, (s.starch * 0.15 * 3).toInt())} Spoons", lang)),
                MealItem(getTranslatedText("لبن (حليب)", "Milk", lang), getTranslatedText("1 كوب", "1 Cup", lang)),
                MealItem(getTranslatedText("فاكهة", "Fruit", lang), getTranslatedText("${maxOf(1, s.fruit.toInt())} حبة", "${maxOf(1, s.fruit.toInt())} Piece", lang))
            )),
            Meal(MealType.LUNCH, listOf(
                MealItem(getTranslatedText("أرز مسلوق / مكرونة", "Rice / Pasta", lang), getTranslatedText("${maxOf(6, (s.starch * 0.40 * 3).toInt())} معلقة", "${maxOf(6, (s.starch * 0.40 * 3).toInt())} Spoons", lang)),
                MealItem(getTranslatedText("دجاج مشوي / سمك", "Chicken / Fish", lang), getTranslatedText("${maxOf(90, (s.meat * 0.45 * 30).toInt())} جرام", "${maxOf(90, (s.meat * 0.45 * 30).toInt())}g", lang)),
                MealItem(getTranslatedText("سلطة خضراء", "Green Salad", lang), getTranslatedText("طبق كبير", "Large Plate", lang))
            )),
            Meal(MealType.AFTERNOON_SNACK, listOf(
                MealItem(getTranslatedText("زبادي يوناني", "Greek Yogurt", lang), getTranslatedText("1 كوب", "1 Cup", lang)),
                MealItem(getTranslatedText("لوز / مكسرات", "Almonds", lang), getTranslatedText("${maxOf(10, (s.fat * 0.30 * 6).toInt())} حبة", "Pieces", lang))
            )),
            Meal(MealType.DINNER, listOf(
                MealItem(getTranslatedText("عيش أسمر", "Brown Bread", lang), getTranslatedText("نصف رغيف", "Half Loaf", lang)),
                MealItem(getTranslatedText("فول مدمس", "Fava Beans", lang), getTranslatedText("${maxOf(3, (s.starch * 0.10 * 3).toInt() + 2)} معالق", "Spoons", lang)),
                MealItem(getTranslatedText("بيض مسلوق", "Boiled Eggs", lang), getTranslatedText("${maxOf(1, (s.meat * 0.25).toInt())} حبة", "Piece", lang))
            ))
        )
    }

    // ==========================================
    // 2. نظام عالي البروتين (زي الصورة اللي بعتها)
    // ==========================================
    private fun generateHighProteinMeals(s: FoodExchangeServes, lang: String): List<Meal> {
        val meatCount = maxOf(3, (s.meat * 0.45).toInt())
        return listOf(
            Meal(MealType.BREAKFAST, listOf(
                MealItem(getTranslatedText("بياض بيض", "Egg whites", lang), "$meatCount حبات"),
                MealItem(getTranslatedText("شوفان", "Oats", lang), "${maxOf(3, (s.starch * 0.20 * 3).toInt())} معالق")
            )),
            Meal(MealType.MORNING_SNACK, listOf(
                MealItem(getTranslatedText("زبادي يوناني", "Greek Yogurt", lang), "1 كوب"),
                MealItem(getTranslatedText("لوز", "Almonds", lang), "15 حبة")
            )),
            Meal(MealType.LUNCH, listOf(
                MealItem(getTranslatedText("صدر دجاج", "Chicken breast", lang), "${meatCount * 30} جرام"),
                MealItem(getTranslatedText("أرز", "Rice", lang), "${maxOf(6, (s.starch * 0.35 * 3).toInt())} معالق"),
                MealItem(getTranslatedText("سلطة", "Salad", lang), "طبق متوسط")
            )),
            Meal(MealType.AFTERNOON_SNACK, listOf(
                MealItem(getTranslatedText("جبنة قريش", "Cottage Cheese", lang), "نصف كوب"),
                MealItem(getTranslatedText("فاكهة", "Fruit", lang), "1 حبة")
            )),
            Meal(MealType.DINNER, listOf(
                MealItem(getTranslatedText("تونة مصفاة / سمك", "Tuna / Fish", lang), "${meatCount * 25} جرام"),
                MealItem(getTranslatedText("سلطة خضراء", "Green Salad", lang), "طبق كبير"),
                MealItem(getTranslatedText("زيت زيتون", "Olive Oil", lang), "1 معلقة صغيرة")
            ))
        )
    }

    // ==========================================
    // 3. نظام الكيتو
    // ==========================================
    private fun generateKetoMeals(s: FoodExchangeServes, lang: String): List<Meal> {
        val meatCount = maxOf(2, (s.meat * 0.40).toInt())
        val fatCount = maxOf(2, (s.fat * 0.40).toInt())
        return listOf(
            Meal(MealType.BREAKFAST, listOf(
                MealItem(getTranslatedText("بيض مقلي", "Fried eggs", lang), "$meatCount حبات"),
                MealItem(getTranslatedText("أفوكادو", "Avocado", lang), "نصف حبة"),
                MealItem(getTranslatedText("زيت زيتون", "Olive oil", lang), "$fatCount معالق")
            )),
            Meal(MealType.MORNING_SNACK, listOf(
                MealItem(getTranslatedText("مكسرات مشكلة", "Mixed Nuts", lang), "30 جرام"),
                MealItem(getTranslatedText("جبنة شيدر", "Cheddar Cheese", lang), "شريحتين")
            )),
            Meal(MealType.LUNCH, listOf(
                MealItem(getTranslatedText("لحم دهني / دجاج", "Fatty Meat", lang), "${meatCount * 30} جرام"),
                MealItem(getTranslatedText("سلطة ورقيات", "Greens", lang), "طبق كبير"),
                MealItem(getTranslatedText("زيت زيتون", "Olive oil", lang), "$fatCount معالق")
            )),
            Meal(MealType.AFTERNOON_SNACK, listOf(
                MealItem(getTranslatedText("زيتون أخضر", "Green Olives", lang), "10 حبات"),
                MealItem(getTranslatedText("خيار", "Cucumber", lang), "1 حبة")
            )),
            Meal(MealType.DINNER, listOf(
                MealItem(getTranslatedText("سمك سلمون / تونة", "Salmon / Tuna", lang), "${meatCount * 25} جرام"),
                MealItem(getTranslatedText("بروكلي مسلوق", "Boiled Broccoli", lang), "كوب"),
                MealItem(getTranslatedText("زبدة طبيعية", "Butter", lang), "1 معلقة")
            ))
        )
    }

    // ==========================================
    // 4. نظام قليل الكارب
    // ==========================================
    private fun generateLowCarbMeals(s: FoodExchangeServes, lang: String): List<Meal> {
        return listOf(
            Meal(MealType.BREAKFAST, listOf(
                MealItem(getTranslatedText("زبادي يوناني", "Greek yogurt", lang), "1 كوب"),
                MealItem(getTranslatedText("مكسرات", "Nuts", lang), "${maxOf(10, (s.fat * 0.40 * 6).toInt())} حبة")
            )),
            Meal(MealType.MORNING_SNACK, listOf(
                MealItem(getTranslatedText("بيض مسلوق", "Boiled Eggs", lang), "2 حبة")
            )),
            Meal(MealType.LUNCH, listOf(
                MealItem(getTranslatedText("بروتين مشوي", "Grilled protein", lang), "${maxOf(100, (s.meat * 0.40 * 30).toInt())} جرام"),
                MealItem(getTranslatedText("خضار سوتيه", "Sauteed veggies", lang), "طبق متوسط")
            )),
            Meal(MealType.AFTERNOON_SNACK, listOf(
                MealItem(getTranslatedText("جبنة قريش", "Cottage Cheese", lang), "ربع قطعة"),
                MealItem(getTranslatedText("خيار", "Cucumber", lang), "1 حبة")
            )),
            Meal(MealType.DINNER, listOf(
                MealItem(getTranslatedText("دجاج مشوي", "Grilled Chicken", lang), "${maxOf(100, (s.meat * 0.30 * 30).toInt())} جرام"),
                MealItem(getTranslatedText("سلطة خضراء", "Green Salad", lang), "طبق كبير")
            ))
        )
    }

    // ==========================================
    // 5. نظام نباتي
    // ==========================================
    private fun generateVegetarianMeals(s: FoodExchangeServes, lang: String): List<Meal> {
        return listOf(
            Meal(MealType.BREAKFAST, listOf(
                MealItem(getTranslatedText("شوفان بحليب نباتي", "Oatmeal with plant milk", lang), "${maxOf(4, (s.starch * 0.25 * 3).toInt())} معالق"),
                MealItem(getTranslatedText("مكسرات", "Nuts", lang), "${maxOf(10, (s.fat * 0.30 * 6).toInt())} حبة")
            )),
            Meal(MealType.MORNING_SNACK, listOf(
                MealItem(getTranslatedText("فاكهة", "Fruit", lang), "1 حبة"),
                MealItem(getTranslatedText("زبدة فول سوداني", "Peanut Butter", lang), "1 معلقة")
            )),
            Meal(MealType.LUNCH, listOf(
                MealItem(getTranslatedText("عدس / حمص", "Lentils / Chickpeas", lang), "${maxOf(4, (s.legumes * 0.50 * 3).toInt())} معالق"),
                MealItem(getTranslatedText("أرز", "Rice", lang), "${maxOf(6, (s.starch * 0.35 * 3).toInt())} معالق"),
                MealItem(getTranslatedText("سلطة", "Salad", lang), "طبق متوسط")
            )),
            Meal(MealType.AFTERNOON_SNACK, listOf(
                MealItem(getTranslatedText("زبادي صويا / يوناني", "Soy/Greek Yogurt", lang), "1 كوب")
            )),
            Meal(MealType.DINNER, listOf(
                MealItem(getTranslatedText("فول مدمس", "Fava Beans", lang), "4 معالق"),
                MealItem(getTranslatedText("خبز أسمر", "Brown Bread", lang), "نصف رغيف"),
                MealItem(getTranslatedText("خضار", "Vegetables", lang), "كمية حرة")
            ))
        )
    }

    // ==========================================
    // 6. الصيام المتقطع
    // ==========================================
    private fun generateIntermittentFastingMeals(s: FoodExchangeServes, lang: String): List<Meal> {
        return listOf(
            Meal(MealType.BREAKFAST, listOf(
                MealItem(getTranslatedText("كسر الصيام (تمر + ماء)", "Break fast (Dates+Water)", lang), "3 حبات تمر"),
                MealItem(getTranslatedText("زبادي يوناني", "Greek Yogurt", lang), "1 كوب")
            )),
            Meal(MealType.MORNING_SNACK, listOf(
                MealItem(getTranslatedText("فاكهة", "Fruit", lang), "1 حبة") // سناك خفيف بعد كسر الصيام
            )),
            Meal(MealType.LUNCH, listOf(
                MealItem(getTranslatedText("أرز مسلوق", "Rice", lang), "${maxOf(6, (s.starch * 0.45 * 3).toInt())} معالق"),
                MealItem(getTranslatedText("بروتين (دجاج/لحم)", "Protein", lang), "${maxOf(100, (s.meat * 0.45 * 30).toInt())} جرام"),
                MealItem(getTranslatedText("سلطة", "Salad", lang), "طبق كبير")
            )),
            Meal(MealType.AFTERNOON_SNACK, listOf(
                MealItem(getTranslatedText("مكسرات", "Nuts", lang), "15 حبة")
            )),
            Meal(MealType.DINNER, listOf(
                MealItem(getTranslatedText("آخر وجبة (جبنة قريش/بيض)", "Last Meal (Cheese/Eggs)", lang), "حصتين"),
                MealItem(getTranslatedText("سلطة بزيت الزيتون", "Salad with Olive Oil", lang), "طبق متوسط")
            ))
        )
    }
}