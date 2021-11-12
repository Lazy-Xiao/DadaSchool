package com.lzy.dadaschool.fragment.chengji

import android.view.View
import android.widget.ExpandableListView
import androidx.fragment.app.viewModels
import com.lzy.dadaschool.adapter.chengjiListAdapter
import com.lzy.dadaschool.data.ChengjiBean
import com.lzy.dadaschool.databinding.ChengjiFragmentBinding
import com.lzy.dadaschool.fragment.kaochang.kaoshiFragment
import com.lzy.smartcity0717.BaseFragment
import java.util.stream.Collectors

class chengjiFragment : BaseFragment<ChengjiFragmentBinding>() {

    companion object {
        fun newInstance() = kaoshiFragment()
    }
    val data: ChengjiViewModel by viewModels()

    override fun getviews(): View = ChengjiFragmentBinding.inflate(layoutInflater).root

    override fun initdata() {
    }

    override fun initevent() {
        var groupList: List<String> = ArrayList()
        var _groupList: ArrayList<String> = ArrayList()
        for (i in data.data.value!!){
            _groupList.add(i.xueqi)
        }
        groupList=_groupList.stream().distinct().collect(Collectors.toList())
        val childList: ArrayList<List<ChengjiBean>> = ArrayList()
        for (i in groupList){
                childList.add(data.data.value!!.stream().filter { u -> u.xueqi == i}.collect(Collectors.toList()))
        }
       val chengjiListAdapter= chengjiListAdapter(groupList,childList)
        binding!!.chengjilist.setAdapter(chengjiListAdapter)

        binding!!.chengjilist.setOnGroupClickListener { _, _, _, _ -> false }
        binding!!.chengjilist.setOnChildClickListener { _, _, _, _, _ -> false }
    }

}