package com.example.exploretartu.ui.questions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exploretartu.R
import kotlinx.android.synthetic.main.activity_group_size.*

class GroupSize : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_size)

        continue_btn.setOnClickListener {
            if(group_size_tv.text.toString()!=""){
                if(group_size_tv.text.toString().toInt()>10){
                    group_size_tv.error = "Unfortunately group can't be bigger than 10"
                    var intent = Intent(this, Budget::class.java)
                    startActivity(intent)
                }
            }
            else{
                group_size_tv.error = "Please enter group size"
            }
        }
    }
}