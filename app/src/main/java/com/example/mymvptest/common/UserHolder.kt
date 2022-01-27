package com.example.mymvptest.common

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymvptest.R

class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var text = itemView.findViewById<TextView>(R.id.text)

    fun bind(user: User) {
        text.text = String.format(
            "id: %s, name: %s, email: %s",
            user.getId(),
            user.getName(),
            user.getEmail()
        )
    }
}
