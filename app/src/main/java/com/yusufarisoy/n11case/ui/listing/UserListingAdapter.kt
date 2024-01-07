package com.yusufarisoy.n11case.ui.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yusufarisoy.n11case.R
import com.yusufarisoy.n11case.databinding.RecyclerItemUserListingBinding
import com.yusufarisoy.n11case.domain.model.UserUiModel

interface UserListingAdapterCallbacks {

    fun onUserClicked(id: Int, username: String)

    fun onFavoriteClicked(id: Int)
}

private sealed interface UserChangePayload {

    data class Favorite(val favorite: Boolean) : UserChangePayload
}

class UserListingAdapter(
    private val callbacks: UserListingAdapterCallbacks
) : RecyclerView.Adapter<UserListingAdapter.UserViewHolder>() {

    private val differ = AsyncListDiffer(
        this,
        diffCallback
    )

    fun setData(items: List<UserUiModel>) {
        differ.submitList(items)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = differ.currentList[position]
        holder.bind(user, callbacks::onUserClicked, callbacks::onFavoriteClicked)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int, payloads: List<Any>) {
        when (val payload = payloads.lastOrNull()) {
            is UserChangePayload.Favorite -> holder.bindFavoriteButton(payload.favorite)
            else -> onBindViewHolder(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent)
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

            override fun getChangePayload(oldItem: UserUiModel, newItem: UserUiModel): Any? {
                return if (oldItem.favorite != newItem.favorite) {
                    UserChangePayload.Favorite(newItem.favorite)
                } else {
                    super.getChangePayload(oldItem, newItem)
                }
            }
        }
    }

    class UserViewHolder(
        private val binding: RecyclerItemUserListingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            user: UserUiModel,
            onUserClicked: (Int, String) -> Unit,
            onFavoriteClicked: (Int) -> Unit
        ) {
            Glide.with(binding.root).load(user.avatar).into(binding.imageAvatar)
            binding.textUsername.text = user.login
            bindFavoriteButton(user.favorite)

            binding.itemLayout.setOnClickListener {
                onUserClicked(user.id, user.login)
            }
            binding.buttonFavorite.setOnClickListener {
                onFavoriteClicked(user.id)
            }
        }

        fun bindFavoriteButton(favorite: Boolean) {
            val icon = if (favorite) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
            binding.buttonFavorite.setImageResource(icon)
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RecyclerItemUserListingBinding.inflate(inflater, parent, false)

                return UserViewHolder(binding)
            }
        }
    }
}
