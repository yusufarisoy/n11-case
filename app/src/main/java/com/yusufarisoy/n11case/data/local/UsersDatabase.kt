package com.yusufarisoy.n11case.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yusufarisoy.n11case.data.entity.LocalUser

@Database(entities = [LocalUser::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao
}
