package com.naky.onetomanyrelationship.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.naky.onetomanyrelationship.dao.UserDao
import com.naky.onetomanyrelationship.entity.Library
import com.naky.onetomanyrelationship.entity.User
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


@Database(entities = [User::class, Library::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object{
        @Volatile
        private var INSTANCE : UserDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context) : UserDatabase{
            val userInstance = INSTANCE
            if(userInstance != null){
                return userInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}