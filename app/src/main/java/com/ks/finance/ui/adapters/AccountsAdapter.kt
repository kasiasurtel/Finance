package com.ks.finance.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ks.finance.data.Account
import com.ks.finance.databinding.ListItemAccountBinding

class AccountsAdapter(private val clickListener: AccountListener) : ListAdapter<Account, AccountsAdapter.ViewHolder>(
    AccountDiffCallback()
) {

    inner class ViewHolder(private val binding: ListItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(account: Account, clickListener: AccountListener) {
            binding.account = account
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListItemAccountBinding.inflate(inflater, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class AccountDiffCallback : DiffUtil.ItemCallback<Account>() {
    override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem == newItem
    }
}

class AccountListener(val clickListener: (accountId: Long) -> Unit) {   //TODO: move it to listeners package (?)
    fun onClick(account: Account) {
        clickListener(account.id)
    }
}