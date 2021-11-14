package com.lzy.dadaschool.fragment.home

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.ViewModelProvider
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzy.dadaschool.R
import com.lzy.dadaschool.activity.main.MainModel
import com.lzy.dadaschool.adapter.miniKeBiaoAdapter
import com.lzy.dadaschool.data.miniKechengBean
import com.lzy.dadaschool.databinding.HomeFragmentBinding
import com.lzy.smartcity0717.BaseFragment
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment<HomeFragmentBinding>() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var data: MainModel
    val viewModel: HomeViewModel by viewModels()
    val minikebiaolist = ArrayList<miniKechengBean>()
    val minikebiaolist2 = ArrayList<miniKechengBean>()

    override fun getviews(): View = HomeFragmentBinding.inflate(layoutInflater).root

    override fun initdata() {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        binding!!.homevm = viewModel
        data = ViewModelProvider(requireActivity()).get(MainModel::class.java)
    }

    override fun initevent() {
        /*data.data.observe(requireActivity(), Observer {
        })*/
        binding!!.homecardview.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_chengjiFragment)
        }
        binding!!.homecardview2.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_kaoshiFragment)
        }

        val week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1
        val df = SimpleDateFormat("HH.mm") //设置日期格式
//        Toast.makeText(activity,minikebiaolist[0].kecheng,Toast.LENGTH_SHORT).show()
        data.data.observe(requireActivity(), Observer {
            if (it.gan == 1) {
                for (i in data.data.value!!.kebiao.stream().filter { u -> u.day == 1 }
                    .collect(Collectors.toList())) {
                    minikebiaolist.add(miniKechengBean(i.name, timesx(i.start)))
                }
                val layoutManager = LinearLayoutManager(context)
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                binding!!.miniKeBiao.setLayoutManager(layoutManager)
                minikebiaolist.reverse()
                val a=df.format(Date()).toDouble()
               for (i in minikebiaolist.stream().filter { u -> u.kechengtime.toInt() > a }
                   .collect(Collectors.toList())) {
                   minikebiaolist2.add(i)
               }
                if (minikebiaolist2.size<=0){
                    minikebiaolist2.add(miniKechengBean("今日已无课",0.00))
                }
              minikebiaolist2.sortBy { it.kechengtime }
                binding!!.miniKeBiao.adapter = miniKeBiaoAdapter(minikebiaolist2)
//                Toast.makeText(context, df.format(Date()), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun timesx(i: Int): Double {
        return when (i) {
            1 -> 8.30
            2 -> 9.20
            3 -> 10.20
            4 -> 11.10
            5 -> 14.40
            6 -> 15.30
            7 -> 16.20
            8 -> 17.10
            9 -> 19.30
            10 -> 20.20
            else -> 0.00
        }

    }
}