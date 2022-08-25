package com.example.myrealinatagremclonapp.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myrealinatagremclonapp.AccountSettingsActivity
import com.example.myrealinatagremclonapp.R
import com.example.myrealinatagremclonapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        val view = binding
        binding.editProfileBtn.setOnClickListener {
            startActivity(Intent(requireContext(),AccountSettingsActivity::class.java))
        }
        return  view.root
    }


}