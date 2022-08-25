package com.example.myrealinatagremclonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myrealinatagremclonapp.databinding.ActivitySignInBinding

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
    }

    override fun onDestroy() {
        super.onDestroy()
        binding == null
    }
}