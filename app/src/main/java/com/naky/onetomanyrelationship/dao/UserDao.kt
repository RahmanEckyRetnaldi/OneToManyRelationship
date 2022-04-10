package com.naky.onetomanyrelationship.dao

import androidx.room.*
import com.naky.onetomanyrelationship.entity.Library
import com.naky.onetomanyrelationship.entity.User
import com.naky.onetomanyrelationship.entity.UserAndLibrary


@Dao
interface UserDao {
    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertUser(item: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLibrary(item: List<Library>)

    @Transaction
    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getUserAndLibraries(userId : Int) : List<UserAndLibrary>
}