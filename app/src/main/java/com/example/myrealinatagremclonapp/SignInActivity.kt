package com.example.myrealinatagremclonapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.myrealinatagremclonapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding
        setContentView(view.root)
        binding.signupLinkBtn.setOnClickListener {
            startActivity(Intent(this,SingUpActivity::class.java))
        }
        binding.loginBtn.setOnClickListener {
            loginuser()
        }
    }

    private fun loginuser() {
        when{
            TextUtils.isEmpty(binding.emailLogin.text.toString()) -> Toast.makeText(this,"Email is required",
                Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(binding.passwordLogin.text.toString()) -> Toast.makeText(this,"Password Name is required",
                Toast.LENGTH_LONG).show()
            else->{
                val progressDialog = ProgressDialog(this@SignInActivity)
                progressDialog.setTitle("Login")
                progressDialog.setMessage("Please wait, this may take a while....")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.signInWithEmailAndPassword(binding.emailLogin.text.toString(),binding.passwordLogin.text.toString())
                    .addOnCompleteListener { task->
                        if (task.isSuccessful){
                            progressDialog.dismiss()
                            val intent = Intent(this@SignInActivity,MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this,"Error: ${task.exception.toString()}",Toast.LENGTH_LONG).show()
                            FirebaseAuth.getInstance().signOut()
                            progressDialog.dismiss()
                        }
                    }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding == null
    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            val intent = Intent(this@SignInActivity,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}