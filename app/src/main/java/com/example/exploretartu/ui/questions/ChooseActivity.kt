package com.example.exploretartu.ui.questions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exploretartu.R
import com.example.exploretartu.dao.Task
import com.example.exploretartu.firebase.util.FirebaseUtil
import com.example.exploretartu.ui.map.MapActivity
import kotlinx.android.synthetic.main.activity_choose.*

class ChooseActivity : AppCompatActivity() {

    private val db = FirebaseUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)

        db.getMatchingTasks(intent.getDoubleExtra("budgetMin",0.0),
            intent.getDoubleExtra("budgetMax",10000.0),
            intent.getIntExtra("group", 1),
            intent.getBooleanExtra("indoor", true)){
            task1, task2 ->
            option1_btn.text=task1.taskName
            option2_btn.text=task2.taskName
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("task1", task1)
            intent.putExtra("task2", task2)
            }


        option1_btn.setOnClickListener {
            intent.putExtra("isTask1", true)
            startActivity(intent)
        }
        option2_btn.setOnClickListener {
            intent.putExtra("isTask1", false)
            startActivity(intent)
        }
    }
}