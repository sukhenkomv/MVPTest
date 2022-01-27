package com.example.mymvptest.mvp

import android.content.ContentValues
import android.text.TextUtils
import android.widget.Toast
import com.example.mymvptest.R
import com.example.mymvptest.UsersActivity
import com.example.mymvptest.common.User
import com.example.mymvptest.common.UserTable

class UsersPresenter(m: UsersModel) {

    private var view: UsersActivity? = null
    private var model: UsersModel = m

//    fun UsersPresenter(model: UsersModel) {
//        this.model = model
//    }

    fun attachView(usersActivity: UsersActivity) {
        this.view = usersActivity
    }

    fun detachView() {
        view = null
    }

    fun viewIsReady() {
        loadUsers()
    }

    fun loadUsers() {
        model.loadUsers(object : UsersModel.LoadUserCallback {
            override fun onLoad(users: MutableList<User>) {
                view?.showUsers(users)
            }
        })
    }

    fun add() {
        val userdata: UserData? = view?.getUserData()
        if (userdata != null) {
            if (TextUtils.isEmpty(userdata.getName()) || TextUtils.isEmpty(userdata.getEmail())) {
                view?.showToast(R.string.empty_values);
                return
            }

            val cv = ContentValues(2)
            cv.put(UserTable.COLUMN.NAME, userdata.getName())
            cv.put(UserTable.COLUMN.EMAIL, userdata.getEmail())

            model.addUser(cv, object : UsersModel.CompleteCallback {
                override fun onComplete() {
                    loadUsers()
                }
            })
        }

    }

    fun clear() {
        model.clearUsers(object : UsersModel.CompleteCallback {
            override fun onComplete() {
                loadUsers()
            }
        })
    }

}