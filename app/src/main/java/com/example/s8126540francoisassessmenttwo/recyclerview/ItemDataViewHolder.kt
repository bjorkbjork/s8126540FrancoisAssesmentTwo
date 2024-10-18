package com.example.s8126540francoisassessmenttwo.recyclerview

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s8126540francoisassessmenttwo.R
import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.example.s8126540francoisassessmenttwo.data.Entity

class ItemDataViewHolder(private val view: View, private val navigationFunction: (Entity) -> Unit) : RecyclerView.ViewHolder(view) {


    private val courseCode: TextView = view.findViewById(R.id.itemCode)
    private val courseName: TextView = view.findViewById(R.id.itemName)
    private val instructor: TextView = view.findViewById(R.id.itemPerson)
    private val credits: TextView = view.findViewById(R.id.intText)
    private val button: Button = view.findViewById(R.id.navigationButton)

    fun bind(itemData: ItemData, position: Int) {
        val entity = itemData.entities[position]
        courseCode.text = entity.stringKeyOne
        courseName.text = entity.stringKeyTwo
        instructor.text = if (entity.stringKeyFour.isNotEmpty()) entity.stringKeyThree else ""
        credits.text = if(entity.intKey != 0) view.context.getString(R.string.credits, entity.intTitle, entity.intKey) else entity.stringKeyFour // Convert credits (Int) to String


        button.text = view.context.getString(R.string.details)
        
        button.setOnClickListener {
            navigationFunction(entity)
        }


    }
}