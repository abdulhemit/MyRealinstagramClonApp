package com.example.myrealinatagremclonapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.myrealinatagremclonapp.Model.User
import com.example.myrealinatagremclonapp.R
import com.example.myrealinatagremclonapp.databinding.LayoutUserItemSearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.util.*

class UserAdapter (var mUser: List<User>, var isFragment : Boolean = false):RecyclerView.Adapter<UserAdapter.UserHolder>() {

    private var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    class UserHolder(var binding : LayoutUserItemSearchBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        var binding = LayoutUserItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.binding.userNameSearch.setText(mUser.get(position).userName)
        holder.binding.fullNameSearch.setText(mUser.get(position).fullName)
        Picasso.get().load(mUser.get(position).image).placeholder(R.drawable.profile).into(holder.binding.imageProfileSearch)

        checkFollowingStatus(mUser.get(position).uid,holder.binding.followBtn)
        var user = mUser[position]
        holder.binding.followBtn.setOnClickListener {
            if(holder.binding.followBtn.text.toString() == "Follow")
            {
                firebaseUser?.uid.let { it1 ->
                    FirebaseDatabase.getInstance().reference
                        .child("Follow").child(it1.toString())
                        .child("Following").child(user.uid.toString())
                        .setValue(true)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful)
                            {

                                firebaseUser?.uid.let { it1 ->
                                    FirebaseDatabase.getInstance().reference
                                        .child("Follow").child(user.uid.toString())
                                        .child("Followers").child(it1.toString())
                                        .setValue(true)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful)
                                            {

                                            }
                                        }
                                }

                            }
                        }
                }
            }
            else
            {
                firebaseUser?.uid.let { it1 ->
                    FirebaseDatabase.getInstance().reference
                        .child("Follow").child(it1.toString())
                        .child("Following").child(user.uid.toString())
                        .removeValue()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful)
                            {

                                firebaseUser?.uid.let { it1 ->
                                    FirebaseDatabase.getInstance().reference
                                        .child("Follow").child(user.uid.toString())
                                        .child("Followers").child(it1.toString())
                                        .removeValue()
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful)
                                            {

                                            }
                                        }
                                }

                            }
                        }
                }

            }
        }
    }

    private fun checkFollowingStatus(uid: String?, followBtn: Button) {

        val followingRef = firebaseUser?.uid.let { it1 ->
            FirebaseDatabase.getInstance().reference
                .child("Follow").child(it1.toString())
                .child("Following")
                }
        followingRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(uid.toString()).exists()){

                    followBtn.text = "Following"
                }else{
                    followBtn.text = "Follow"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    override fun getItemCount(): Int {
        return mUser.size
    }

}