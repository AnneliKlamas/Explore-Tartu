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
            startActivity(intent)
        }
        no_budget_btn.setOnClickListener {
            val intent = Intent(this, ChooseActivity::class.java)
            startActivity(intent)
        }
        free_btn.setOnClickListener {
            val intent = Intent(this, ChooseActivity::class.java)
            startActivity(intent)
        }

    }
}