package com.example.exploretartu.ui.questions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exploretartu.R
import kotlinx.android.synthetic.main.activity_exact_budget.*

class ExactBudget : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exact_budget)

        to_choice_btn.setOnClickListener {
            var min = min_sum_et.text.toString()
            var max = max_sum_et.text.toString()

            if(min!=""  &&max!=""){
                if(max.toInt()>300){
                    max_sum_et.error = getString(R.string.too_big_sum)
                }
            }
            else if (min==""){
                min_sum_et.error = getString(R.string.min_error)
            }
            else if(max==""){
                max_sum_et.error = getString(R.string.max_error)
            }
        }
    }
}