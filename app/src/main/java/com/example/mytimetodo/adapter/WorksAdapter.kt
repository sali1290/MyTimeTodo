package com.example.mytimetodo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Work
import com.example.mytimetodo.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class WorksAdapter(private val data: List<Work>) :
    RecyclerView.Adapter<WorksAdapter.DailyRoutineViewHolder>() {

    class DailyRoutineViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val workItem: CardView = view.findViewById(R.id.work_item)
        val title: TextView = view.findViewById(R.id.tv_title)
        val body: TextView = view.findViewById(R.id.tv_body)
        val timeSwitch: SwitchCompat = view.findViewById(R.id.tv_time)
        val icEdit: ImageView = view.findViewById(R.id.img_edit)
        val icDelete: ImageView = view.findViewById(R.id.img_delete)
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
                timeSwitch.text = ""
                timeSwitch.visibility = View.GONE
            } else {
                timeSwitch.text =
                    SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(data[position].time!!)

                timeSwitch.setOnCheckedChangeListener { _, isChecked ->
                    onSwitchCheckedChangeListener?.onChecked(
                        position,
                        data[position],
                        isChecked,
                        data[position].time!!
                    )
                }
            }


            icEdit.setOnClickListener {
                if (onEditIconClickListener != null) {
                    onEditIconClickListener!!.onClick(position, data[position])
                }
            }

            icDelete.setOnClickListener {
                if (onDeleteIconClickListener != null) {
                    onDeleteIconClickListener!!.onClick(position, data[position])
                }
            }

        }
    }

    override fun getItemCount() = data.size

    //Listener for icon edit
    private var onEditIconClickListener: OnEditIconClickListener? = null
    fun setOnEditClickListener(
        onEditIconClickListener: OnEditIconClickListener,
    ) {
        this.onEditIconClickListener = onEditIconClickListener
    }

    interface OnEditIconClickListener {
        fun onClick(position: Int, work: Work)
    }

    //Listener for icon delete
    private var onDeleteIconClickListener: OnDeleteIconClickListener? = null

    interface OnDeleteIconClickListener {
        fun onClick(position: Int, work: Work)
    }

    fun setOnDeleteClickListener(
        onDeleteIconClickListener: OnDeleteIconClickListener
    ) {
        this.onDeleteIconClickListener = onDeleteIconClickListener
    }


    private var onSwitchCheckedChangeListener: OnSwitchCheckedChangeListener? = null

    interface OnSwitchCheckedChangeListener {
        fun onChecked(position: Int, work: Work, isChecked: Boolean, date: Date)
    }

    fun setOnSwitchCheckedChangeListener(onSwitchCheckedChangeListener: OnSwitchCheckedChangeListener) {
        this.onSwitchCheckedChangeListener = onSwitchCheckedChangeListener
    }

}