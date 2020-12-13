package com.example.exploretartu.firebase.util

import com.google.firebase.auth.FirebaseAuth

private val auth: FirebaseAuth = FirebaseAuth.getInstance()

object FirebaseUtil {

    fun logOut(){
        auth.signOut()
    }

    fun auth(): FirebaseAuth{
        return auth
    }

}