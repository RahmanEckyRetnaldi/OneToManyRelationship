package com.naky.onetomanyrelationship.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.naky.onetomanyrelationship.database.UserDatabase
import com.naky.onetomanyrelationship.entity.Library
import com.naky.onetomanyrelationship.entity.User
import com.naky.onetomanyrelationship.entity.UserAndLibrary
import com.naky.onetomanyrelationship.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel (application: Application) : AndroidViewModel(application) {

    private val _readAllData  = MutableLiveData<List<UserAndLibrary>>()
    var readAllData : LiveData<List<UserAndLibrary>> = _readAllData
    private val repository : UserRepository

    init {
        val userDao = UserDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
    }

    fun getUser(userId : Int){
        viewModelScope.launch(Dispatchers.IO) {
            _readAllData.postValue(repository.getUserAndLibrary(userId = userId))
        }
    }

    fun addUser(item : List<User>){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(item = item)
        }
    }

    fun addLibrary(item: List<Library>){
        viewModelScope.launch(Dispatchers.IO){
            repository.addLibrary(item)
        }
    }
}