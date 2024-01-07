package com.yusufarisoy.n11case.ui.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusufarisoy.n11case.R
import com.yusufarisoy.n11case.core.BaseFragment
import com.yusufarisoy.n11case.databinding.FragmentListingBinding
import com.yusufarisoy.n11case.ui.SharedViewModel
import com.yusufarisoy.n11case.ui.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListingFragment : BaseFragment() {

    private val viewModel: ListingViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentListingBinding
    private lateinit var adapter: UserListingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerAdapter()
        initViews()
        observeStateFlow()
        observeEventFlow()
        observeErrorFlow()
        observeSharedUpdateFlow()
    }

    private fun initRecyclerAdapter() {
        adapter = UserListingAdapter(userListingCallbacks())
        binding.recyclerUserListing.layoutManager = LinearLayoutManager(context)
        binding.recyclerUserListing.adapter = adapter
    }

    private fun initViews() {
        binding.searchView.setOnClickListener {
            viewModel.onSearchClicked()
        }
    }

    private fun observeStateFlow() {
        viewModel.stateFlow.collectIn { users ->
            adapter.setData(users)
        }
    }

    private fun observeEventFlow() {
        viewModel.eventFlow.collectIn { event ->
            when (event) {
                is ListingEvent.NavigateToDetail -> navigateToDetail(event.id, event.username)
                is ListingEvent.NavigateToSearch -> findNavController().navigate(R.id.searchFragment)
            }
        }
    }

    private fun observeErrorFlow() {
        viewModel.errorFlow.collectIn {message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    // SharedFlow can't be collected in ListingFragment because lifeCycleState.STARTED used and
    // emitted value can't be sent when there are no observers.
    // StateFlow needs to set null to prevent redundant updates
    private fun observeSharedUpdateFlow() {
        sharedViewModel.favoriteUpdateFlow.collectIn { userId ->
            userId?.let {
                viewModel.updateFavorite(it)
                sharedViewModel.updateFavorite(userId = null)
            }
        }
    }

    private fun navigateToDetail(userId: Int, username: String) {
        val bundle = Bundle()
        bundle.putInt(DetailViewModel.KEY_USER_ID, userId)
        bundle.putString(DetailViewModel.KEY_USERNAME, username)
        findNavController().navigate(R.id.detailFragment, bundle)
    }

    private fun userListingCallbacks() = object : UserListingAdapterCallbacks {
        override fun onUserClicked(id: Int, username: String) {
            viewModel.onUserClicked(id, username)
        }

        override fun onFavoriteClicked(id: Int) {
            viewModel.onFavoriteClicked(id)
        }
    }
}
