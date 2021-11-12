package com.lzy.dadaschool.activity.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lzy.dadaschool.data.DataBean
import kotlinx.coroutines.*

/**
 *  @Description:
 *  @Author: 李钟意
 *  @Date: 2021/8/16
 */

class LoginModel @ViewModelInject constructor(
) : ViewModel() {
    private var _data = MutableLiveData<DataBean>().apply {
        value=DataBean(
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
            arrayListOf(),0
        )
    }
    val data = _data
    fun logininit(username:String,password:String){
        GlobalScope.launch (Dispatchers.Main){
            data.value= com.lzy.dadaschool.net.LoginReptile.login(username, password)
        }
    }
}