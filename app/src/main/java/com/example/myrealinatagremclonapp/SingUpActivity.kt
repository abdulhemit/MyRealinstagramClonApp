package com.example.myrealinatagremclonapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.myrealinatagremclonapp.databinding.ActivitySingUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SingUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        val view = binding
        setContentView(view.root)
        binding.singInLinkBtn.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }
        binding.signupBtn.setOnClickListener {
            CreatAccount()
        }
    }

    private fun CreatAccount() {
        when {
            TextUtils.isEmpty(binding.FullNameLogin.text.toString()) -> Toast.makeText(this,"Full Name is required",Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(binding.UserNameLogin.text.toString()) -> Toast.makeText(this,"User Name is required",Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(binding.emailLogin.text.toString()) -> Toast.makeText(this,"Email is required",Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(binding.passwordLogin.text.toString()) -> Toast.makeText(this,"Password Name is required",Toast.LENGTH_LONG).show()

            else-> {
                val progressDialog = ProgressDialog(this@SingUpActivity)
                progressDialog.setTitle("Sign up")
                progressDialog.setMessage("Please wait, this may take a while....")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.createUserWithEmailAndPassword(binding.emailLogin.text.toString(),binding.passwordLogin.text.toString())
                    .addOnCompleteListener{ task->
                        if (task.isSuccessful){
                            saveUserInfo(binding.FullNameLogin.text.toString(),binding.UserNameLogin.text.toString(),binding.emailLogin.text.toString(),progressDialog)
                        }else{
                            Toast.makeText(this,"Error: ${task.exception.toString()}",Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }

            }
        }
    }

    private fun saveUserInfo(fullName: String, userName: String, email: String,progressDialog: ProgressDialog) {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

        val userRef : DatabaseReference = FirebaseDatabase.getInstance().reference.child("User")

        val userMap = HashMap<String,Any>()
        userMap["uid"] = currentUserId
        userMap["fullName"] = fullName.toLowerCase()
        userMap["userName"] = userName.toLowerCase()
        userMap["email"] = email
        userMap["bio"] = "Hey i am using coding cafe Instagram Clone App."
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/myrealinstagramcloneapp.appspot.com/o/Default%20Images%2Fphoto1607960843.jpeg?alt=media&token=2263ed18-01c5-46e0-8c3b-6148f7402dd8"

        userRef.child(currentUserId).setValue(userMap).addOnCompleteListener { task->
            if (task.isSuccessful){
                progressDialog.dismiss()
                Toast.makeText(this@SingUpActivity,"Account has been created successful",Toast.LENGTH_LONG).show()

                val intent = Intent(this@SingUpActivity,MainActivity::class.java)
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

    override fun onDestroy() {
        super.onDestroy()
        binding == null
    }
}