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
        }



        option1_btn.setOnClickListener {
            startMapActivity(true)
        }
        option2_btn.setOnClickListener {
            startMapActivity(false)
        }
    }

    fun startMapActivity(task1Boolean: Boolean){
        db.getMatchingTasks(intent.getDoubleExtra("budgetMin",0.0),
                intent.getDoubleExtra("budgetMax",10000.0),
                intent.getIntExtra("group", 1),
                intent.getBooleanExtra("indoor", true)){
            task1, task2 ->

            val intent = Intent(this, MapActivity::class.java)
            if (task1Boolean){
                intent.putExtra("task", task1)
            }
            else{
                intent.putExtra("task", task2)
            }
            startActivity(intent)
            finish()
        }
    }
}