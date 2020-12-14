package com.example.exploretartu.ui.questions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.view.View
import android.widget.Toast
import com.example.exploretartu.R
import com.example.exploretartu.firebase.util.FirebaseUtil
import com.example.exploretartu.ui.map.MapActivity
import kotlinx.android.synthetic.main.activity_choose.*

class ChooseActivity : AppCompatActivity() {

    private val db = FirebaseUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)

        val intentMap = Intent(this, MapActivity::class.java)
        generateActivities(intentMap)

        val toast = Toast.makeText(this, "Loading activities", Toast.LENGTH_SHORT)
        toast.show()

        db.getMatchingTasks(intent.getDoubleExtra("budgetMin",0.0),
                intent.getDoubleExtra("budgetMax",10000.0),
                intent.getIntExtra("group", 1),
                intent.getBooleanExtra("indoor", true)){
            task1, task2 ->
            option1_btn.text=task1.taskName
            option2_btn.text=task2.taskName

            //Progressbar is set gone
            pb_progress.visibility = View.GONE
            toast.cancel()

            //Show
            option1_btn.visibility = View.VISIBLE
            option2_btn.visibility = View.VISIBLE
            generate_new_btn.visibility = View.VISIBLE
        }

        option1_btn.setOnClickListener {
            intentMap.putExtra("isTask1", true)
            startActivity(intentMap)
            finish()
        }
        option2_btn.setOnClickListener {
            intentMap.putExtra("isTask1", false)
            startActivity(intentMap)
            finish()
        }

        generate_new_btn.setOnClickListener {
            generateActivities(intentMap)
        }
    }

    private fun generateActivities(intentMap:Intent){
        db.getMatchingTasks(intent.getDoubleExtra("budgetMin",0.0),
                intent.getDoubleExtra("budgetMax",10000.0),
                intent.getIntExtra("group", 1),
                intent.getBooleanExtra("indoor", true)){
            task1, task2 ->

            option1_btn.text=task1.taskName
            option2_btn.text=task2.taskName

            intentMap.putExtra("task1", task1)
            intentMap.putExtra("task2", task2)
        }
    }
}