package com.example.exploretartu.ui.questions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exploretartu.R
import kotlinx.android.synthetic.main.activity_budget.*

class Budget : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)

        budget_btn.setOnClickListener {
            val intent = Intent(this, ExactBudget::class.java)
            intent.putExtra("indoor",intent.getBooleanExtra("indoor", true))
            intent.putExtra("group",intent.getIntExtra("group", 1))
            startActivity(intent)
        }
        no_budget_btn.setOnClickListener {
            val intent = Intent(this, ChooseActivity::class.java)
            intent.putExtra("indoor",intent.getBooleanExtra("indoor", true))
            intent.putExtra("group",intent.getIntExtra("group", 1))
            intent.putExtra("budgetMin",0.0)
            intent.putExtra("budgetMax",100000.0)
            startActivity(intent)
        }
        free_btn.setOnClickListener {
            val intent = Intent(this, ChooseActivity::class.java)
            intent.putExtra("indoor",intent.getBooleanExtra("indoor", true))
            intent.putExtra("group",intent.getIntExtra("group", 1))
            intent.putExtra("budgetMin",0.0)
            intent.putExtra("budgetMax",0.0)
            startActivity(intent)
        }
    }
}