package com.ks.finance.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ks.finance.R
import com.ks.finance.data.FinanceDatabase
import com.ks.finance.databinding.FragmentAccountsBinding
import com.ks.finance.ui.adapters.AccountListener
import com.ks.finance.ui.adapters.AccountsAdapter
import com.ks.finance.ui.viewmodels.AccountsViewModel
import com.ks.finance.ui.viewmodels.AccountsViewModelFactory

class AccountsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(this.activity).application
        val dataSource = FinanceDatabase.getInstance(application).accountsDao
        val viewModelFactory = AccountsViewModelFactory(dataSource, application)
        val accountsViewModel = ViewModelProvider(this, viewModelFactory)
            .get(AccountsViewModel::class.java)

        val binding: FragmentAccountsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_accounts, container, false)

        binding.lifecycleOwner = this
        binding.accountsViewModel = accountsViewModel

        val adapter = AccountsAdapter(AccountListener { accountId ->
            this.findNavController().navigate(
                AccountsFragmentDirections.actionNavAccountsToAccountFragment(accountId.toString())
            )
        })

        binding.recyclerView.adapter = adapter
        accountsViewModel.accounts.observe(viewLifecycleOwner, {
            it?.let { adapter.submitList(it) }
        })

        binding.addAccountButton.setOnClickListener {
            this.findNavController().navigate(
                AccountsFragmentDirections.actionNavAccountsToAccountEditFragment(null)
            )
        }

        return binding.root
    }
}