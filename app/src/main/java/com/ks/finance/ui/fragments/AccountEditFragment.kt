package com.ks.finance.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ks.finance.R
import com.ks.finance.data.Account
import com.ks.finance.data.Currency
import com.ks.finance.data.FinanceDatabase
import com.ks.finance.databinding.FragmentAccountEditBinding
import com.ks.finance.ui.viewmodels.AccountEditViewModel
import com.ks.finance.ui.viewmodels.AccountEditViewModelFactory

class AccountEditFragment : Fragment() {

    lateinit var binding: FragmentAccountEditBinding
    lateinit var viewModel: AccountEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_account_edit, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = AccountEditFragmentArgs.fromBundle(requireArguments())
        val dataSource = FinanceDatabase.getInstance(application).accountsDao
        val viewModelFactory = AccountEditViewModelFactory(dataSource, arguments.accountId)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AccountEditViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.currencySpinner.adapter = ArrayAdapter(requireContext(),
            R.layout.support_simple_spinner_dropdown_item, Currency.values())
        viewModel.account.value?.currency?.ordinal?.let { binding.currencySpinner.setSelection(it) }

        viewModel.leave.observe(viewLifecycleOwner, {
            if (it) {
                if (arguments.accountId == null || viewModel.account.value == null) {
                    this.findNavController().navigate(
                        AccountEditFragmentDirections.actionAccountEditFragmentToNavAccounts()
                    )
                } else {
                    this.findNavController().navigate(
                        AccountEditFragmentDirections.actionAccountEditFragmentToAccountFragment(arguments.accountId)
                    )
                }

                viewModel.doneNavigating()
            }
        })

        binding.buttonDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(getString(R.string.account_delete))
            builder.setMessage("Are you sure you want to delete this account?")
            builder.setNegativeButton("No") { dialog, _ -> dialog.cancel()}
            builder.setPositiveButton("Yes") { _, _ -> viewModel.onDelete() }
            builder.create()
            builder.show()
        }

        binding.buttonSave.setOnClickListener {
            if(dataIsValid()) {
                val name = binding.nameEdit.text.toString()
                val currency = binding.currencySpinner.selectedItem as Currency
                val balance = binding.balanceEdit.text.toString().toDouble()
                viewModel.onSave(name, currency, balance)
            } else
                Toast.makeText(context, "Fill all fields!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun dataIsValid() = !binding.nameEdit.text.isNullOrBlank()
            && !binding.balanceEdit.text.isNullOrBlank()
}