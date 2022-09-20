package com.example.myrealinatagremclonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.myrealinatagremclonapp.Model.User
import com.example.myrealinatagremclonapp.databinding.ActivityAccountSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.core.utilities.Utilities
import com.squareup.picasso.Picasso

class AccountSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountSettingsBinding
    private lateinit var firebaseUser: FirebaseUser
    private var checker = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSettingsBinding.inflate(layoutInflater)
        val view = binding
        setContentView(view.root)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        binding.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@AccountSettingsActivity,SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        binding.saveInfoProfileEdit.setOnClickListener {
            if (checker == "clicked")
            {

            }else
            {
                updateUserInfoOnly()
            }
        }

        userInfo()
    }

    // kullanici bilgilerini guncellemek
    private fun updateUserInfoOnly() {
        when {
            TextUtils.isEmpty(binding.editFullName.text.toString()) -> {
                Toast.makeText(this,"Please write fullName first", Toast.LENGTH_LONG).show()
            }
            TextUtils.isEmpty(binding.editUserName.text.toString()) -> {
                Toast.makeText(this,"Please write userName first", Toast.LENGTH_LONG).show()
            }
            TextUtils.isEmpty(binding.editBioProfile.text.toString()) -> {
                Toast.makeText(this,"Please write your bio first", Toast.LENGTH_LONG).show()
            }
            else -> {
                val userRef : DatabaseReference = FirebaseDatabase.getInstance().reference.child("User")

                val userMap = HashMap<String,Any>()
                userMap["fullName"] = binding.editFullName.text.toString().toLowerCase()
                userMap["userName"] = binding.editUserName.text.toString().toLowerCase()
                userMap["bio"] = binding.editBioProfile.text.toString().toLowerCase()

                userRef.child(firebaseUser.uid).updateChildren(userMap)

                Toast.makeText(this,"Account Information  has been updated successful",Toast.LENGTH_LONG).show()

                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


    }

    // kullanici bilgilerini getirmek
    private fun userInfo(){
        val usersPref = FirebaseDatabase.getInstance().getReference().child("User").child(firebaseUser.uid)

        usersPref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                if (context != null){
//                    return
//                }
                if (snapshot.exists()){
                    val user = snapshot.getValue<User>(User::class.java)
                    Picasso.get().load(user?.image).placeholder(R.drawable.profile_icon).into(binding.imageProfileSettingsActivity)
                    binding.editFullName.setText(user?.fullName)
                    binding.editUserName.setText(user?.userName)
                    binding.editBioProfile.setText(user?.bio)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}