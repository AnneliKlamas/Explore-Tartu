package com.example.exploretartu.ui.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.exploretartu.R
import com.example.exploretartu.firebase.util.FirebaseUtil
import com.example.exploretartu.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    private val util = FirebaseUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        bt_logout.setOnClickListener {
            logOut()
        }
    }

    private fun logOut(){
        util.logOut()
        Toast.makeText(this, "Logging out!", Toast.LENGTH_SHORT).show()
        startLoginActivity()
    }

    private fun startLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}