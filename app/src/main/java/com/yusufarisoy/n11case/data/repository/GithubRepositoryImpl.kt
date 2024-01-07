package com.yusufarisoy.n11case.data.repository

import com.yusufarisoy.n11case.core.networkCall
import com.yusufarisoy.n11case.data.api.GithubApi
import com.yusufarisoy.n11case.data.local.UsersDao
import com.yusufarisoy.n11case.domain.mapper.LocalUserToUserUiModelMapper
import com.yusufarisoy.n11case.domain.mapper.SearchResponseToSearchUiModelMapper
import com.yusufarisoy.n11case.domain.mapper.UserDetailResponseToUserUiModelMapper
import com.yusufarisoy.n11case.domain.mapper.UserResponseToUserUiModelMapper
import com.yusufarisoy.n11case.domain.mapper.UserUiModelToLocalUserMapper
import com.yusufarisoy.n11case.domain.model.SearchUiModel
import com.yusufarisoy.n11case.domain.model.UserUiModel
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi,
    private val usersDao: UsersDao,
    private val userResponseToUserUiModelMapper: UserResponseToUserUiModelMapper,
    private val searchResponseToSearchUiModelMapper: SearchResponseToSearchUiModelMapper,
    private val userDetailResponseToUserUiModelMapper: UserDetailResponseToUserUiModelMapper,
    private val localUserToUserUiModelMapper: LocalUserToUserUiModelMapper,
    private val userUiModelToLocalUserMapper: UserUiModelToLocalUserMapper
) : GithubRepository {

    override suspend fun getUsers(): List<UserUiModel> {
        val cachedUsers = usersDao.getUsers()
        if (cachedUsers.isEmpty()) {
            val response = networkCall { githubApi.getUsers(20) }
            val userUiModels = userResponseToUserUiModelMapper.map(response)
            val localUsers = userUiModels.map { userUiModelToLocalUserMapper.map(it) }
            usersDao.insert(localUsers)

            return userUiModels
        }

        return cachedUsers.map { localUserToUserUiModelMapper.map(it) }
    }

    override suspend fun searchUser(query: String): SearchUiModel {
        val response = networkCall { githubApi.searchUsers(query) }

        return searchResponseToSearchUiModelMapper.map(response)
    }

    override suspend fun getUserDetail(id: Int, username: String): UserUiModel {
        val cachedUser = usersDao.getUserById(id)
        if (cachedUser.following == null || cachedUser.followers == null) {
            val response = networkCall { githubApi.getUserDetail(username) }
            val userUiModel = userDetailResponseToUserUiModelMapper.map(response).copy(favorite = cachedUser.favorite)
            usersDao.update(userUiModelToLocalUserMapper.map(userUiModel))

            return userUiModel
        }

        return localUserToUserUiModelMapper.map(cachedUser)
    }

    override suspend fun updateLocalUser(user: UserUiModel) {
        val localUser = userUiModelToLocalUserMapper.map(user)
        usersDao.update(localUser)
    }

    override suspend fun deleteLocalUser(user: UserUiModel) {
        val localUser = userUiModelToLocalUserMapper.map(user)
        usersDao.delete(localUser)
    }
}
