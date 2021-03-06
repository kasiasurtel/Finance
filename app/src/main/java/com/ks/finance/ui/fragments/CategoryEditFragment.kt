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
import com.ks.finance.databinding.FragmentCategoryEditBinding
import com.ks.finance.ui.viewmodels.AccountEditViewModel
import com.ks.finance.ui.viewmodels.AccountEditViewModelFactory
import com.ks.finance.ui.viewmodels.CategoryEditViewModel
import com.ks.finance.ui.viewmodels.CategoryEditViewModelFactory

class CategoryEditFragment : Fragment() {

    lateinit var binding: FragmentCategoryEditBinding
    lateinit var viewModel: CategoryEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_category_edit, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = CategoryEditFragmentArgs.fromBundle(requireArguments())
        val dataSource = FinanceDatabase.getInstance(application).categoriesDao
        val viewModelFactory = CategoryEditViewModelFactory(dataSource, arguments.categoryId)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CategoryEditViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.leave.observe(viewLifecycleOwner, {
            if (it) {
                    this.findNavController().navigate(CategoryEditFragmentDirections
                        .actionCategoryEditFragmentToNavCategories())
                viewModel.doneNavigating()
            }
        })


        binding.buttonDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(getString(R.string.account_delete))
            builder.setMessage("Are you sure you want to delete this category?")
            builder.setNegativeButton("No") { dialog, _ -> dialog.cancel()}
            builder.setPositiveButton("Yes") { _, _ -> viewModel.onDelete() }
            builder.create()
            builder.show()
        }

        binding.buttonSave.setOnClickListener {
            if(dataIsValid()) {
                val name = binding.nameEdit.text.toString()
                viewModel.onSave(name)
            } else
                Toast.makeText(context, "Fill all fields!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun dataIsValid() = !binding.nameEdit.text.isNullOrBlank()
}