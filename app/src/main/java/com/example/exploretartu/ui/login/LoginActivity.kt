package com.example.exploretartu.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.exploretartu.MainActivity
import com.example.exploretartu.R
import com.example.exploretartu.ui.register.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*


private lateinit var auth: FirebaseAuth
private lateinit var mGoogleSignInClient: GoogleSignInClient
private val RC_GOOGLE_SIGN_IN: Int = 100

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        auth = FirebaseAuth.getInstance()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        setContentView(R.layout.activity_login)

        if (FirebaseAuth.getInstance().currentUser != null){
            startMainActivity()
        }

        bt_register.setOnClickListener {
            startRegisterActivity()
        }

        bt_login_email.setOnClickListener {
            startEmailLogin()
        }

        bt_login_google.setOnClickListener {
            startGoogleLogin()
        }
    }

    private fun startRegisterActivity(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startEmailLogin(){
        var allCorrect = true
        if(et_login_email.text.toString().isBlank()){
            et_login_email.error = getString(R.string.fill_email)
            allCorrect = false
        }
        if(et_login_password.text.toString().isBlank()){
            et_login_password.error = getString(R.string.fill_password)
            allCorrect = false
        }
        if(allCorrect){
            auth.signInWithEmailAndPassword(et_login_email.text.toString(),
                et_login_password.text.toString())
                .addOnCompleteListener{task ->
                    if (task.isSuccessful){
                        val user = auth.currentUser
                        if (user != null) {
                            Toast.makeText(this,
                                "Successfully Logged in!\n" +
                                        "Welcome back, ${user.displayName}!", Toast.LENGTH_LONG).show()
                        }
                        startMainActivity()
                    }
                    else {
                        Toast.makeText(this, "Error when logging in, check credentials!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun startGoogleLogin(){
        val signinIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signinIntent, RC_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE_SIGN_IN){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handdleSigninResult(task)
        }
    }

    private fun handdleSigninResult(task: Task<GoogleSignInAccount>) {
        try {
            val acc: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show()
            FirebaseGoogleAuth(acc)
        }
        catch (e: ApiException){
            Toast.makeText(this, "Signin failed", Toast.LENGTH_SHORT).show()
            FirebaseGoogleAuth(null)
        }
    }

    private fun FirebaseGoogleAuth(acc: GoogleSignInAccount?) {
        acc?.apply {
            val authCredential: AuthCredential = GoogleAuthProvider.getCredential(acc.idToken, null)
            auth.signInWithCredential(authCredential).addOnCompleteListener {
                if (it.isSuccessful){
                    val user = auth.currentUser
                    if (user != null) {
                        Toast.makeText(applicationContext, "Welcome back, ${user.displayName}!", Toast.LENGTH_SHORT).show()
                    }
                    startMainActivity()
                }
                else{
                    Toast.makeText(applicationContext, "Unsuccessful", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}