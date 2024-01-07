package com.yusufarisoy.n11case.ui.detail

import androidx.lifecycle.SavedStateHandle
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
class DetailViewModel @Inject constructor(
    private val repository: GithubRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _stateFlow: MutableStateFlow<UserUiModel?> = MutableStateFlow(null)
    val stateFlow: StateFlow<UserUiModel?>
        get() = _stateFlow.asStateFlow()

    private val _eventFlow: MutableSharedFlow<DetailEvent> = MutableSharedFlow()
    val eventFlow: SharedFlow<DetailEvent>
        get() = _eventFlow.asSharedFlow()

    init {
        fetchUser()
    }

    private fun fetchUser() {
        val id: Int? = savedStateHandle[KEY_USER_ID]
        val username: String? = savedStateHandle[KEY_USERNAME]

        if (id != null && !username.isNullOrEmpty()) {
            secureLaunch {
                val response = withContext(Dispatchers.IO) {
                    repository.getUserDetail(id, username)
                }

                _stateFlow.emit(response)
            }
        }
    }

    fun onFavoriteClicked() {
        secureLaunch {
            val currentState = _stateFlow.value
            if (currentState != null) {
                val updatedState = currentState.copy(favorite = !currentState.favorite)
                repository.updateLocalUser(updatedState)
                _eventFlow.emit(DetailEvent.UpdateFavorite(updatedState.id)) // Send update to SharedViewModel
                _stateFlow.emit(updatedState)
            }
        }
    }

    companion object {
        const val KEY_USER_ID = "userId"
        const val KEY_USERNAME = "username"
    }
}
