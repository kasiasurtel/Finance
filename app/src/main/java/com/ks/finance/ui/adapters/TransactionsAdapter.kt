package com.ks.finance.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ks.finance.data.Account
import com.ks.finance.data.Transaction
import com.ks.finance.databinding.ListItemAccountBinding
import com.ks.finance.databinding.ListItemTransactionBinding

class TransactionsAdapter(private val clickListener: TransactionListener) : ListAdapter<Transaction,
        TransactionsAdapter.ViewHolder>(TransactionDiffCallback()) {

    inner class ViewHolder(private val binding: ListItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(account: Transaction, clickListener: TransactionListener) {
            binding.transaction = account
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListItemTransactionBinding.inflate(inflater, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }
}

class TransactionListener(val clickListener: (transactionId: Long) -> Unit) {   //TODO: move it to listeners package (?)
    fun onClick(transaction: Transaction) {
        clickListener(transaction.id)
    }
}