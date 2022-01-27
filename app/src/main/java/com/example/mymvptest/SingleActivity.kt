package com.example.mymvptest

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.mymvptest.common.UserTable

import android.view.View
import com.example.mymvptest.common.User
import com.example.mymvptest.common.UserAdapter
import com.example.mymvptest.database.DbHelper

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.ContentValues
import android.widget.Toast
import android.text.TextUtils
import android.widget.ProgressBar

class SingleActivity : AppCompatActivity() {

    lateinit var editName: EditText
    lateinit var editEmail: EditText

    lateinit var userAdapter: UserAdapter
    lateinit var dbHelper: DbHelper
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        init()
        loadUsers()
    }

    fun init() {
        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)

        progressBar = findViewById(R.id.progressBar)
        hideProgress();

        userAdapter = UserAdapter()

        findViewById<Button>(R.id.addButton).setOnClickListener {
            addUser()
        }

        findViewById<Button>(R.id.clearButton).setOnClickListener {
            clearUsers()
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        val userList = findViewById<View>(R.id.list) as RecyclerView
        userList.layoutManager = layoutManager
        userList.adapter = userAdapter

        dbHelper = DbHelper(this)
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
    }

    fun loadUsers() {
        val users: MutableList<User> = mutableListOf()
        val cursor: Cursor = dbHelper.getReadableDatabase()
            .query(UserTable.TABLE, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val user = User()
            cursor.getColumnIndex(UserTable.COLUMN.ID)
            user.setId(cursor.getLong(cursor.getColumnIndex(UserTable.COLUMN.ID)))
            user.setName(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN.NAME)))
            user.setEmail(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN.EMAIL)))
            users.add(user)
        }
        cursor.close()
        userAdapter.setDataaaaaa(users);
    }

    fun addUser() {
        val name: String = editName.getText().toString()
        val email: String = editEmail.getText().toString()

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)) {
            Toast.makeText(this, R.string.empty_values, Toast.LENGTH_SHORT).show()
            return
        }

        val cv = ContentValues(2)
        cv.put(UserTable.COLUMN.NAME, name)
        cv.put(UserTable.COLUMN.EMAIL, email)

        showProgress()
        dbHelper.getWritableDatabase().insert(UserTable.TABLE, null, cv)
        hideProgress()
        loadUsers()
    }

    fun clearUsers() {
        showProgress()
        dbHelper.getWritableDatabase().delete(UserTable.TABLE, null, null);
        hideProgress();
        loadUsers();
    }

}