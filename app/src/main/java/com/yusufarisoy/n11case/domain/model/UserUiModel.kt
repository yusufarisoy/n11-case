package com.yusufarisoy.n11case.domain.model

data class UserUiModel(
    val login: String,
    val id: Int,
    val avatar: String,
    val favorite: Boolean,
    val score: Double? = null,
    val name: String? = null,
    val followers: Int? = null,
    val following: Int? = null
)
