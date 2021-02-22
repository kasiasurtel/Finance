package com.ks.finance.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ks.finance.R
import com.ks.finance.data.Category
import java.util.*

class CategoriesAdapter (private val dataSet: List<Category>)
    : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView

        init {
            name = view.findViewById(R.id.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_account, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = dataSet[position].name.toUpperCase(Locale.ROOT)
    }

    override fun getItemCount() = dataSet.size
}