package com.yusufarisoy.n11case.domain.model

data class SearchUiModel(
    val totalCount: Int = -1,
    val users: List<UserUiModel> = emptyList()
)
