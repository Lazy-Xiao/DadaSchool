package com.lzy.dadaschool.fragment.me

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lzy.dadaschool.R
import com.lzy.dadaschool.databinding.HomeFragmentBinding
import com.lzy.dadaschool.databinding.MeFragmentBinding
import com.lzy.dadaschool.fragment.home.HomeViewModel
import com.lzy.smartcity0717.BaseFragment

class MeFragment : BaseFragment<MeFragmentBinding>() {

    companion object {
        fun newInstance() = MeFragment()
    }
    val viewModel: MeViewModel by viewModels()

    override fun getviews(): View = MeFragmentBinding.inflate(layoutInflater).root

    override fun initdata() {
        binding = MeFragmentBinding.inflate(layoutInflater)
        binding!!.mevm=viewModel

    }

    override fun initevent() {

    }

}