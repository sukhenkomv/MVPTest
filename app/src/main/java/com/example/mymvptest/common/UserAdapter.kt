package com.example.mymvptest.common

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import com.example.mymvptest.R


class UserAdapter : RecyclerView.Adapter<UserHolder>() {

    var data: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {

        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.user_item,
                    parent,
                    false
                )

        return UserHolder(view)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(data[position]);
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setDataaaaaa(users: MutableList<User>) {
        data.clear()
        data.addAll(users)
        notifyDataSetChanged()
        Log.d("AAA", "add users = $itemCount")
    }
}