package com.example.exploretartu.ui.questions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exploretartu.R
import kotlinx.android.synthetic.main.activity_alone_or_group.*

class AloneOrGroup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alone_or_group)

        val budgetIntent = Intent(this, Budget::class.java)

        budgetIntent.putExtra("indoor", intent.getBooleanExtra("indoor", true))

        alone_btn.setOnClickListener {
            budgetIntent.putExtra("group", 1)
            startActivity(budgetIntent)
        }

        group_btn.setOnClickListener {
            val groupSizeIntent = Intent(this, GroupSize::class.java)
            groupSizeIntent.putExtra("indoor", intent.getBooleanExtra("indoor", true))
            startActivity(groupSizeIntent)
        }
    }
}