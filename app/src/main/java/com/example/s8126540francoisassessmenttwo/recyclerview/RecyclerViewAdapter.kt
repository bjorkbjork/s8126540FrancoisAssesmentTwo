package com.example.s8126540francoisassessmenttwo.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.s8126540francoisassessmenttwo.R
import com.example.s8126540francoisassessmenttwo.data.ItemData

class RecyclerViewAdapter(private val dataList: MutableList<ItemData> = mutableListOf(), private val navigationFunction: (ItemData) -> Unit) : RecyclerView.Adapter<ResponseItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseItemViewHolder {
        val view = LayoutInflater.from(parent.context) .inflate(R.layout.item_layout, parent, false)
        return ResponseItemViewHolder(view, navigationFunction = navigationFunction)
    }

    override fun onBindViewHolder(viewHolder: ResponseItemViewHolder, position: Int) {
        viewHolder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size

    fun setData(newDataList: List<ItemData>) {
        dataList.clear()
        dataList.addAll(newDataList)
        notifyDataSetChanged()
    }
}