package com.yusufarisoy.n11case.domain.mapper

import com.yusufarisoy.n11case.core.Mapper
import com.yusufarisoy.n11case.data.entity.SearchResponse
import com.yusufarisoy.n11case.domain.model.SearchUiModel
import javax.inject.Inject

class SearchResponseToSearchUiModelMapper @Inject constructor(
    private val userResponseToUserUiModelMapper: UserResponseToUserUiModelMapper
) : Mapper<SearchResponse, SearchUiModel> {

    override fun map(input: SearchResponse): SearchUiModel {
        return SearchUiModel(
            totalCount = input.totalCount,
            users = userResponseToUserUiModelMapper.map(input.users)
        )
    }
}
