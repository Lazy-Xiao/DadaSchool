package com.lzy.dadaschool.fragment.chengji

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lzy.dadaschool.data.ChengjiBean
import com.lzy.dadaschool.data.KaoshiTimeBean
import com.lzy.dadaschool.net.ChengjiReptile
import com.lzy.dadaschool.net.KaochangReptile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class kaoshiViewModel : ViewModel() {
    private var _data = MutableLiveData<ArrayList<KaoshiTimeBean>>().apply {
        value= arrayListOf(
            KaoshiTimeBean(
            "未知",
            "未知",
            "未知",
            "未知",
            "未知","未知"
        )
        )
    }
    val data = _data
    fun logininit(username:String,password:String){
        GlobalScope.launch (Dispatchers.Main) {
            data.value= KaochangReptile.login(username, password)
        }
    }
}