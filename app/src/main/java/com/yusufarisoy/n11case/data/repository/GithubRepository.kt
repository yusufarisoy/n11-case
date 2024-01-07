package com.yusufarisoy.n11case.data.repository

import com.yusufarisoy.n11case.domain.model.SearchUiModel
import com.yusufarisoy.n11case.domain.model.UserUiModel

interface GithubRepository {

    suspend fun getUsers(): List<UserUiModel>

    suspend fun searchUser(query: String): SearchUiModel

    suspend fun getUserDetail(id: Int, username: String): UserUiModel

    // Local
    suspend fun updateLocalUser(user: UserUiModel)

    suspend fun deleteLocalUser(user: UserUiModel)
}
