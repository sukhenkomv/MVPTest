package com.example.mymvptest.mvp

import android.content.ContentValues
import android.database.Cursor
import com.example.mymvptest.common.User
import com.example.mymvptest.common.UserTable
import com.example.mymvptest.database.DbHelper


class UsersModel(dbh: DbHelper) {

    private val dbHelper: DbHelper = dbh

    interface LoadUserCallback {
        fun onLoad(users: MutableList<User>)
    }

    interface CompleteCallback {
        fun onComplete()
    }

    fun loadUsers(callback: LoadUserCallback?) {
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
        callback?.onLoad(users)
    }

    fun addUser(cvuser: ContentValues, callback: CompleteCallback?) {
        dbHelper.getWritableDatabase().insert(UserTable.TABLE, null, cvuser)
        callback?.onComplete();
    }

    fun clearUsers(callback: CompleteCallback?) {
        dbHelper.getWritableDatabase().delete(UserTable.TABLE, null, null);
        callback?.onComplete()
    }
}
