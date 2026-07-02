package com.example.diet

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.diet.system.enums.Gender
import com.diet.system.enums.MeatType
import com.diet.system.enums.MilkType
import com.diet.system.models.Patient
import com.example.diet.enums.DietType
import com.example.diet.system.DietSystem
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // ===================== النظام الرئيسي =====================
    private lateinit var dietSystem: DietSystem
    private var selectedDietType: DietType = DietType.STANDARD

    // ===================== متغيرات BMI =====================
    private lateinit var etHeightBmi: TextInputEditText
    private lateinit var etWeightBmi: TextInputEditText
    private lateinit var btnCalculateBmi: Button
    private lateinit var layoutBmiResult: LinearLayout
    private lateinit var tvBmiResult: TextView
    private lateinit var tvBmiCategoryResult: TextView
    private lateinit var tvBmiDescription: TextView
    private lateinit var tvBmiTip: TextView
    private lateinit var seekBarBmi: SeekBar
    private lateinit var tvIdealWeightRangeBmi: TextView
    private lateinit var chipBmiStatus: com.google.android.material.chip.Chip

    // ===================== أزرار التمرير =====================
    private lateinit var btnScrollBmi: com.google.android.material.button.MaterialButton
    private lateinit var btnScrollPatient: com.google.android.material.button.MaterialButton
    private lateinit var btnScrollDiet: com.google.android.material.button.MaterialButton
    private lateinit var btnScrollResults: com.google.android.material.button.MaterialButton
    private lateinit var btnScrollAnalysis: com.google.android.material.button.MaterialButton

    // ===================== زر تغيير اللغة والمشاركة =====================
    private lateinit var btnChangeLanguage: com.google.android.material.button.MaterialButton
    private lateinit var btnShare: com.google.android.material.button.MaterialButton

    // ===================== بيانات المريض =====================
    private lateinit var tilName: TextInputLayout
    private lateinit var etName: TextInputEditText
    private lateinit var tilAge: TextInputLayout
    private lateinit var etAge: TextInputEditText
    private lateinit var tilWeight: TextInputLayout
    private lateinit var etWeight: TextInputEditText
    private lateinit var tilHeight: TextInputLayout
    private lateinit var etHeight: TextInputEditText
    private lateinit var rgGender: RadioGroup
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var chipGroupDiet: ChipGroup
    private lateinit var btnCalculate: Button
    private lateinit var btnClear: Button

    // ===================== النتائج =====================
    private lateinit var cardResults: com.google.android.material.card.MaterialCardView
    private lateinit var tvResultTitle: TextView
    private lateinit var tvBmiValue: TextView
    private lateinit var tvBmiCategory: TextView
    private lateinit var tvIdealWeight: TextView
    private lateinit var tvCalories: TextView
    private lateinit var tvMacros: TextView
    private lateinit var tvExchangeTable: TextView
    private lateinit var tvMealPlan: TextView
    private lateinit var tvMatchPercentage: TextView

    // ===================== تحليل الوزن =====================
    private lateinit var cardWeightAnalysis: com.google.android.material.card.MaterialCardView
    private lateinit var tvIdealWeightRange: TextView
    private lateinit var tvWeightDifference: TextView
    private lateinit var tvAdvice: TextView

    // ===================== عام =====================
    private lateinit var scrollView: NestedScrollView

    // ===================== onCreate =====================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        initViews()
        setupListeners()
        dietSystem = DietSystem()

        // تحديث أسماء الأنظمة حسب اللغة
        updateDietChips()
    }

    // ===================== تهيئة الـ Views =====================
    private fun initViews() {
        scrollView = findViewById(R.id.scrollView)

        // أزرار
        btnChangeLanguage = findViewById(R.id.btnChangeLanguage)
        btnShare = findViewById(R.id.btnShare)

        // أزرار التمرير
        btnScrollBmi = findViewById(R.id.btnScrollBmi)
        btnScrollPatient = findViewById(R.id.btnScrollPatient)
        btnScrollDiet = findViewById(R.id.btnScrollDiet)
        btnScrollResults = findViewById(R.id.btnScrollResults)
        btnScrollAnalysis = findViewById(R.id.btnScrollAnalysis)

        // BMI
        etHeightBmi = findViewById(R.id.etHeightBmi)
        etWeightBmi = findViewById(R.id.etWeightBmi)
        btnCalculateBmi = findViewById(R.id.btnCalculateBmi)
        layoutBmiResult = findViewById(R.id.layoutBmiResult)
        tvBmiResult = findViewById(R.id.tvBmiResult)
        tvBmiCategoryResult = findViewById(R.id.tvBmiCategoryResult)
        tvBmiDescription = findViewById(R.id.tvBmiDescription)
        tvBmiTip = findViewById(R.id.tvBmiTip)
        seekBarBmi = findViewById(R.id.seekBarBmi)
        tvIdealWeightRangeBmi = findViewById(R.id.tvIdealWeightRangeBmi)
        chipBmiStatus = findViewById(R.id.chipBmiStatus)

        seekBarBmi.isEnabled = false
        seekBarBmi.isClickable = false
        seekBarBmi.isFocusable = false

        // بيانات المريض
        tilName = findViewById(R.id.tilName)
        etName = findViewById(R.id.etName)
        tilAge = findViewById(R.id.tilAge)
        etAge = findViewById(R.id.etAge)
        tilWeight = findViewById(R.id.tilWeight)
        etWeight = findViewById(R.id.etWeight)
        tilHeight = findViewById(R.id.tilHeight)
        etHeight = findViewById(R.id.etHeight)
        rgGender = findViewById(R.id.rgGender)
        rbMale = findViewById(R.id.rbMale)
        rbFemale = findViewById(R.id.rbFemale)
        chipGroupDiet = findViewById(R.id.chipGroupDiet)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnClear = findViewById(R.id.btnClear)

        // النتائج
        cardResults = findViewById(R.id.cardResults)
        tvResultTitle = findViewById(R.id.tvResultTitle)
        tvBmiValue = findViewById(R.id.tvBmiValue)
        tvBmiCategory = findViewById(R.id.tvBmiCategory)
        tvIdealWeight = findViewById(R.id.tvIdealWeight)
        tvCalories = findViewById(R.id.tvCalories)
        tvMacros = findViewById(R.id.tvMacros)
        tvExchangeTable = findViewById(R.id.tvExchangeTable)
        tvMealPlan = findViewById(R.id.tvMealPlan)
        tvMatchPercentage = findViewById(R.id.tvMatchPercentage)

        // تحليل الوزن
        cardWeightAnalysis = findViewById(R.id.cardWeightAnalysis)
        tvIdealWeightRange = findViewById(R.id.tvIdealWeightRange)
        tvWeightDifference = findViewById(R.id.tvWeightDifference)
        tvAdvice = findViewById(R.id.tvAdvice)

        chipGroupDiet.check(R.id.chipStandard)
    }

    // ===================== تحديث أسماء الأنظمة حسب اللغة =====================
    private fun updateDietChips() {
        val chips = listOf(
            R.id.chipStandard to DietType.STANDARD,
            R.id.chipKeto to DietType.KETO,
            R.id.chipHighProtein to DietType.HIGH_PROTEIN,
            R.id.chipLowCarb to DietType.LOW_CARB,
            R.id.chipVegetarian to DietType.VEGETARIAN,
            R.id.chipIntermittent to DietType.INTERMITTENT_FASTING
        )

        chips.forEach { (id, dietType) ->
            val chip = findViewById<com.google.android.material.chip.Chip>(id)
            chip?.text = dietType.getName(this)
        }
    }

    // ===================== إعداد الـ Listeners =====================
    private fun setupListeners() {
        // تغيير اللغة
        btnChangeLanguage.setOnClickListener {
            showLanguageDialog()
        }

        // مشاركة
        btnShare.setOnClickListener {
            shareDietPlan()
        }

        // أزرار التمرير
        btnScrollBmi.setOnClickListener {
            scrollView.post {
                scrollView.smoothScrollTo(0, 0)
            }
        }

        btnScrollPatient.setOnClickListener {
            scrollView.post {
                val targetView = findViewById<View>(R.id.tilName)
                scrollView.smoothScrollTo(0, targetView.top - 100)
            }
        }

        btnScrollDiet.setOnClickListener {
            scrollView.post {
                val targetView = findViewById<View>(R.id.chipGroupDiet)
                scrollView.smoothScrollTo(0, targetView.top - 100)
            }
        }

        btnScrollResults.setOnClickListener {
            scrollView.post {
                val targetView = findViewById<View>(R.id.cardResults)
                if (targetView.visibility == View.VISIBLE) {
                    scrollView.smoothScrollTo(0, targetView.top - 100)
                } else {
                    Toast.makeText(this, getString(R.string.error_not_calculated), Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnScrollAnalysis.setOnClickListener {
            scrollView.post {
                val targetView = findViewById<View>(R.id.cardWeightAnalysis)
                if (targetView.visibility == View.VISIBLE) {
                    scrollView.smoothScrollTo(0, targetView.top - 100)
                } else {
                    Toast.makeText(this, getString(R.string.error_not_calculated), Toast.LENGTH_SHORT).show()
                }
            }
        }

        // حساب BMI
        btnCalculateBmi.setOnClickListener {
            calculateBmi()
        }

        // اختيار النظام الغذائي
        chipGroupDiet.setOnCheckedChangeListener { _, checkedId ->
            for (i in 0 until chipGroupDiet.childCount) {
                val chip = chipGroupDiet.getChildAt(i) as? com.google.android.material.chip.Chip
                chip?.apply {
                    chipBackgroundColor = android.content.res.ColorStateList.valueOf(
                        when (id) {
                            R.id.chipStandard -> Color.parseColor("#E8F5E9")
                            R.id.chipKeto -> Color.parseColor("#FFF3E0")
                            R.id.chipHighProtein -> Color.parseColor("#E3F2FD")
                            R.id.chipLowCarb -> Color.parseColor("#F1F8E9")
                            R.id.chipVegetarian -> Color.parseColor("#E8F5E9")
                            R.id.chipIntermittent -> Color.parseColor("#FCE4EC")
                            else -> Color.parseColor("#F5F5F5")
                        }
                    )
                    chipStrokeColor = android.content.res.ColorStateList.valueOf(Color.parseColor("#BDBDBD"))
                    setTextColor(Color.parseColor("#333333"))
                }
            }

            val selectedChip = chipGroupDiet.findViewById<com.google.android.material.chip.Chip>(checkedId)
            selectedChip?.apply {
                chipBackgroundColor = android.content.res.ColorStateList.valueOf(
                    when (checkedId) {
                        R.id.chipStandard -> Color.parseColor("#2E7D32")
                        R.id.chipKeto -> Color.parseColor("#E65100")
                        R.id.chipHighProtein -> Color.parseColor("#0D47A1")
                        R.id.chipLowCarb -> Color.parseColor("#33691E")
                        R.id.chipVegetarian -> Color.parseColor("#1B5E20")
                        R.id.chipIntermittent -> Color.parseColor("#880E4F")
                        else -> Color.parseColor("#2E7D32")
                    }
                )
                chipStrokeColor = android.content.res.ColorStateList.valueOf(Color.parseColor("#000000"))
                setTextColor(Color.parseColor("#FFFFFF"))
            }

            selectedDietType = when (checkedId) {
                R.id.chipKeto -> DietType.KETO
                R.id.chipHighProtein -> DietType.HIGH_PROTEIN
                R.id.chipLowCarb -> DietType.LOW_CARB
                R.id.chipVegetarian -> DietType.VEGETARIAN
                R.id.chipIntermittent -> DietType.INTERMITTENT_FASTING
                else -> DietType.STANDARD
            }
        }

        // حساب الخطة
        btnCalculate.setOnClickListener {
            calculateDietPlan()
        }

        // مسح
        btnClear.setOnClickListener {
            clearForm()
        }
    }

    // ===================== تغيير اللغة =====================
    private fun showLanguageDialog() {
        val languages = arrayOf(
            "🇸🇦 العربية",
            "🇬🇧 English",
            "🇫🇷 Français",
            "🇪🇸 Español",
            "🇩🇪 Deutsch"
        )

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_language))
            .setItems(languages) { _, which ->
                when (which) {
                    0 -> setLocale("ar")
                    1 -> setLocale("en")
                    2 -> setLocale("fr")
                    3 -> setLocale("es")
                    4 -> setLocale("de")
                }
            }
            .setNegativeButton(getString(R.string.btn_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.displayMetrics)

        // تحديث الـ Chips
        updateDietChips()

        recreate()
    }

    // ===================== دوال مساعدة لجلب أسماء الأنواع حسب اللغة =====================
    private fun getMeatTypeName(meatType: MeatType): String {
        return when (meatType) {
            MeatType.LEAN -> getString(R.string.meat_lean)
            MeatType.MEDIUM_FAT -> getString(R.string.meat_medium_fat)
            MeatType.HIGH_FAT -> getString(R.string.meat_high_fat)
        }
    }

    private fun getMilkTypeName(milkType: MilkType): String {
        return when (milkType) {
            MilkType.SKIM -> getString(R.string.milk_skim)
            MilkType.LOW_FAT -> getString(R.string.milk_low_fat)
            MilkType.WHOLE -> getString(R.string.milk_whole)
        }
    }

    // ===================== مشاركة الخطة =====================
    private fun shareDietPlan() {
        try {
            val name = etName.text.toString().trim()
            val age = etAge.text.toString()
            val weight = etWeight.text.toString()
            val height = etHeight.text.toString()
            val gender = if (rbMale.isChecked) "ذكر" else "أنثى"

            val shareText = buildString {
                append("🥗 ${getString(R.string.app_name)}\n")
                append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n")
                append("📋 ${getString(R.string.patient_title)}:\n")
                append("${getString(R.string.patient_name)}: $name\n")
                append("${getString(R.string.patient_age)}: $age ${getString(R.string.patient_age)}\n")
                append("${getString(R.string.patient_gender)}: $gender\n")
                append("${getString(R.string.patient_weight)}: $weight ${getString(R.string.patient_weight)}\n")
                append("${getString(R.string.patient_height)}: $height ${getString(R.string.patient_height)}\n\n")

                if (cardResults.visibility == View.VISIBLE) {
                    append("📊 ${getString(R.string.results_title)}:\n")
                    append("${getString(R.string.results_bmi)}: ${tvBmiValue.text}\n")
                    append("${getString(R.string.bmi_category)}: ${tvBmiCategory.text}\n")
                    append("${getString(R.string.analysis_ideal)}: ${tvIdealWeight.text}\n\n")
                    append("🔥 ${getString(R.string.results_calories)}:\n${tvCalories.text}\n\n")
                    append("⚖️ ${getString(R.string.results_macros)}:\n${tvMacros.text}\n\n")
                    append("🍽️ ${getString(R.string.results_meal_plan)}:\n${tvMealPlan.text}")
                } else {
                    append("⚠️ ${getString(R.string.error_not_calculated)}\n")
                    append("${getString(R.string.error_not_calculated)}")
                }

                append("\n\n━━━━━━━━━━━━━━━━━━━━━━━━━━\n")
                append("💪 ${getString(R.string.footer)}")
            }

            val shareIntent = android.content.Intent().apply {
                action = android.content.Intent.ACTION_SEND
                putExtra(android.content.Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }

            startActivity(android.content.Intent.createChooser(shareIntent, "شارك الخطة الغذائية"))

        } catch (e: Exception) {
            Toast.makeText(this, "حدث خطأ أثناء المشاركة: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // ===================== حساب BMI =====================
    private fun calculateBmi() {
        try {
            val heightText = etHeightBmi.text.toString().trim()
            val weightText = etWeightBmi.text.toString().trim()

            if (heightText.isEmpty() || weightText.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_enter_height_weight), Toast.LENGTH_SHORT).show()
                return
            }

            val height = heightText.toDouble()
            val weight = weightText.toDouble()

            if (height <= 0 || weight <= 0) {
                Toast.makeText(this, getString(R.string.error_valid_values), Toast.LENGTH_SHORT).show()
                return
            }

            val heightM = height / 100.0
            val bmi = weight / (heightM * heightM)
            val bmiRounded = String.format("%.1f", bmi)

            var category = ""
            var color = ""
            var description = ""
            var tip = ""

            when {
                bmi < 18.5 -> {
                    category = getString(R.string.bmi_underweight)
                    color = "#F44336"
                    description = getString(R.string.bmi_underweight_desc)
                    tip = getString(R.string.tip_underweight)
                }
                bmi < 25.0 -> {
                    category = getString(R.string.bmi_normal)
                    color = "#4CAF50"
                    description = getString(R.string.bmi_normal_desc)
                    tip = getString(R.string.tip_normal)
                }
                bmi < 30.0 -> {
                    category = getString(R.string.bmi_overweight)
                    color = "#FF9800"
                    description = getString(R.string.bmi_overweight_desc)
                    tip = getString(R.string.tip_overweight)
                }
                bmi < 35.0 -> {
                    category = getString(R.string.bmi_obese1)
                    color = "#FF5722"
                    description = getString(R.string.bmi_obese1_desc)
                    tip = getString(R.string.tip_obese1)
                }
                bmi < 40.0 -> {
                    category = getString(R.string.bmi_obese2)
                    color = "#D32F2F"
                    description = getString(R.string.bmi_obese2_desc)
                    tip = getString(R.string.tip_obese2)
                }
                else -> {
                    category = getString(R.string.bmi_obese3)
                    color = "#B71C1C"
                    description = getString(R.string.bmi_obese3_desc)
                    tip = getString(R.string.tip_obese3)
                }
            }

            val idealMin = 18.5 * heightM * heightM
            val idealMax = 24.9 * heightM * heightM

            layoutBmiResult.visibility = View.VISIBLE
            tvBmiResult.text = "${getString(R.string.bmi_result)} $bmiRounded"
            tvBmiCategoryResult.text = "${getString(R.string.bmi_category)} $category"
            tvBmiCategoryResult.setTextColor(Color.parseColor(color))
            tvBmiDescription.text = description
            tvBmiTip.text = "💡 $tip"

            val progress = (bmi * 10).toInt()
            seekBarBmi.progress = progress.coerceIn(0, 400)

            seekBarBmi.isEnabled = false
            seekBarBmi.isClickable = false
            seekBarBmi.isFocusable = false

            tvIdealWeightRangeBmi.text = String.format("%.1f - %.1f كجم", idealMin, idealMax)

            chipBmiStatus.text = category
            chipBmiStatus.chipBackgroundColor = android.content.res.ColorStateList.valueOf(Color.parseColor(color))

            scrollView.post {
                scrollView.smoothScrollTo(0, layoutBmiResult.top)
            }

        } catch (e: NumberFormatException) {
            Toast.makeText(this, getString(R.string.error_enter_numbers), Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "خطأ: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // ===================== حساب الخطة الغذائية =====================
    private fun calculateDietPlan() {
        if (!validateInputs()) return

        try {
            val name = etName.text.toString().trim()
            val age = etAge.text.toString().toInt()
            val weight = etWeight.text.toString().toDouble()
            val height = etHeight.text.toString().toDouble()
            val gender = if (rbMale.isChecked) Gender.MALE else Gender.FEMALE

            val patient = Patient(name, age, gender, weight, height)

            val dietPlan = dietSystem.createDietPlan(patient, this, selectedDietType)

            displayResults(dietPlan)

            scrollView.post {
                scrollView.smoothScrollTo(0, cardResults.top)
            }

        } catch (e: Exception) {
            Toast.makeText(this, "خطأ: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // ===================== التحقق من صحة المدخلات =====================
    private fun validateInputs(): Boolean {
        var isValid = true

        if (etName.text.toString().trim().isEmpty()) {
            tilName.error = getString(R.string.error_enter_name)
            isValid = false
        } else {
            tilName.error = null
        }

        if (etAge.text.toString().isEmpty()) {
            tilAge.error = getString(R.string.error_enter_age)
            isValid = false
        } else {
            try {
                val age = etAge.text.toString().toInt()
                if (age < 1 || age > 150) {
                    tilAge.error = getString(R.string.error_age_range)
                    isValid = false
                } else {
                    tilAge.error = null
                }
            } catch (e: NumberFormatException) {
                tilAge.error = getString(R.string.error_enter_numbers)
                isValid = false
            }
        }

        if (etWeight.text.toString().isEmpty()) {
            tilWeight.error = getString(R.string.error_enter_weight)
            isValid = false
        } else {
            try {
                val weight = etWeight.text.toString().toDouble()
                if (weight < 10 || weight > 500) {
                    tilWeight.error = getString(R.string.error_weight_range)
                    isValid = false
                } else {
                    tilWeight.error = null
                }
            } catch (e: NumberFormatException) {
                tilWeight.error = getString(R.string.error_enter_numbers)
                isValid = false
            }
        }

        if (etHeight.text.toString().isEmpty()) {
            tilHeight.error = getString(R.string.error_enter_height)
            isValid = false
        } else {
            try {
                val height = etHeight.text.toString().toDouble()
                if (height < 50 || height > 300) {
                    tilHeight.error = getString(R.string.error_height_range)
                    isValid = false
                } else {
                    tilHeight.error = null
                }
            } catch (e: NumberFormatException) {
                tilHeight.error = getString(R.string.error_enter_numbers)
                isValid = false
            }
        }

        if (rgGender.checkedRadioButtonId == -1) {
            Toast.makeText(this, getString(R.string.error_select_gender), Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    // ===================== عرض النتائج =====================
    private fun displayResults(plan: com.example.diet.models.DietPlan) {
        cardResults.visibility = View.VISIBLE
        cardWeightAnalysis.visibility = View.VISIBLE

        tvResultTitle.text = "🥗 ${selectedDietType.getName(this)} - ${plan.patient.name}"

        tvBmiValue.text = String.format(": %.1f", plan.bmiResult.bmi)
        tvBmiCategory.text = ": ${plan.bmiResult.category.name} (${plan.bmiResult.category.range})"
        tvIdealWeight.text = String.format(": %.1f - %.1f كجم", plan.bmiResult.idealWeightMin, plan.bmiResult.idealWeightMax)

        // ✅ السعرات - من strings.xml
        val caloriesText = buildString {
            append("${getString(R.string.calories_gross)}: ${plan.patient.weightKg} × ${plan.macroTargets.caloriesPerKg.toInt()} = ${plan.macroTargets.grossCalories.toInt()} ${getString(R.string.calories_unit)}\n")
            if (plan.macroTargets.deficit > 0) {
                append("${getString(R.string.calories_deficit)}: ${plan.macroTargets.deficit.toInt()} ${getString(R.string.calories_unit)} (${getString(R.string.calories_deficit_desc)})\n")
            }
            append("${getString(R.string.calories_net)}: ${plan.macroTargets.totalCalories.toInt()} ${getString(R.string.calories_unit)}")
        }
        tvCalories.text = caloriesText

        // ✅ الماكروز - من strings.xml
        val macrosText = buildString {
            append("${getString(R.string.macro_carbs)}: ${plan.macroTargets.carbGrams.toInt()}غ (${plan.macroTargets.carbCalories.toInt()} ${getString(R.string.calories_unit)})\n")
            append("${getString(R.string.macro_protein)}: ${plan.macroTargets.proteinGrams.toInt()}غ (${plan.macroTargets.proteinCalories.toInt()} ${getString(R.string.calories_unit)})\n")
            append("${getString(R.string.macro_fat)}: ${plan.macroTargets.fatGrams.toInt()}غ (${plan.macroTargets.fatCalories.toInt()} ${getString(R.string.calories_unit)})")
        }
        tvMacros.text = macrosText

        // ✅ جدول التبادل - من strings.xml
        val exchangeText = buildString {
            val sv = plan.exchangeServes
            append("${getString(R.string.exchange_starch)}: ${sv.starch} ${getString(R.string.exchange_units)}\n")
            append("${getString(R.string.exchange_vegetables)}: ${sv.vegetables} ${getString(R.string.exchange_units)}\n")
            append("${getString(R.string.exchange_fruit)}: ${sv.fruit} ${getString(R.string.exchange_units)}\n")
            append("${getString(R.string.exchange_meat)}: ${sv.meat} ${getString(R.string.exchange_units)} (${getMeatTypeName(sv.meatType)})\n")
            append("${getString(R.string.exchange_milk)}: ${sv.milk} ${getString(R.string.exchange_units)} (${getMilkTypeName(sv.milkType)})\n")
            append("${getString(R.string.exchange_legumes)}: ${sv.legumes} ${getString(R.string.exchange_units)}\n")
            append("${getString(R.string.exchange_fat)}: ${sv.fat} ${getString(R.string.exchange_units)}\n")
            append("---\n")
            append("${getString(R.string.exchange_total_carbs)}: ${plan.exchangeMacros.totalCarb.toInt()}غ | ")
            append("${getString(R.string.exchange_total_protein)}: ${plan.exchangeMacros.totalProtein.toInt()}غ | ")
            append("${getString(R.string.exchange_total_fat)}: ${plan.exchangeMacros.totalFat.toInt()}غ")
        }
        tvExchangeTable.text = exchangeText

        // خطة الوجبات
        val mealText = buildString {
            plan.meals.forEach { meal ->
                append("\n${meal.mealType.emoji} ${meal.mealType.name}:\n")
                meal.items.forEach { item ->
                    append("  • ${item.food}: ${item.quantity}\n")
                }
                meal.exerciseNote?.let {
                    append("  ⏰ ملاحظة: $it\n")
                }
                append("\n")
            }
        }
        tvMealPlan.text = mealText

        val matchPercent = calculateMatchPercentage(plan)
        tvMatchPercentage.text = "$matchPercent%"

        displayWeightAnalysis(plan)
    }

    // ===================== حساب نسبة المطابقة =====================
    private fun calculateMatchPercentage(plan: com.example.diet.models.DietPlan): Int {
        val carbDiff = kotlin.math.abs(plan.exchangeMacros.totalCarb - plan.macroTargets.carbGrams)
        val proteinDiff = kotlin.math.abs(plan.exchangeMacros.totalProtein - plan.macroTargets.proteinGrams)
        val fatDiff = kotlin.math.abs(plan.exchangeMacros.totalFat - plan.macroTargets.fatGrams)

        val maxDiff = plan.macroTargets.carbGrams + plan.macroTargets.proteinGrams + plan.macroTargets.fatGrams
        val totalDiff = carbDiff + proteinDiff + fatDiff

        return if (maxDiff > 0) {
            ((1 - (totalDiff / maxDiff)) * 100).toInt().coerceIn(0, 100)
        } else {
            100
        }
    }

    // ===================== عرض تحليل الوزن =====================
    private fun displayWeightAnalysis(plan: com.example.diet.models.DietPlan) {
        val currentWeight = plan.patient.weightKg
        val idealMin = plan.bmiResult.idealWeightMin
        val idealMax = plan.bmiResult.idealWeightMax

        tvIdealWeightRange.text = String.format("%.1f - %.1f كجم", idealMin, idealMax)

        val difference = if (currentWeight > idealMax) {
            val diff = currentWeight - idealMax
            val advice = when {
                diff > 20 -> "⚠️ أنت تعاني من سمنة مفرطة، استشر طبيبك فوراً"
                diff > 10 -> "⚠️ تحتاج لإنقاص وزنك بشكل ملحوظ"
                diff > 5 -> "⚡ لديك زيادة في الوزن، اتبع الخطة"
                else -> "📉 زيادة بسيطة، يمكنك التخلص منها بسهولة"
            }
            tvAdvice.text = advice
            "+${String.format("%.1f", diff)} كجم فوق المثالي"
        } else if (currentWeight < idealMin) {
            val diff = idealMin - currentWeight
            tvAdvice.text = "📈 أنت تعاني من نقص الوزن، ننصح بزيادة السعرات"
            "${String.format("%.1f", diff)} كجم تحت المثالي"
        } else {
            tvAdvice.text = "✅ وزنك مثالي! حافظ على نظامك الغذائي"
            "0 كجم (وزن مثالي)"
        }

        tvWeightDifference.text = difference
    }

    // ===================== مسح النموذج =====================
    private fun clearForm() {
        etName.text?.clear()
        etAge.text?.clear()
        etWeight.text?.clear()
        etHeight.text?.clear()
        rgGender.clearCheck()
        chipGroupDiet.check(R.id.chipStandard)

        tilName.error = null
        tilAge.error = null
        tilWeight.error = null
        tilHeight.error = null

        cardResults.visibility = View.GONE
        cardWeightAnalysis.visibility = View.GONE

        layoutBmiResult.visibility = View.GONE
        seekBarBmi.progress = 0
        chipBmiStatus.text = getString(R.string.bmi_not_calculated)
        chipBmiStatus.chipBackgroundColor = android.content.res.ColorStateList.valueOf(Color.parseColor("#E0E0E0"))

        etName.requestFocus()
    }
}