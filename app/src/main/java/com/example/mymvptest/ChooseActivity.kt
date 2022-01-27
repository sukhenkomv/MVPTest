package com.example.mymvptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View

class ChooseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)

        findViewById<View>(R.id.activity).setOnClickListener {
            startActivity(
                Intent(
                    this@ChooseActivity,
                    SingleActivity::class.java
                )
            )
        }

        findViewById<View>(R.id.mvp).setOnClickListener {
            startActivity(
                Intent(
                    this@ChooseActivity,
                    UsersActivity::class.java
                )
            )
        }

    }
}