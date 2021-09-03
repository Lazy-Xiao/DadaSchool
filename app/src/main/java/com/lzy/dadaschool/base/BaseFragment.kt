package com.lzy.smartcity0717

import android.R
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 *  @Description:
 *  @Author: 李钟意
 *  @Date: 2021/7/18
 */
abstract class BaseFragment<T:ViewDataBinding> : Fragment() {
    var binding: T ?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= getviews()?.let { DataBindingUtil.bind(it) }
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.explode)
        enterTransition = inflater.inflateTransition(R.transition.explode)
        initdata()
        initevent()
        return binding?.root
    }

    abstract fun getviews(): View
    abstract fun initdata()
    abstract fun initevent()


}