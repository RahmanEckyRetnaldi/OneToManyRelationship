package com.naky.onetomanyrelationship.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naky.onetomanyrelationship.dao.UserDao
import com.naky.onetomanyrelationship.database.UserDatabase
import com.naky.onetomanyrelationship.entity.Library
import com.naky.onetomanyrelationship.entity.User
import com.naky.onetomanyrelationship.entity.UserAndLibrary

class UserRepository(private val userDao: UserDao) {

    var readAllData : LiveData<List<UserAndLibrary>>? = null

    suspend fun addUser(item : List<User>){
        userDao.insertUser(item)
    }

    suspend fun addLibrary(item : List<Library>){
        userDao.insertLibrary(item)
    }

    fun getUserAndLibrary(userId : Int) : List<UserAndLibrary>{
        return userDao.getUserAndLibraries(userId = userId)
    }
}