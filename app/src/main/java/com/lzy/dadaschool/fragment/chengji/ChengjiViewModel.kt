package com.lzy.dadaschool.fragment.chengji

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lzy.dadaschool.data.ChengjiBean
import com.lzy.dadaschool.data.DataBean
import com.lzy.dadaschool.net.ChengjiReptile
import com.lzy.dadaschool.net.Reptile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChengjiViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var _data = MutableLiveData<ArrayList<ChengjiBean>>().apply {
        value= arrayListOf(ChengjiBean(
            "未知",
            "未知",
            "未知",
            "未知",
            "未知"
        ))
    }
    val data = _data
    fun logininit(username:String,password:String){
        GlobalScope.launch (Dispatchers.Main) {
            data.value= ChengjiReptile.login(username, password)
        }
    }
}