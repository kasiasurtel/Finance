package com.ks.finance.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ks.finance.R
import com.ks.finance.data.FinanceDatabase
import com.ks.finance.databinding.FragmentBudgetBinding
import com.ks.finance.ui.viewmodels.AccountViewModel
import com.ks.finance.ui.viewmodels.AccountViewModelFactory
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
        val dataSource = FinanceDatabase.getInstance(application).accountsDao
        val viewModelFactory = BudgetViewModelFactory(dataSource)
        viewModel = ViewModelProvider(
            this, viewModelFactory).get(BudgetViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.account_delete))

        viewModel.accounts.observe(viewLifecycleOwner, {
            builder.setSingleChoiceItems(
                viewModel.getAccountsNames(),
                viewModel.getSelectedAccountPosition()
            ) { _, position ->
                viewModel.selectAccount(position)
            }
        })

        binding.accountButton.setOnClickListener {
            builder.setPositiveButton("Ok") { _, _ ->
                viewModel.setSelectedAccount()
            }
            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            builder.create()
            builder.show()
        }

        return binding.root
    }
}