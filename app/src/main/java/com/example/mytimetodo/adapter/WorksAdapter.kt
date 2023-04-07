package com.example.mytimetodo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Work
import com.example.mytimetodo.R
import java.text.DateFormat
import java.text.SimpleDateFormat

class WorksAdapter(private val data: List<Work>) :
    RecyclerView.Adapter<WorksAdapter.DailyRoutineViewHolder>() {

    class DailyRoutineViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val workItem: CardView = view.findViewById(R.id.work_item)
        val title: TextView = view.findViewById(R.id.tv_title)
        val body: TextView = view.findViewById(R.id.tv_body)
        val time: TextView = view.findViewById(R.id.tv_time)
        val icEdit: ImageView = view.findViewById(R.id.img_edit)
        val icDelete: ImageView = view.findViewById(R.id.img_delete)
        val icIsDone: ImageView = view.findViewById(R.id.img_isDone)
        val imgCheck: ImageView = view.findViewById(R.id.img_check_done)
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
                time.text =
                    SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(data[position].time!!)
            }

            if (data[position].isDone) {
                icIsDone.setImageResource(R.drawable.ic_undo)
                imgCheck.visibility = View.VISIBLE
            } else {
                icIsDone.setImageResource(R.drawable.ic_check)
                imgCheck.visibility = View.GONE
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

            icIsDone.setOnClickListener {
                if (onDoneIconClickListener != null) {
                    onDoneIconClickListener!!.onClick(position, data[position])
                }
                if (data[position].isDone) {
                    icIsDone.setImageResource(R.drawable.ic_undo)
                    imgCheck.visibility = View.VISIBLE
                } else {
                    icIsDone.setImageResource(R.drawable.ic_check)
                    imgCheck.visibility = View.GONE
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

    private var onDoneIconClickListener: OnDoneIconClickListener? = null

    fun setOnDoneIconClickListener(onDoneIconClickListener: OnDoneIconClickListener) {
        this.onDoneIconClickListener = onDoneIconClickListener
    }

    interface OnDoneIconClickListener {
        fun onClick(position: Int, work: Work)
    }


}