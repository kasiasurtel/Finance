package com.ks.finance.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ks.finance.R
import com.ks.finance.ui.viewmodels.StatisticsViewModel

class StatisticsFragment : Fragment() {

    private lateinit var statisticsViewModel: StatisticsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        statisticsViewModel =
                ViewModelProvider(this).get(StatisticsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_categories, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        statisticsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}