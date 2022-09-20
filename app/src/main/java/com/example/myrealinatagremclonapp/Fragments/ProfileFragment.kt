package com.example.myrealinatagremclonapp.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myrealinatagremclonapp.AccountSettingsActivity
import com.example.myrealinatagremclonapp.Model.User
import com.example.myrealinatagremclonapp.R
import com.example.myrealinatagremclonapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileId : String
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        val view = binding

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)
        if (pref != null)
        {
            this.profileId = pref.getString("profileId","noon").toString()
        }
        if (profileId == firebaseUser.uid)
        {
            binding.editProfileBtn.text = "Edit Profile"
        }else if (profileId != firebaseUser.uid)
        {

            //
            checkFollowAndFollowingButtonStatus()
        }


        //profile edit button islemleri
        binding.editProfileBtn.setOnClickListener {
            //startActivity(Intent(requireContext(),AccountSettingsActivity::class.java))
            val getButtonText = binding.editProfileBtn.text.toString()
            when {
                getButtonText == "Edit Profile" -> startActivity(Intent(requireContext(),AccountSettingsActivity::class.java))

                // kullaniciyi takip etmek icin follow and following button click
                getButtonText == "Follow" -> {
                     firebaseUser.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(it1.toString())
                            .child("Following").child(profileId)
                            .setValue(true)
                    }
                     firebaseUser.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(profileId)
                            .child("Followers").child(it1.toString())
                            .setValue(true)
                    }
                }

                getButtonText == "Following" -> {
                    firebaseUser.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(it1.toString())
                            .child("Following").child(profileId)
                            .removeValue()
                    }
                    firebaseUser.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow").child(profileId)
                            .child("Followers").child(it1.toString())
                            .removeValue()
                    }
                }
                else -> {

                }
            }

        }

        getFollowing()
        getFollowers()
        userInfo()

        return  view.root
    }

    private fun checkFollowAndFollowingButtonStatus() {

        val followingRef = firebaseUser?.uid.let { it1 ->
            FirebaseDatabase.getInstance().reference
                .child("Follow").child(it1.toString())
                .child("Following")
        }
        if (followingRef != null)
        {
            followingRef.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.child(profileId).exists()){
                        binding.editProfileBtn.text = "Following"
                    }else{
                        binding.editProfileBtn.text = "Follow"
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }
    private fun getFollowers(){
        val followersRef = FirebaseDatabase.getInstance().reference
                .child("Follow").child(profileId)
                .child("Followers")

            followersRef.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        binding.profileFallowersId.text = snapshot.childrenCount.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

    }

    private fun getFollowing(){
        val followersRef = FirebaseDatabase.getInstance().reference
                .child("Follow").child(profileId)
                .child("Following")



            followersRef.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        binding.profileFallowingId.text = snapshot.childrenCount.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

    }


    private fun userInfo(){
        val usersPref = FirebaseDatabase.getInstance().getReference().child("User").child(profileId)

        usersPref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                if (context != null){
//                    return
//                }
                if (snapshot.exists()){
                    val user = snapshot.getValue<User>(User::class.java)
                    Picasso.get().load(user?.image).placeholder(R.drawable.profile_icon).into(binding.proImaageProfileFrags)
                    binding.fullNameProfileFrag.setText(user?.fullName)
                    binding.profileFragmentUserName.setText(user?.userName)
                    binding.bioProfileFrag.setText(user?.bio)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    override fun onStop() {
        super.onStop()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId",firebaseUser.uid)
        pref?.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId",firebaseUser.uid)
        pref?.apply()
    }

    override fun onPause() {
        super.onPause()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId",firebaseUser.uid)
        pref?.apply()
    }
}