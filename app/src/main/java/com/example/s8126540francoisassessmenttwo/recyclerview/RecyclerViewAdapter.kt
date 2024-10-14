package com.example.s8126540francoisassessmenttwo.recyclerview

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.s8126540francoisassessmenttwo.R
import com.example.s8126540francoisassessmenttwo.data.ItemData

class RecyclerViewAdapter(private var data: ItemData, private val navigationFunction: (ItemData) -> Unit) : RecyclerView.Adapter<ItemDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDataViewHolder {
        val view = LayoutInflater.from(parent.context) .inflate(R.layout.item_layout, parent, false)
        return ItemDataViewHolder(view, navigationFunction = navigationFunction)
    }

    override fun onBindViewHolder(viewHolder: ItemDataViewHolder, position: Int) {
        // Bind the specific Entity from the single ItemData instance
        viewHolder.bind(data, position)
    }

    override fun getItemCount() = data.entities.size

    fun setData(newData: ItemData) {
        data = newData
        notifyDataSetChanged()
    }
}