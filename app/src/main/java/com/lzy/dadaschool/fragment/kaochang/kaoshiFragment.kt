package com.lzy.dadaschool.fragment.kaochang


import android.view.View
import android.widget.ExpandableListView
import androidx.fragment.app.viewModels
import com.lzy.dadaschool.adapter.kaoshiListAdapter
import com.lzy.dadaschool.data.KaoshiTimeBean
import com.lzy.dadaschool.databinding.KaoshiFragmentBinding
import com.lzy.dadaschool.fragment.chengji.kaoshiViewModel
import com.lzy.smartcity0717.BaseFragment
import java.util.stream.Collectors

class kaoshiFragment : BaseFragment<KaoshiFragmentBinding>() {

    companion object {
        fun newInstance() = kaoshiFragment()
    }
    val data: kaoshiViewModel by viewModels()



    override fun getviews(): View = KaoshiFragmentBinding.inflate(layoutInflater).root

    override fun initdata() {
    }

    override fun initevent() {
        var groupList: List<String> = ArrayList()
        var _groupList: ArrayList<String> = ArrayList()
        for (i in data.data.value!!){
            _groupList.add(i.xueqi)
        }
        groupList=_groupList.stream().distinct().collect(Collectors.toList())
        val childList: ArrayList<List<KaoshiTimeBean>> = ArrayList()
        for (i in groupList){
                childList.add(data.data.value!!.stream().filter { u -> u.xueqi == i}.collect(Collectors.toList()))
        }
       val kaoshiListAdapter= kaoshiListAdapter(groupList,childList)
        binding!!.chengjilist.setAdapter(kaoshiListAdapter)

        binding!!.chengjilist.setOnGroupClickListener(object :ExpandableListView.OnGroupClickListener{
            override fun onGroupClick(
                parent: ExpandableListView?,
                v: View?,
                groupPosition: Int,
                id: Long
            ): Boolean =false
        })
        binding!!.chengjilist.setOnChildClickListener(object :ExpandableListView.OnChildClickListener{
            override fun onChildClick(
                parent: ExpandableListView?,
                v: View?,
                groupPosition: Int,
                childPosition: Int,
                id: Long
            ): Boolean =false

        })
    }

}