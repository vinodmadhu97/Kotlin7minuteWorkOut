package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_bmiactivity.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW = "METRIC"
    val US_UNIT_VIEW = "US"
    var currentVisibleView = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        setSupportActionBar(toolBar_bmi_activity)
        val actionbar = supportActionBar

        if (actionbar != null){
            actionbar!!.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "BMI Calculator"
        }
        toolBar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btn_bmi_calculator.setOnClickListener {
            if (currentVisibleView == METRIC_UNITS_VIEW){
                if(isValidateMetricValues()){
                    val height : Float = et_height.text.toString().toFloat()/100
                    val weight : Float = et_weight.text.toString().toFloat()

                    val bmi = weight / (height*height)
                    displayBMIValue(bmi)
                }else{
                    Toast.makeText(this,"please fill all fields",Toast.LENGTH_SHORT).show()
                }
            }else{
                if (isValidUsMetricValue()){
                    val weight = et_us_weight.text.toString().toFloat()
                    val feet = et_us_feet.text.toString()
                    val inch = et_us_inch.text.toString()

                    val height = inch.toFloat() + feet.toFloat() * 12

                    val bmi = 703 * (weight / (height * height))
                    displayBMIValue(bmi)
                }else{
                    Toast.makeText(this,"please fill all fields",Toast.LENGTH_SHORT).show()
                }
            }

        }

        ll_bmi_data.visibility = View.INVISIBLE
        ll_us_units.visibility = View.GONE

        rg_units.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_metric_unit){
                makeVisibleMetricUnitView()
            }else{
                makeVisibleUsUnitView()
            }
        }

    }

    private fun isValidateMetricValues() : Boolean{
        var isValid = true

        if (et_weight.text.toString().isEmpty()){
            isValid = false
        }else if(et_height.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }
    private fun isValidUsMetricValue() : Boolean{
        var isValid = true

        when {
            et_us_feet.text.toString().isEmpty() -> {
                isValid = false
            }
            et_us_inch.text.toString().isEmpty() -> {
                isValid = false
            }
            et_us_weight.text.toString().isEmpty() -> {
                isValid = false
            }
        }

        return isValid
    }

    private fun displayBMIValue(bmi:Float){
        val bmiLabel : String
        val bmiDescription : String

        if (java.lang.Float.compare(bmi, 15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 15f) > 0 && java.lang.Float.compare(
                bmi,
                16f
            ) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 16f) > 0 && java.lang.Float.compare(
                bmi,
                18.5f
            ) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 18.5f) > 0 && java.lang.Float.compare(
                bmi,
                25f
            ) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 30f) > 0 && java.lang.Float.compare(
                bmi,
                35f
            ) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 35f) > 0 && java.lang.Float.compare(
                bmi,
                40f
            ) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        ll_bmi_data.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        tv_bmi_value.text = bmiValue
        tv_bmi_label.text = bmiLabel
        tv_bmi_description.text = bmiDescription


    }

    private fun makeVisibleMetricUnitView(){
        currentVisibleView = METRIC_UNITS_VIEW

        ll_metric_units.visibility = View.VISIBLE
        ll_us_units.visibility = View.GONE

        ll_bmi_data.visibility = View.INVISIBLE

        et_height.text!!.clear()
        et_weight.text!!.clear()
    }

    private fun makeVisibleUsUnitView(){
        currentVisibleView = US_UNIT_VIEW

        ll_metric_units.visibility = View.GONE
        ll_us_units.visibility = View.VISIBLE

        ll_bmi_data.visibility = View.INVISIBLE

        et_us_weight.text!!.clear()
        et_us_feet.text!!.clear()
        et_us_inch.text!!.clear()
    }

}