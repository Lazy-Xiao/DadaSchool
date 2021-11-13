package com.lzy.dadaschool.fragment.notice

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lzy.dadaschool.R
import com.lzy.dadaschool.activity.main.MainModel
import com.lzy.dadaschool.databinding.MeFragmentBinding
import com.lzy.dadaschool.databinding.NoticeFragmentBinding
import com.lzy.dadaschool.fragment.me.MeViewModel
import com.lzy.smartcity0717.BaseFragment
import com.zhuangfei.timetable.listener.OnSlideBuildAdapter




class NoticeFragment : BaseFragment<NoticeFragmentBinding>() {

    companion object {
        fun newInstance() = NoticeFragment()
    }
    private lateinit var data: MainModel

    val viewModel: NoticeViewModel by viewModels()

    override fun getviews(): View = NoticeFragmentBinding.inflate(layoutInflater).root

    override fun initdata() {
        binding = NoticeFragmentBinding.inflate(layoutInflater)
        binding!!.noticevm=viewModel
        data = ViewModelProvider(requireActivity()).get(MainModel::class.java)
    }
    override fun initevent() {
        val times = arrayOf(
            "8:00", "9:00", "10:10", "11:00",
            "15:00", "16:00", "17:00", "18:00",
            "19:30", "20:30", "21:30", "22:30"
        )
        val slideAdapter = OnSlideBuildAdapter()
        slideAdapter.times = times
        binding?.timetableView?.callback(slideAdapter)

        binding?.timetableView?.data(data.data.value?.kebiao)
            ?.curWeek(1)
            ?.showView()
        binding?.timetableView?.maxSlideItem(10)?.updateSlideView();
        binding?.timetableView?.cornerAll(15)
            ?.marTop(8)
            ?.marLeft(8)
            ?.updateView();
    }

}