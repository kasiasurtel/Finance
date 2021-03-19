package com.ks.finance.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ks.finance.data.Account
import com.ks.finance.data.Category
import com.ks.finance.databinding.PagerAccountItemBinding

class AccountsPagerAdapter(private val clickListener: AccountsPagerListener)
    : ListAdapter<Account, AccountsPagerAdapter.ViewHolder>(AccountDiffCallback()) {

    inner class ViewHolder(private val binding: PagerAccountItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(account: Account, clickListener: AccountsPagerListener) {
            binding.account = account
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(PagerAccountItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class AccountsPagerListener(val clickListener: () -> Unit) {   //TODO: move it to listeners package (?)
    fun onClick() { //category: Category
        clickListener()
    }
}