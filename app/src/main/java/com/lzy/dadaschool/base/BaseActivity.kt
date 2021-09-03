package com.lzy.smartcity0717

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *  @Description:
 *  @Author: 李钟意
 *  @Date: 2021/7/17
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    var binding : T ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.bind(getview())
        setContentView(binding?.root)
        initdata()
        initevent()
    }
    abstract fun getview(): View
    abstract fun initdata()
    abstract fun initevent()



}