package com.example.mymvptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymvptest.common.UserAdapter
import com.example.mymvptest.mvp.UserData
import com.example.mymvptest.mvp.UsersPresenter
import android.widget.Toast
import com.example.mymvptest.common.User
import com.example.mymvptest.mvp.UsersModel
import com.example.mymvptest.database.DbHelper

class UsersActivity : AppCompatActivity() {

    lateinit var editName: EditText
    lateinit var editEmail: EditText

    lateinit var userAdapter: UserAdapter

    lateinit var progressBar: ProgressBar

    lateinit var presenter: UsersPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        init()
    }

    fun init() {
        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        findViewById<Button>(R.id.addButton).setOnClickListener {
            presenter.add()
        }

        findViewById<Button>(R.id.clearButton).setOnClickListener {
            presenter.clear()
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        userAdapter = UserAdapter()

        val userList = findViewById<View>(R.id.list) as RecyclerView
        userList.layoutManager = layoutManager
        userList.adapter = userAdapter

        val dbHelper = DbHelper(this)
        val usersModel = UsersModel(dbHelper)
        presenter = UsersPresenter(usersModel)
        presenter.attachView(this)
        presenter.viewIsReady()

    }

    fun getUserData(): UserData {
        val userData = UserData()
        userData.setName(editName.getText().toString())
        userData.setEmail(editEmail.getText().toString())
        return userData
    }

    fun showToast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    fun showUsers(users: MutableList<User>) {
        userAdapter.setDataaaaaa(users)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}