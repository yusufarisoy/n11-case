package com.yusufarisoy.n11case.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yusufarisoy.n11case.R
import com.yusufarisoy.n11case.databinding.RecyclerItemUserSearchBinding
import com.yusufarisoy.n11case.domain.model.UserUiModel

class UserSearchAdapter : RecyclerView.Adapter<UserSearchAdapter.UserSearchViewHolder>() {

    private val differ = AsyncListDiffer(
        this,
        diffCallback
    )

    fun setData(items: List<UserUiModel>) {
        differ.submitList(items)
    }

    override fun onBindViewHolder(holder: UserSearchViewHolder, position: Int) {
        val user = differ.currentList[position]
        holder.bind(user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSearchViewHolder {
        return UserSearchViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<UserUiModel>() {
            override fun areItemsTheSame(
                oldItem: UserUiModel,
                newItem: UserUiModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UserUiModel,
                newItem: UserUiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class UserSearchViewHolder(
        private val binding: RecyclerItemUserSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserUiModel) {
            Glide.with(binding.root).load(user.avatar).into(binding.imageAvatar)
            binding.textUsername.text = user.login
            user.score?.let { score ->
                binding.textScore.text = binding.root.resources.getString(R.string.search_score, score)
            }
        }

        companion object {
            fun from(parent: ViewGroup): UserSearchViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RecyclerItemUserSearchBinding.inflate(inflater, parent, false)

                return UserSearchViewHolder(binding)
            }
        }
    }
}
