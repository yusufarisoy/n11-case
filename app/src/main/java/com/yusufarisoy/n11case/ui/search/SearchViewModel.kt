package com.yusufarisoy.n11case.ui.search

import androidx.lifecycle.viewModelScope
import com.yusufarisoy.n11case.core.BaseViewModel
import com.yusufarisoy.n11case.core.secureLaunch
import com.yusufarisoy.n11case.data.repository.GithubRepository
import com.yusufarisoy.n11case.domain.model.SearchUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: GithubRepository
) : BaseViewModel() {

    private val _stateFlow: MutableStateFlow<SearchUiModel> = MutableStateFlow(SearchUiModel())
    val stateFlow: StateFlow<SearchUiModel>
        get() = _stateFlow.asStateFlow()

    private val _queryFlow: MutableStateFlow<String> = MutableStateFlow(EMPTY_QUERY)
    private val queryFlow: StateFlow<String>
        get() = _queryFlow.asStateFlow()

    init {
        observeQuery()
    }

    fun onQueryChanged(query: String?) {
        secureLaunch {
            _queryFlow.emit(query ?: EMPTY_QUERY)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeQuery() {
        queryFlow
            .debounce(DEBOUNCE)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.length > 2) {
                    search(query)
                } else {
                    clearPage()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun search(query: String) {
        secureLaunch {
            val response = withContext(Dispatchers.IO) {
                repository.searchUser(query)
            }
            if (response.totalCount == 0) {
                onError(Throwable(message = "No result found"))
            } else {
                _stateFlow.emit(response)
            }
        }
    }

    private fun clearPage() {
        secureLaunch {
            _stateFlow.emit(SearchUiModel(totalCount = 0))
        }
    }

    companion object {
        private const val DEBOUNCE = 300L
        private const val EMPTY_QUERY = ""
    }
}
