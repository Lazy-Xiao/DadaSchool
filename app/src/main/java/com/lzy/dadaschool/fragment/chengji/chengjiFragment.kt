package com.lzy.dadaschool.fragment.chengji

import androidx.lifecycle.ViewModelProvider
import android.view.View
import android.widget.ExpandableListView
import androidx.fragment.app.viewModels
import com.lzy.dadaschool.activity.main.MainModel
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
    private lateinit var data: MainModel

    private  val viewModel: kaoshiViewModel by viewModels()

    override fun getviews(): View = ChengjiFragmentBinding.inflate(layoutInflater).root

    override fun initdata() {
        data = ViewModelProvider(requireActivity()).get(MainModel::class.java)
    }

    override fun initevent() {
        var groupList: List<String> = ArrayList()
        var _groupList: ArrayList<String> = ArrayList()
        for (i in data.data.value!!.chengji){
            _groupList.add(i.xueqi)
        }
        groupList=_groupList.stream().distinct().collect(Collectors.toList())
        val childList: ArrayList<List<ChengjiBean>> = ArrayList()
        for (i in groupList){
                childList.add(data.data.value!!.chengji.stream().filter { u -> u.xueqi == i}.collect(Collectors.toList()))
        }
       val chengjiListAdapter= chengjiListAdapter(groupList,childList)
        binding!!.chengjilist.setAdapter(chengjiListAdapter)

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