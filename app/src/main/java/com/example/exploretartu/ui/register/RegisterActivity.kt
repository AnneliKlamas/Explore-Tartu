package com.example.exploretartu.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.exploretartu.MainActivity
import com.example.exploretartu.R
import com.example.exploretartu.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_register.*

private const val TAG_REGISTER_EVENT = "Registering event"
private lateinit var auth: FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_register)

        bt_register_account.setOnClickListener {
            val name: String = et_register_name.text.toString()
            val email: String = et_register_email.text.toString()
            val password: String = et_register_password.text.toString()
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isNotBlank() && name.isNotBlank()){
                startRegisterNewUser(email, password, name)
            }
        }
    }

    private fun startRegisterNewUser(email: String, password: String, name: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG_REGISTER_EVENT, "createUserWithEmail:success")
                    val user = auth.currentUser
                    //Lisa kasutaja kontole tema nimi juurde
                    val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                    user?.updateProfile(profileUpdates)?.addOnSuccessListener {
                        Toast.makeText(this, "Welcome, ${user.displayName}", Toast.LENGTH_SHORT).show()
                        startMainActivity()
                    }
                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG_REGISTER_EVENT, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Registering failed!", Toast.LENGTH_SHORT).show()

                }
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}