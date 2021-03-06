package com.ks.finance.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ks.finance.R
import com.ks.finance.data.FinanceDatabase
import com.ks.finance.databinding.FragmentAccountsBinding
import com.ks.finance.databinding.FragmentCategoriesBinding
import com.ks.finance.ui.adapters.AccountListener
import com.ks.finance.ui.adapters.AccountsAdapter
import com.ks.finance.ui.adapters.CategoriesAdapter
import com.ks.finance.ui.adapters.CategoryListener
import com.ks.finance.ui.viewmodels.AccountsViewModel
import com.ks.finance.ui.viewmodels.AccountsViewModelFactory
import com.ks.finance.ui.viewmodels.CategoriesViewModel
import com.ks.finance.ui.viewmodels.CategoriesViewModelFactory

class CategoriesFragment : Fragment() {

    private lateinit var viewModel: CategoriesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(this.activity).application
        val dataSource = FinanceDatabase.getInstance(application).categoriesDao
        val viewModelFactory = CategoriesViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CategoriesViewModel::class.java)

        val binding: FragmentCategoriesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_categories, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = CategoriesAdapter(CategoryListener { categoryId ->
            this.findNavController().navigate(
                CategoriesFragmentDirections.actionNavCategoriesToCategoryEditFragment(categoryId.toString())
            )
        })

        binding.recyclerView.adapter = adapter
        viewModel.categories.observe(viewLifecycleOwner, {
            it?.let { adapter.submitList(it) }
        })

        binding.addCategoryButton.setOnClickListener {
            this.findNavController().navigate(
                CategoriesFragmentDirections.actionNavCategoriesToCategoryEditFragment(null)
            )
        }

        return binding.root
    }
}