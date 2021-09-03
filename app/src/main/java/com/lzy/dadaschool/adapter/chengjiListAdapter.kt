package com.lzy.dadaschool.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.core.app.NotificationCompat.getGroup

import android.widget.TextView

import android.R

import android.view.LayoutInflater
import com.lzy.dadaschool.data.ChengjiBean
import kotlinx.android.synthetic.main.chengji_list_item.view.*
import kotlinx.android.synthetic.main.chengji_list_item.view.text1
import kotlinx.android.synthetic.main.chengji_list_item2.view.*


/**
 *  @Description:
 *  @Author: 李钟意
 *  @Date: 2021/8/31
 */
class chengjiListAdapter(
    mGroupList: List<String>,mChildList: List<List<ChengjiBean>>
) : BaseExpandableListAdapter(
) {
    var mGroupList: List<String>? = mGroupList
    var mChildList: List<List<ChengjiBean>>? = mChildList

  /*  fun chengjiListAdapter() {
       this.mGroupList=mGroupList
        this.mChildList=mChildList
    }*/

    override fun getGroupCount(): Int =mGroupList!!.size

    override fun getChildrenCount(groupPosition: Int): Int = mChildList!![groupPosition].size

    override fun getGroup(groupPosition: Int): Any =mGroupList!!.get(groupPosition)

    override fun getChild(groupPosition: Int, childPosition: Int): Any =groupPosition

    override fun getGroupId(groupPosition: Int): Long =groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long =(groupPosition + childPosition).toLong()

    override fun hasStableIds(): Boolean =true

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
       val view= LayoutInflater.from(parent!!.getContext()).inflate(com.lzy.dadaschool.R.layout.chengji_list_item, parent,false)
        (view.text1 as TextView).text = getGroup(groupPosition) as String
        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view= LayoutInflater.from(parent!!.getContext()).inflate(com.lzy.dadaschool.R.layout.chengji_list_item2, parent,false)
        (view.text1 as TextView).text = mChildList?.get(groupPosition)?.get(childPosition)!!.kecheng
        (view.text2 as TextView).text = mChildList?.get(groupPosition)?.get(childPosition)!!.fenshu+" 分"
        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean =true
}