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
            val min = min_sum_et.text.toString()
            val max = max_sum_et.text.toString()

            if(min!=""  &&max!=""){
                if(max.toInt()>300){
                    max_sum_et.error = getString(R.string.too_big_sum)
                }
                else if (max.toDouble() < min.toDouble()){
                    max_sum_et.error = getString(R.string.max_smaller_min)
                }
                else{
                    val intentExact = Intent(this, ChooseActivity::class.java)
                    intentExact.putExtra("indoor",intent.getBooleanExtra("indoor", true))
                    intentExact.putExtra("group",intent.getIntExtra("group", 1))
                    intentExact.putExtra("budgetMin",min.toDouble())
                    intentExact.putExtra("budgetMax",max.toDouble())
                    startActivity(intentExact)
                    finish()
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