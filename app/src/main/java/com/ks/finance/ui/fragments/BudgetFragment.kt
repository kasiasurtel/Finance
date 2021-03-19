package com.ks.finance.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.ks.finance.R
import com.ks.finance.data.FinanceDatabase
import com.ks.finance.databinding.FragmentBudgetBinding
import com.ks.finance.ui.adapters.*
import com.ks.finance.ui.viewmodels.BudgetViewModel
import com.ks.finance.ui.viewmodels.BudgetViewModelFactory

class BudgetFragment : Fragment() {

    private lateinit var viewModel: BudgetViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding: FragmentBudgetBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_budget, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = FinanceDatabase.getInstance(application).budgetDao
        val viewModelFactory = BudgetViewModelFactory(dataSource)
        viewModel = ViewModelProvider(
            this, viewModelFactory).get(BudgetViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val transactionsAdapter = TransactionsAdapter(TransactionListener { transactionId ->
            //click listener
        })

        binding.recyclerView.adapter = transactionsAdapter

        viewModel.transactions.observe(viewLifecycleOwner, {
            it?.let { transactionsAdapter.submitList(viewModel.sortTransactions()) }
        })


        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.selectAccount(position)
                transactionsAdapter.submitList(viewModel.sortTransactions())
                super.onPageSelected(position)
            }
        })

        val adapter = AccountsPagerAdapter(AccountsPagerListener {
            var currentPosition: Int = viewModel.getSelectedAccountPosition()

            val builder = AlertDialog.Builder(context)
            builder.setTitle("select account")
            builder.setSingleChoiceItems(
                viewModel.getAccountsNames(),
                currentPosition
            ) { _, position ->
                currentPosition = position
            }
            builder.setPositiveButton("Ok") { _, _ ->
                viewModel.selectAccount(currentPosition)
            }
            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            builder.create()
            builder.show()
        })

        binding.viewPager.adapter = adapter

        viewModel.accounts.observe(viewLifecycleOwner, {
            it?.let { adapter.submitList(it) }
        })

        viewModel.selectedAccount.observe(viewLifecycleOwner, {
            binding.viewPager.setCurrentItem(viewModel.getSelectedAccountPosition(), true)
        })

        binding.buttonAddTransaction.setOnClickListener{
            viewModel.addTransaction()
        }

        return binding.root
    }
}