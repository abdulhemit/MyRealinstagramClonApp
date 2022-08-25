package com.example.myrealinatagremclonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myrealinatagremclonapp.databinding.ActivitySingUpBinding

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
    }

    override fun onDestroy() {
        super.onDestroy()
        binding == null
    }
}