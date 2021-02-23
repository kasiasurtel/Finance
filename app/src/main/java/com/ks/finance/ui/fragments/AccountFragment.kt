package com.ks.finance.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ks.finance.R
import com.ks.finance.data.FinanceDatabase
import com.ks.finance.databinding.FragmentAccountBinding
import com.ks.finance.ui.viewmodels.AccountViewModel
import com.ks.finance.ui.viewmodels.AccountViewModelFactory

class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentAccountBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_account, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = AccountFragmentArgs.fromBundle(requireArguments())
        val dataSource = FinanceDatabase.getInstance(application).accountsDao
        val viewModelFactory = AccountViewModelFactory(dataSource, arguments.accountId)
        val accountViewModel = ViewModelProvider(
                this, viewModelFactory).get(AccountViewModel::class.java)
        binding.accountViewModel = accountViewModel
        binding.lifecycleOwner = this

        binding.buttonEdit.setOnClickListener {
            this.findNavController().navigate(
                AccountFragmentDirections
                    .actionAccountFragmentToAccountEditFragment(arguments.accountId.toString())
            )
        }

        return binding.root
    }
}