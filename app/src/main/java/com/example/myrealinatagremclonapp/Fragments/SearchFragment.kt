package com.example.myrealinatagremclonapp.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrealinatagremclonapp.Adapter.UserAdapter
import com.example.myrealinatagremclonapp.Model.User
import com.example.myrealinatagremclonapp.R
import com.example.myrealinatagremclonapp.databinding.FragmentProfileBinding
import com.example.myrealinatagremclonapp.databinding.FragmentSearchBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class SearchFragment : Fragment() {
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var userList : MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        recyclerView = binding.SearchFragmentRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        userList = ArrayList<User>()
        userAdapter = UserAdapter(userList,true)
        recyclerView.adapter = userAdapter

        binding.searchEditText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(binding.searchEditText.text.toString() == ""){

                }else{
                    recyclerView.visibility = View.VISIBLE
                    retrieveUsers()
                    searchUser(p0.toString().toLowerCase())
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        val view = binding


        return view.root
    }

    private fun searchUser(input: String) {
        val query = FirebaseDatabase.getInstance().getReference()
            .child("User")
            .orderByChild("fullName")
            .startAt(input)
            .endAt(input + "\uf8ff ")

        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()

                for (snapshot in snapshot.children){
                    val user = snapshot.getValue(User::class.java)
                    if (user != null){
                        userList?.add(user)
                    }

                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


    }

    private fun retrieveUsers() {
        val userRag = FirebaseDatabase.getInstance().getReference().child("User")
            userRag.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (binding.searchEditText.text.toString() == ""){
                        userList.clear()
                        for (snapshot in snapshot.children){
                            val user = snapshot.getValue(User::class.java)
                            if (user != null){
                                userList?.add(user)
                            }

                        }
                        userAdapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }


}