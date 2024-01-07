package com.yusufarisoy.n11case.ui.listing

sealed interface ListingEvent {

    data object NavigateToSearch : ListingEvent

    data class NavigateToDetail(val id: Int, val username: String) : ListingEvent
}
