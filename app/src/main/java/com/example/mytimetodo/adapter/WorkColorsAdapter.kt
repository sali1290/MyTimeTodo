package com.example.mytimetodo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mytimetodo.R

class WorkColorsAdapter(private val context: Context, private val colorList: List<Int>) :
    RecyclerView.Adapter<WorkColorsAdapter.ColorViewHolder>() {

    class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val colorItem: CardView = view.findViewById(R.id.card_color)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ColorViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_color, viewGroup, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ColorViewHolder, position: Int) {

        viewHolder.apply {
            colorItem.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    colorList[position]
                )
            )
            colorItem.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, colorList[position])
                }
            }
        }

    }

    override fun getItemCount() = colorList.size

    private var onClickListener: OnClickListener? = null
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, colorId: Int)
    }

}

fun WorkColorsAdapter.changeBackgroundColor(context: Context, view: View) {
    this.setOnClickListener(object :
        WorkColorsAdapter.OnClickListener {
        override fun onClick(position: Int, colorId: Int) {
            view.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    colorId
                )
            )
        }
    })
}
