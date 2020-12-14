package com.example.exploretartu.ui.addTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.exploretartu.R
import com.example.exploretartu.dao.Task
import com.example.exploretartu.firebase.util.FirebaseUtil
import kotlinx.android.synthetic.main.fragment_add_task.*

class AddTaskFragment : Fragment() {

    private lateinit var addTaskViewModel: AddTaskViewModel
    private val db = FirebaseUtil

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addTaskViewModel =
                ViewModelProvider(this).get(AddTaskViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_task, container, false)
        addTaskViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_activity_btn.setOnClickListener {
            val task = Task()
            var noErrors = true
            val location = arrayOf(0.0,0.0)
            if(activity_name_et.text.toString()!=""){
                task.taskName = activity_name_et.text.toString()
            }
            else{
                activity_name_et.error = getString(R.string.empty_activity_name)
                noErrors=false
            }
            if(latitude_et.text.toString()!=""){
                location[0]=latitude_et.text.toString().toDouble()
            }
            else{
                latitude_et.error = getString(R.string.empty_latitude)
                noErrors=false
            }
            if(longitude_et.text.toString()!=""){
                location[1] = longitude_et.text.toString().toDouble()
            }
            else{
                longitude_et.error = getString(R.string.empty_longitude)
                noErrors=false
            }
            if(!indoor_rb.isChecked && !outdoor_rb.isChecked){
                indoor_rb.error = getString(R.string.select_indoor_or)
                outdoor_rb.error = getString(R.string.select_indoor_or)
                noErrors=false
            }
            else {task.indoor=indoor_rb.isChecked}
            if(price_et.text.toString()!=""){
                task.price=price_et.text.toString().toDouble()
            }
            else{
                price_et.error=getString(R.string.empty_price)
                noErrors=false
            }
            if(min_person_et.text.toString()!=""){
                task.minPersons = min_person_et.text.toString().toInt()
            }
            else{
                min_person_et.error = getString(R.string.empty_min_person)
                noErrors=false
            }
            if(max_person_et.text.toString()!=""){
                task.maxPersons = max_person_et.text.toString().toInt()
            }
            else{
                max_person_et.error = getString(R.string.empty_max_person)
                noErrors=false
            }
            if(noErrors){
                task.taskLocation= arrayListOf(location[0],location[1])
                db.createTask(task)
                clearAll()
                Toast.makeText(context,"Your task was sucsessfully added", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearAll(){
        activity_name_et.text.clear()
        latitude_et.text.clear()
        longitude_et.text.clear()
        indoor_rb.isChecked = false
        outdoor_rb.isChecked = false
        price_et.text.clear()
        min_person_et.text.clear()
        max_person_et.text.clear()
    }
}