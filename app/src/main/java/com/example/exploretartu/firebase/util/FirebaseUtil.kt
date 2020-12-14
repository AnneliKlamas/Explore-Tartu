package com.example.exploretartu.firebase.util

import android.content.ContentValues
import android.util.Log
import com.example.exploretartu.dao.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

private val auth: FirebaseAuth = FirebaseAuth.getInstance()

object FirebaseUtil {

    private val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun logOut(){
        auth.signOut()
    }

    fun getMatchingTasks(budgetMin: Double, budgetMax: Double, group: Int, indoor: Boolean,
                         onComplete: (Task, Task) -> Unit){

        firestoreInstance.collection("tasks").whereEqualTo("indoor", indoor)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("", "Listen failed.", e)
                    return@addSnapshotListener
                }
                val tasksList = ArrayList<Task>()
                for (doc in value!!) {
                    doc?.let {
                        val task = doc.toObject(Task::class.java)
                        if(task.price <= budgetMin || task.price >= budgetMax) {
                            if(task.minPersons <= group){
                                if(task.maxPersons >= group){
                                    tasksList.add(task)
                                }
                            }

                        }
                    }
                }
                lateinit var option1: Task
                lateinit var option2: Task

                when (tasksList.size) {
                    0 -> {
                        option1 = Task()
                        option2 = Task()
                    }
                    1 -> {
                        option1 = tasksList[0]
                        option2 = tasksList[0]
                    }
                    2 -> {
                        option1 = tasksList[0]
                        option2 = tasksList[1]
                    }
                    else -> {
                        option1 = tasksList[Random.nextInt(0, tasksList.size)]
                        tasksList.remove(option1)
                        option2 = tasksList[0]
                    }
                }

                onComplete(option1,option2)
            }
    }

    fun createTask(task: Task) {
        FirebaseFirestore.getInstance().collection("tasks")
            .add(task).addOnSuccessListener { documentReference ->
                updateTask(documentReference.id, documentReference.id)
                Log.d(ContentValues.TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    private fun updateTask(id: String, taskID: String = "") {
        val taskFieldMap = mutableMapOf<String, Any>()
        if (taskID.isNotBlank()) taskFieldMap["taskID"] = taskID
        firestoreInstance.document("tasks/${id}").update(taskFieldMap)
    }
}