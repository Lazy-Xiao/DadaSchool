package com.lzy.dadaschool.data

/**
 *  @Description:
 *  @Author: 李钟意
 *  @Date: 2021/8/17
 */
data class DataBean (
    var nianji:String="",
    var banji:String="",
    var xuehao:String="",
    var kaoshenghao:String="",
    var name:String="",
    var sex:String="",
    var sfznum:String="",
    var minzu:String="",
    var zhuanye:String="",
    var xibu:String="",
    var zhuanyedaima:String="",
    var chengji:ArrayList<ChengjiBean>,
    var kaoshiTime:ArrayList<KaoshiTimeBean>,
    var gan:Int=0
){
}