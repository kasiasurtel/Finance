package com.ks.finance.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ks.finance.R
import com.ks.finance.data.Currency
import com.ks.finance.data.FinanceDatabase
import com.ks.finance.databinding.FragmentAccountEditBinding
import com.ks.finance.ui.viewmodels.AccountEditViewModel
import com.ks.finance.ui.viewmodels.AccountEditViewModelFactory

class AccountEditFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentAccountEditBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_account_edit, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = AccountEditFragmentArgs.fromBundle(requireArguments())
        val dataSource = FinanceDatabase.getInstance(application).accountsDao
        val viewModelFactory = AccountEditViewModelFactory(dataSource, arguments.accountId)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(AccountEditViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.currencySpinner.adapter = ArrayAdapter(requireContext(),
            R.layout.support_simple_spinner_dropdown_item, Currency.values())
        viewModel.account.value?.currency?.ordinal?.let { binding.currencySpinner.setSelection(it) }

        viewModel.account.observe(viewLifecycleOwner, {
            if(it == null) this.findNavController().navigate(
                AccountEditFragmentDirections.actionAccountEditFragmentToNavAccounts()
            )
        })

        return binding.root
    }
}