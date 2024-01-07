package com.yusufarisoy.n11case.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.yusufarisoy.n11case.R
import com.yusufarisoy.n11case.core.BaseFragment
import com.yusufarisoy.n11case.core.hide
import com.yusufarisoy.n11case.core.show
import com.yusufarisoy.n11case.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: UserSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerAdapter()
        initViews()
        observeStateFlow()
        observeErrorFlow()
    }

    private fun initRecyclerAdapter() {
        adapter = UserSearchAdapter()
        binding.recyclerUserSearch.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerUserSearch.adapter = adapter
    }

    private fun initViews() {
        binding.searchView.setOnQueryTextListener(
            object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.onQueryChanged(newText)

                    return false
                }
            }
        )
    }

    private fun observeStateFlow() {
        viewModel.stateFlow.collectIn { searchUiModel ->
            if (searchUiModel.totalCount >= 0) {
                binding.textSearchResult.show()
                binding.recyclerUserSearch.show()
                binding.textNoResult.hide()
                binding.textSearchResult.text = resources.getString(R.string.search_result_count, searchUiModel.totalCount)
                adapter.setData(searchUiModel.users)
            }
        }
    }

    private fun observeErrorFlow() {
        viewModel.errorFlow.collectIn { message ->
            if (!message.isNullOrEmpty()) {
                binding.textSearchResult.hide()
                binding.recyclerUserSearch.hide()
                binding.textNoResult.show()
                binding.textNoResult.text = message
            }
        }
    }
}
