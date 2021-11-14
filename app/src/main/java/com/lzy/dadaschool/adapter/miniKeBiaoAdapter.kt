package com.lzy.dadaschool.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzy.dadaschool.R
import com.lzy.dadaschool.data.miniKechengBean
import com.zhuangfei.timetable.model.Schedule

class miniKeBiaoAdapter(val minikebiao: ArrayList<miniKechengBean>) : RecyclerView.Adapter<miniKeBiaoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val kechengName: TextView=itemView.findViewById(R.id.kecheng)
        val kechengTime: TextView=itemView.findViewById(R.id.kechengtime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO("Not yet implemented")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kebiao_item, null)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("Not yet implemented")
        holder.kechengTime.text=minikebiao[position].kechengtime.toString()+"0"
        holder.kechengName.text=minikebiao[position].kecheng
    }

    override fun getItemCount(): Int =minikebiao.size

}