package com.yusufarisoy.n11case.domain.mapper

import com.yusufarisoy.n11case.core.Mapper
import com.yusufarisoy.n11case.data.entity.UserResponse
import com.yusufarisoy.n11case.domain.model.UserUiModel
import javax.inject.Inject

class UserResponseToUserUiModelMapper @Inject constructor() :
    Mapper<List<UserResponse>, List<UserUiModel>> {

    override fun map(input: List<UserResponse>): List<UserUiModel> {
        return input.map { response ->
            UserUiModel(
                login = response.login,
                id = response.id,
                avatar = response.avatarUrl,
                favorite = false,
                score = response.score
            )
        }
    }
}
