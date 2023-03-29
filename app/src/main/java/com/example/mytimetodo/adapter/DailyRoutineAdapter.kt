package com.example.mytimetodo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Work
import com.example.mytimetodo.R
import com.google.android.material.timepicker.TimeFormat
import java.text.DateFormat
import java.text.SimpleDateFormat

class DailyRoutineAdapter(private val data: List<Work>) :
    RecyclerView.Adapter<DailyRoutineAdapter.DailyRoutineViewHolder>() {

    class DailyRoutineViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val workItem: CardView = view.findViewById(R.id.work_item)
        val title: TextView = view.findViewById(R.id.tv_title)
        val body: TextView = view.findViewById(R.id.tv_body)
        val time: TextView = view.findViewById(R.id.tv_time)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DailyRoutineViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_work, viewGroup, false)
        return DailyRoutineViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: DailyRoutineViewHolder, position: Int) {
        viewHolder.apply {
            workItem.setCardBackgroundColor(
                data[position].color.toInt()
            )
            title.text = data[position].title
            body.text = data[position].body
            if (data[position].time == null) {
                time.text = ""
            } else {
                time.text = SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(data[position].time!!)
            }
        }
    }

    override fun getItemCount() = data.size


}