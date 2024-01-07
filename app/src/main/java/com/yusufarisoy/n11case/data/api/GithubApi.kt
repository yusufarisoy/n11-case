package com.yusufarisoy.n11case.data.api

import com.yusufarisoy.n11case.data.entity.SearchResponse
import com.yusufarisoy.n11case.data.entity.UserDetailResponse
import com.yusufarisoy.n11case.data.entity.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET(Endpoints.USERS)
    suspend fun getUsers(
        @Query(Queries.PER_PAGE) perPage: Int
    ): Response<List<UserResponse>>

    @GET(Endpoints.SEARCH)
    suspend fun searchUsers(
        @Query(Queries.QUERY) query: String
    ): Response<SearchResponse>

    @GET(Endpoints.USER_DETAIL)
    suspend fun getUserDetail(
        @Path(Paths.USERNAME) username: String
    ): Response<UserDetailResponse>

    companion object {
        private object Endpoints {
            const val USERS = "users"
            const val SEARCH = "search/users"
            const val USER_DETAIL = "users/{${Paths.USERNAME}}"
        }

        private object Queries {
            const val QUERY = "q"
            const val PER_PAGE = "per_page"
        }

        private object Paths {
            const val USERNAME = "username"
        }
    }
}
