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
            val intentBudget = Intent(this, ExactBudget::class.java)
            intentBudget.putExtra("indoor",intent.getBooleanExtra("indoor", true))
            intentBudget.putExtra("group",intent.getIntExtra("group", 1))
            startActivity(intentBudget)
            finish()
        }
        no_budget_btn.setOnClickListener {
            val intentNoBudget = Intent(this, ChooseActivity::class.java)
            intentNoBudget.putExtra("indoor",intent.getBooleanExtra("indoor", true))
            intentNoBudget.putExtra("group",intent.getIntExtra("group", 1))
            intentNoBudget.putExtra("budgetMin",0.0)
            intentNoBudget.putExtra("budgetMax",100000.0)
            startActivity(intentNoBudget)
            finish()
        }
        free_btn.setOnClickListener {
            val intentFree = Intent(this, ChooseActivity::class.java)
            intentFree.putExtra("indoor",intent.getBooleanExtra("indoor", true))
            intentFree.putExtra("group",intent.getIntExtra("group", 1))
            intentFree.putExtra("budgetMin",0.0)
            intentFree.putExtra("budgetMax",0.0)
            startActivity(intentFree)
            finish()
        }
    }
}