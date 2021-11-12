package com.lzy.dadaschool.activity.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lzy.dadaschool.data.DataBean
import com.lzy.dadaschool.net.KeBiaoReptile
import com.lzy.dadaschool.net.Reptile
import com.zhuangfei.timetable.model.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *  @Description:
 *  @Author: 李钟意
 *  @Date: 2021/8/18
 */
class MainModel @ViewModelInject constructor(
):ViewModel() {
    private var _data = MutableLiveData<DataBean>().apply {
        value= DataBean(
            "未知",
            "未知",
            "未知",
            "未知",
            "未知",
            "未知",
            "未知",
            "未知",
            "未知",
            "未知",
            "未知",
            arrayListOf(),
            0
        )
    }
    val data = _data
    fun logininit(username:String,password:String){
        GlobalScope.launch (Dispatchers.Main) {
            data.value= Reptile.login(username, password)
        }

    }



}