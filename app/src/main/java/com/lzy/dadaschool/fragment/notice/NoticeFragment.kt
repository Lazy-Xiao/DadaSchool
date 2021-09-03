package com.lzy.dadaschool.fragment.notice

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lzy.dadaschool.R
import com.lzy.dadaschool.databinding.MeFragmentBinding
import com.lzy.dadaschool.databinding.NoticeFragmentBinding
import com.lzy.dadaschool.fragment.me.MeViewModel
import com.lzy.smartcity0717.BaseFragment

class NoticeFragment : BaseFragment<NoticeFragmentBinding>() {

    companion object {
        fun newInstance() = NoticeFragment()
    }

    val viewModel: NoticeViewModel by viewModels()

    override fun getviews(): View = NoticeFragmentBinding.inflate(layoutInflater).root

    override fun initdata() {
        binding = NoticeFragmentBinding.inflate(layoutInflater)
        binding!!.noticevm=viewModel

    }

    override fun initevent() {

    }

}