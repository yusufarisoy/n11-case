package com.yusufarisoy.n11case.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.yusufarisoy.n11case.R
import com.yusufarisoy.n11case.core.BaseFragment
import com.yusufarisoy.n11case.databinding.FragmentDetailBinding
import com.yusufarisoy.n11case.ui.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment() {

    private val viewModel: DetailViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStateFlow()
        observeEventFlow()
        initViews()
    }

    private fun observeStateFlow() {
        viewModel.stateFlow.collectIn { userUiModel ->
            userUiModel?.let {
                binding.user = it

                val icon = if (it.favorite) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
                binding.buttonFavorite.setImageResource(icon)
            }
        }
    }

    private fun observeEventFlow() {
        viewModel.eventFlow.collectIn { event ->
            when (event) {
                is DetailEvent.UpdateFavorite -> sharedViewModel.updateFavorite(event.userId)
            }
        }
    }

    private fun initViews() {
        binding.buttonFavorite.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
    }
}
