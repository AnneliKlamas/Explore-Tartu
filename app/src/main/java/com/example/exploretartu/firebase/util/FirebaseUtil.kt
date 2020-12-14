package com.example.exploretartu.firebase.util

import android.content.ContentValues
import android.util.Log
import com.example.exploretartu.dao.Task
import com.example.exploretartu.dao.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

private val auth: FirebaseAuth = FirebaseAuth.getInstance()

object FirebaseUtil {

    private val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("users/${
        FirebaseAuth.getInstance().uid
            ?: throw NullPointerException("UID is null.")
        }")

    fun logOut(){
        auth.signOut()
    }

    fun auth(): FirebaseAuth{
        return auth
    }

    fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                val newUser = User(currentUserDocRef.id,FirebaseAuth.getInstance().currentUser?.displayName
                    ?: "", FirebaseAuth.getInstance().currentUser?.email?: "", Task()
                )
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else
                onComplete()
        }
    }

    fun getCurrentUser(onComplete: (User) -> Unit) {
        currentUserDocRef.get()
            .addOnSuccessListener {
                it.toObject(User::class.java)?.let { it1 -> onComplete(it1) }
            }
    }

    fun getMatchingTasks(budgetMin: Double, budgetMax: Double, group: Int, indoor: Boolean,
                         outdoor: Boolean, onComplete: (Task, Task) -> Unit){

        firestoreInstance.collection("tasks").whereEqualTo("indoor", indoor)
            .whereEqualTo("outdoor",outdoor).whereGreaterThanOrEqualTo("price",budgetMin)
            .whereLessThanOrEqualTo("price",budgetMax).whereGreaterThanOrEqualTo("minPersons", group)
            .whereLessThanOrEqualTo("maxPersons",group)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("", "Listen failed.", e)
                    return@addSnapshotListener
                }
                val tasksList = ArrayList<Task>()
                for (doc in value!!) {
                    doc?.let {
                        val task = doc.toObject(Task::class.java)
                        tasksList.add(task)
                    }
                }
                var option1 = tasksList[Random.nextInt(0, tasksList.size-1)]
                tasksList.remove(option1)
                var option2 = tasksList[Random.nextInt(0, tasksList.size-1)]
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