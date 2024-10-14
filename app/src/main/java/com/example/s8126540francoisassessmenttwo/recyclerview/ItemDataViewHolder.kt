package com.example.s8126540francoisassessmenttwo.recyclerview

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.s8126540francoisassessmenttwo.R
import com.example.s8126540francoisassessmenttwo.data.ItemData

class ItemDataViewHolder(view: View, private val navigationFunction: (ItemData) -> Unit) : RecyclerView.ViewHolder(view) {


    private val name: TextView = view.findViewById(R.id.itemNametext)
    private val id: TextView = view.findViewById(R.id.itemIdText)
    private val details: TextView = view.findViewById(R.id.detailsText)
    private val button: Button = view.findViewById(R.id.navigationButton)

    fun bind(item: ItemData) {
//        name.text = item.objectName
//        id.text = "Id: " + item.id
//        val showDetails = if (item.dataSection.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
//        details.visibility = showDetails
//        button.visibility = showDetails
//
//        if (showDetails == View.VISIBLE) {
//            button.setOnClickListener {
//                navigationFunction(item)
//            }
//        }
    }
}