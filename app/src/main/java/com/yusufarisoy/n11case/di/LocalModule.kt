package com.yusufarisoy.n11case.di

import android.content.Context
import androidx.room.Room
import com.yusufarisoy.n11case.data.local.UsersDao
import com.yusufarisoy.n11case.data.local.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    fun provideCartDao(usersDatabase: UsersDatabase): UsersDao = usersDatabase.usersDao()

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): UsersDatabase {
        return Room
            .databaseBuilder(context, UsersDatabase::class.java, "UsersDatabase")
            .build()
    }
}
