package com.project.every.view.student.fragment.schedule.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.every.DTO.student.schedule.SchedulesList
import com.project.every.R
import com.project.every.view.student.activity.schedule.ScheduleContentActivity
import kotlinx.android.synthetic.main.schedule_item.view.*
import kotlin.collections.ArrayList

class ScheduleAdapter(val mContext : Context, val items : ArrayList<SchedulesList>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater : LayoutInflater = LayoutInflater.from(parent.context)
        var itemView = inflater.inflate(R.layout.schedule_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item : SchedulesList = items.get(position)

        val listener = View.OnClickListener {
            val intent = Intent(mContext, ScheduleContentActivity::class.java)
            intent.putExtra("idx", item.idx)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext.startActivity(intent)
        }

        holder.apply {
            bind(listener, item)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var view : View = itemView

        @SuppressLint("SetTextI18n")
        fun bind(listener : View.OnClickListener, item : SchedulesList){

            // title
            itemView.title_textView.isSelected = true
            itemView.title_textView.text = item.title

            // date
            itemView.date_textView.text = "${item.start_date.substring(0, 4)}년 ${item.start_date.substring(5, 7)}월 ${item.start_date.substring(8, 10)}일 ~ " +
                                          "${item.end_date.substring(0, 4)}년 ${item.end_date.substring(5, 7)}월 ${item.end_date.subSequence(8, 10)}일"
            view.setOnClickListener(listener)
        }
    }
}