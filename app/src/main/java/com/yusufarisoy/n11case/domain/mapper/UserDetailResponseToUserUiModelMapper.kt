package com.yusufarisoy.n11case.domain.mapper

import com.yusufarisoy.n11case.core.Mapper
import com.yusufarisoy.n11case.data.entity.UserDetailResponse
import com.yusufarisoy.n11case.domain.model.UserUiModel
import javax.inject.Inject

class UserDetailResponseToUserUiModelMapper @Inject constructor() :
    Mapper<UserDetailResponse, UserUiModel> {

    override fun map(input: UserDetailResponse): UserUiModel {
        return with(input) {
            UserUiModel(
                login = login,
                id = id,
                avatar = avatarUrl,
                name = name,
                followers = followers,
                following = following,
                favorite = false
            )
        }
    }
}
