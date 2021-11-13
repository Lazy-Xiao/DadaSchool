package com.lzy.dadaschool.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzy.dadaschool.R
import com.lzy.dadaschool.adapter.miniKeBiaoAdapter.ViewHolder

class miniKeBiaoAdapter(a:ArrayList<String>) : RecyclerView.Adapter<miniKeBiaoAdapter.ViewHolder>() {

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



    }

    override fun getItemCount(): Int =0

}