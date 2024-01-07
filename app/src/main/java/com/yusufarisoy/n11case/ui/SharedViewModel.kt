package com.yusufarisoy.n11case.ui

import com.yusufarisoy.n11case.core.BaseViewModel
import com.yusufarisoy.n11case.core.secureLaunch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : BaseViewModel() {

    private val _favoriteUpdateFlow: MutableStateFlow<Int?> = MutableStateFlow(null)
    val favoriteUpdateFlow: StateFlow<Int?>
        get() = _favoriteUpdateFlow.asStateFlow()

    fun updateFavorite(userId: Int?) {
        secureLaunch {
            _favoriteUpdateFlow.emit(userId)
        }
    }
}
