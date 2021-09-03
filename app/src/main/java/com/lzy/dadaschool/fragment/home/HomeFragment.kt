package com.lzy.dadaschool.fragment.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.lzy.dadaschool.R
import com.lzy.dadaschool.activity.main.MainModel
import com.lzy.dadaschool.databinding.ActivityLoginBinding.inflate
import com.lzy.dadaschool.databinding.HomeFragmentBinding
import com.lzy.dadaschool.fragment.me.MeViewModel
import com.lzy.smartcity0717.BaseFragment

class HomeFragment : BaseFragment<HomeFragmentBinding>() {
    companion object {
        fun newInstance() = HomeFragment()
    }
    private lateinit var data: MainModel
    val viewModel: HomeViewModel by viewModels()
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
    }

}