package com.ks.finance.ui.adapters
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.ks.finance.data.Account
//import com.ks.finance.databinding.ListItemAccountBinding
//import com.ks.finance.databinding.ListItemAccountExpandedBinding
//
//class AccountsAdapter(private val clickListener: AccountListener) : ListAdapter<Account, RecyclerView.ViewHolder>(
//    AccountDiffCallback()
//) {
//
//    inner class ViewHolder(private val binding: ListItemAccountBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(account: Account, clickListener: AccountListener) {
//            binding.account = account
//            binding.clickListener = clickListener
//            binding.executePendingBindings()
//        }
//    }
//
//    inner class ExpandedViewHolder(private val binding: ListItemAccountExpandedBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(account: Account, clickListener: AccountListener) {
//            binding.account = account
//            binding.clickListener = clickListener
//            binding.executePendingBindings()
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//
//        return when (viewType) {
//            0 -> ViewHolder(ListItemAccountBinding.inflate(inflater, parent, false))
//            1 -> ExpandedViewHolder(ListItemAccountExpandedBinding.inflate(inflater, parent, false))
//            else -> ViewHolder(ListItemAccountBinding.inflate(inflater, parent, false))
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val item = getItem(position)
//
//        when(item.isExpanded) {
//            false -> (holder as ViewHolder).bind(item, clickListener)
//            true -> (holder as ExpandedViewHolder).bind(item, clickListener)
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int =
//        if(getItem(position).isExpanded) 1 else 0
//}
//
//class AccountDiffCallback : DiffUtil.ItemCallback<Account>() {
//    override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
//        return oldItem == newItem
//    }
//}
//
//class AccountListener(val clickListener: (accountId: Long) -> Unit) {   //TODO: move it to listeners package (?)
//    fun onClick(account: Account) {
//        clickListener(account.id)
//    }
//}