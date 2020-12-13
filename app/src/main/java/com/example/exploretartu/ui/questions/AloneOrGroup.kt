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

        alone_btn.setOnClickListener {
            val intent = Intent(this, Budget::class.java)
            startActivity(intent)
        }

        group_btn.setOnClickListener {
            val intent = Intent(this,GroupSize::class.java)
            startActivity(intent)
        }
    }
}