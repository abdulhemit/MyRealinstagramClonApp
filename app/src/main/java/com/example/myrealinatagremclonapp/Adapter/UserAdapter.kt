package com.example.myrealinatagremclonapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myrealinatagremclonapp.Model.User
import com.example.myrealinatagremclonapp.R
import com.example.myrealinatagremclonapp.databinding.LayoutUserItemSearchBinding
import com.squareup.picasso.Picasso

class UserAdapter (var mUser: List<User>, var isFragment : Boolean = false):RecyclerView.Adapter<UserAdapter.UserHolder>() {

    class UserHolder(var binding : LayoutUserItemSearchBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        var binding = LayoutUserItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.binding.userNameSearch.setText(mUser.get(position).userName)
        holder.binding.fullNameSearch.setText(mUser.get(position).fullName)
        Picasso.get().load(mUser.get(position).image).placeholder(R.drawable.profile).into(holder.binding.imageProfileSearch)
    }

    override fun getItemCount(): Int {
        return mUser.size
    }

}