package com.lzy.dadaschool.fragment.kaochang


import android.content.Intent
import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kaopiz.kprogresshud.KProgressHUD
import com.lzy.dadaschool.activity.login.LoginActivity
import com.lzy.dadaschool.adapter.chengjiListAdapter
import com.lzy.dadaschool.adapter.kaoshiListAdapter
import com.lzy.dadaschool.data.ChengjiBean
import com.lzy.dadaschool.data.KaoshiTimeBean
import com.lzy.dadaschool.databinding.KaoshiFragmentBinding
import com.lzy.dadaschool.fragment.chengji.kaoshiViewModel
import com.lzy.smartcity0717.BaseFragment
import com.wang.avi.AVLoadingIndicatorView
import java.util.stream.Collectors

class kaoshiFragment : BaseFragment<KaoshiFragmentBinding>() {
    lateinit var hud: KProgressHUD
    companion object {
        fun newInstance() = kaoshiFragment()
    }
    val data: kaoshiViewModel by viewModels()



    override fun getviews(): View = KaoshiFragmentBinding.inflate(layoutInflater).root

    override fun initdata() {
        hud = KProgressHUD.create(context)
        try {
            indata()

        } catch (e: Exception) {
            activity?.intent?.setClass(requireActivity(), LoginActivity::class.java)
            startActivity(activity?.intent)
            activity?.finish()
        }

    }

    override fun initevent() {

    }
    private fun indata() {
        val sharedPreference = activity?.getSharedPreferences("data", AppCompatActivity.MODE_PRIVATE)
        data.logininit(
            sharedPreference?.getString("username", "").toString(),
            sharedPreference?.getString("password", "").toString()
        )
        data.data.observe(this, Observer {
            Toast.makeText(context, it[0].gan.toString(), Toast.LENGTH_SHORT).show()
            when (it[0].gan) {
                -1 -> {
                    val intent = Intent()
                    intent.setClass(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                    hud.dismiss()
//                    finish()
                }
                0 -> {
                    val avLoadingIndicatorView = AVLoadingIndicatorView(context)
                    hud.setDetailsLabel("加载数据中")
                        .setCustomView(avLoadingIndicatorView)
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show()
                }
                1 -> {
                    hud.dismiss()
                    showView()
                }
            }
        })
    }
    private fun showView() {
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
        binding!!.chengjilist.setOnGroupClickListener { _, _, _, _ -> false }
        binding!!.chengjilist.setOnChildClickListener { _, _, _, _, _ -> false }
    }
}