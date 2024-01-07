package com.yusufarisoy.n11case.di

import com.yusufarisoy.n11case.data.repository.GithubRepository
import com.yusufarisoy.n11case.data.repository.GithubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun provideGithubRepository(githubRepositoryImpl: GithubRepositoryImpl): GithubRepository
}
