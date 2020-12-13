package com.example.exploretartu.ui.questions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exploretartu.R
import kotlinx.android.synthetic.main.activity_weather.*

class Weather : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)


        val intent = Intent(this, AloneOrGroup::class.java)
        inside_btn.setOnClickListener {
            startActivity(intent)
        }
        outside_btn.setOnClickListener {
            startActivity(intent)
        }
        inside_outside_btn.setOnClickListener {
            startActivity(intent)
        }
    }
}