package com.example.s8126540francoisassessmenttwo.recyclerview

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.s8126540francoisassessmenttwo.R
import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.example.s8126540francoisassessmenttwo.data.Entity

class ItemDataViewHolder(view: View, private val navigationFunction: (ItemData) -> Unit) : RecyclerView.ViewHolder(view) {


    private val courseCode: TextView = view.findViewById(R.id.itemNametext)
    private val courseName: TextView = view.findViewById(R.id.itemIdText)
    private val instructor: TextView = view.findViewById(R.id.detailsText)
    private val credits: TextView = view.findViewById(R.id.intText)
    private val button: Button = view.findViewById(R.id.navigationButton)

    fun bind(itemData: ItemData, position: Int) {
        // Get the specific Entity object at the current position
        val entity = itemData.entities[position]

        // Set the text for each TextView from the entity
        courseCode.text = entity.key1
        courseName.text = entity.key2
        instructor.text = entity.key3
        credits.text = entity.key4.toString() // Convert credits (Int) to String

    }
}