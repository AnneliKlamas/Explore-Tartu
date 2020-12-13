package com.example.exploretartu.ui.questions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exploretartu.R
import com.example.exploretartu.ui.map.MapActivity
import kotlinx.android.synthetic.main.activity_choose.*

class ChooseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)

        val intent = Intent(this, MapActivity::class.java)
        option1_btn.setOnClickListener {
            startActivity(intent)
        }
        option2_btn.setOnClickListener {
            startActivity(intent)
        }
    }
}