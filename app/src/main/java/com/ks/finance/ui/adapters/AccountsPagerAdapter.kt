package com.ks.finance.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.ks.finance.R
import com.ks.finance.data.Account
import com.ks.finance.data.Category
import com.ks.finance.databinding.ListItemAccountBinding
import com.ks.finance.databinding.ListItemCategoryBinding
import com.ks.finance.databinding.PagerAccountItemBinding

class AccountsPagerAdapter :
    ListAdapter<Account, AccountsPagerAdapter.ViewHolder>(AccountDiffCallback()) {

    inner class ViewHolder(private val binding: PagerAccountItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(account: Account) {
            binding.account = account
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(PagerAccountItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}