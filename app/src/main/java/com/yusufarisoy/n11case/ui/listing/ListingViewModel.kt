package com.yusufarisoy.n11case.ui.listing

import com.yusufarisoy.n11case.core.BaseViewModel
import com.yusufarisoy.n11case.core.secureLaunch
import com.yusufarisoy.n11case.data.repository.GithubRepository
import com.yusufarisoy.n11case.domain.model.UserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(
    private val repository: GithubRepository
) : BaseViewModel() {

    private val _stateFlow: MutableStateFlow<List<UserUiModel>> = MutableStateFlow(emptyList())
    val stateFlow: StateFlow<List<UserUiModel>>
        get() = _stateFlow.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ListingEvent>()
    val eventFlow: SharedFlow<ListingEvent>
        get() = _eventFlow.asSharedFlow()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        secureLaunch {
            val response = withContext(Dispatchers.IO) {
                repository.getUsers()
            }
            _stateFlow.emit(response)
        }
    }

    fun onSearchClicked() {
        secureLaunch {
            _eventFlow.emit(ListingEvent.NavigateToSearch)
        }
    }

    fun onUserClicked(id: Int, username: String) {
        secureLaunch {
            _eventFlow.emit(ListingEvent.NavigateToDetail(id, username))
        }
    }

    fun onFavoriteClicked(id: Int) {
        updateFavorite(id, updateLocal = true)
    }

    fun updateFavorite(userId: Int, updateLocal: Boolean = false) {
        secureLaunch {
            val currentState = _stateFlow.value
            if (currentState.isNotEmpty()) {
                val updatedState = currentState.map { user ->
                    if (user.id == userId) {
                        val updatedUser = user.copy(favorite = !user.favorite)
                        if (updateLocal) { // Don't update if sent from SharedViewModel
                            repository.updateLocalUser(updatedUser)
                        }
                        updatedUser
                    } else {
                        user
                    }
                }

                _stateFlow.emit(updatedState)
            }
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        // Handle if needed
    }
}
