package com.yusufarisoy.n11case.ui.detail

sealed interface DetailEvent {

    data class UpdateFavorite(val userId: Int): DetailEvent
}
