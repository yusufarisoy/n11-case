package com.yusufarisoy.n11case.domain.mapper

import com.yusufarisoy.n11case.core.Mapper
import com.yusufarisoy.n11case.data.entity.LocalUser
import com.yusufarisoy.n11case.domain.model.UserUiModel
import javax.inject.Inject

class UserUiModelToLocalUserMapper @Inject constructor() : Mapper<UserUiModel, LocalUser> {

    override fun map(input: UserUiModel): LocalUser {
        return with(input) {
            LocalUser(login, id, avatar, favorite)
        }
    }
}
