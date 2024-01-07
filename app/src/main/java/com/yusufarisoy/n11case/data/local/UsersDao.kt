package com.yusufarisoy.n11case.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yusufarisoy.n11case.data.entity.LocalUser

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<LocalUser>)

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<LocalUser>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): LocalUser

    @Update
    suspend fun update(user: LocalUser)

    @Delete
    suspend fun delete(user: LocalUser)

    @Query("DELETE FROM users")
    suspend fun clear()
}
