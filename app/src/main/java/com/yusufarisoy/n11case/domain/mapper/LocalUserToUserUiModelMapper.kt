package com.yusufarisoy.n11case.domain.mapper

import com.yusufarisoy.n11case.core.Mapper
import com.yusufarisoy.n11case.data.entity.LocalUser
import com.yusufarisoy.n11case.domain.model.UserUiModel
import javax.inject.Inject

class LocalUserToUserUiModelMapper @Inject constructor() :
    Mapper<LocalUser, UserUiModel> {

    override fun map(input: LocalUser): UserUiModel {
        return UserUiModel(
            login = input.login,
            id = input.id,
            avatar = input.avatar,
            favorite = input.favorite,
            name = input.name,
            followers = input.followers,
            following = input.following
        )
    }
}
