package com.lzy.dadaschool.net

import android.util.Log
import com.lzy.dadaschool.net.store.DefaultCookieStore
import com.sink.reptile.Kebiao
import com.zhuangfei.timetable.model.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jsoup.Connection
import org.jsoup.Jsoup

@Suppress("BlockingMethodInNonBlockingContext")
object KeBiaoReptile {
    init {
        ReptileManager.timeout = 3000
        ReptileManager.cookieStore = DefaultCookieStore()
    }

    private fun encode(plaintText: String): String {
        val sb = StringBuilder()
        for (c in plaintText) {
            sb.append(c.code + 100000)
        }
        return sb.toString()
    }

    suspend fun login(username: String, password: String): ArrayList<Schedule> {
        return withContext(Dispatchers.IO) {
            try {
                var response = Jsoup.newSession()
                    .timeout(ReptileManager.timeout)
                    .url("http://jwmis.dzvtc.edu.cn/web/web/web/index")
                    .method(Connection.Method.GET)
                    .execute()
                //保存Cookie
                ReptileManager.cookieStore.putAll(response.cookies())
                val data = mapOf(
                    "imageField.x" to "39",
                    "imageField.y" to "17",
                    "user" to username,
                    "pwd" to "",
                    "user1" to encode(username),
                    "pwd1" to encode(password)
                )
                val header = mapOf(
                    "Connection" to "keep-alive",
                    "Cache-Control" to "max-age=0",
                    "Upgrade-Insecure-Requests" to "1",
                    "Origin" to "http://jwmis.dzvtc.edu.cn",
                    "Referer" to "http://jwmis.dzvtc.edu.cn/web/web/web/index",
                )
                response = Jsoup.newSession()
                    .url("http://jwmis.dzvtc.edu.cn/jiaoshi/bangong/main/check.asp")
                    .timeout(ReptileManager.timeout)
                    .headers(header)
                    .cookies(ReptileManager.cookieStore.getCookies())
                    .method(Connection.Method.POST)
                    .data(data)
                    .execute()
                response.bodyAsBytes().let {
                    val document = Jsoup.parse(String(it, charset("gb2312")))
                    if (document.select("form").text()
                            .equals("密码不对，无法登录！") || document.select("form").text()
                            .equals("该帐号不存在，请重新输入！") || response.statusCode() != 200
                    ) {
                        println("密码错误")
                        return@withContext arrayListOf(
                            Schedule(
                                "错误",
                                "错误",
                                "错误", arrayListOf(), 0, 0, 0, 0
                            )
                        )
                    } else {

                        return@withContext getKeBiao(
                            response,
                            header,
                            data, "http://jwmis.dzvtc.edu.cn/jiaoshi/xslm/gongxuan/kai"
                        )
                    }
                }
//            ReptileManager.cookieStore.putAll(response.cookies())


            } catch (e: Exception) {
                return@withContext arrayListOf(
                    Schedule(
                        "未知",
                        "未知",
                        "未知", arrayListOf(), 0, 0, 0, 0
                    )
                )
            }

        }
    }
}

fun getKeBiao(
    response: Connection.Response,
    header: Map<String, String>,
    data: Map<String, String>,
    url: String
): ArrayList<Schedule> {
    var response1 = response
    val kebiaoList: ArrayList<Schedule> = ArrayList<Schedule>()
    response1 = Jsoup.newSession()
        .url(url)
        .timeout(ReptileManager.timeout)
        .headers(header)
        .cookies(ReptileManager.cookieStore.getCookies())
        .method(Connection.Method.GET)
        .data(data)
        .execute()
    response1.bodyAsBytes().let { it ->
        val document = Jsoup.parse(String(it, charset("gb2312")))
        /*
        * 课程目录
        * */
        for (i in document.select("tr").select("[onmouseover]").select("a")) {
//                println("http://jwmis.dzvtc.edu.cn/jiaoshi/xslm/gongxuan/"+i.attr("href"))
            if (i.text() == "查看教学班") {
                response1 = Jsoup.newSession()
                    .url("http://jwmis.dzvtc.edu.cn/jiaoshi/xslm/gongxuan/" + i.attr("href"))
                    .timeout(ReptileManager.timeout)
                    .headers(header)
                    .cookies(ReptileManager.cookieStore.getCookies())
                    .method(Connection.Method.GET)
                    .data(data)
                    .execute()
                response1.bodyAsBytes().let {
                    val document = Jsoup.parse(String(it, charset("gb2312")))
                    /*
                    * 课程
                    * */
                    val arrayList = ArrayList<Kebiao>()
                    for (i in document.select("center").select("table").select("tr")
                        .select("[onmouseover]")) {
                        /* print(
                             "科目：${i.select("td")[3].text()}\t\t\t\t\t\t老师：${
                                 i.select("td")[5].text()
                             }\t\t\t\t时间：${i.select("td")[6].text()}\n"
                         )*/
                        arrayList.add(
                            Kebiao(
                                i.select("td")[3].text(),
                                i.select("td")[5].text(),
                                i.select("td")[6].text()
                            )
                        )
                    }
                    for (i in arrayList) {
                        for (j in i.time.split(" ")) {
//            println(j+"    "+i.kecheng+"      "+i.teacher)
                            var x = 0
                            when (j.length) {
                                6 -> {
                                    kebiaoList.add(
                                        Schedule(
                                            i.kecheng,
                                            "",
                                            i.teacher,
                                            arrayListOf(1),
                                            j.substring(3, 4).toInt(),
//                                            j.substring(4, 5).toInt()
                                            2
                                            , weeknum(j.substring(0, 3)),3
                                        )
                                    )
                                    Log.i("aaaa",
                                        "科目：" +
                                                i.kecheng +
                                                j.substring(0, 3) +
                                                "的第" +
                                                j.substring(3, 4) +
                                                "和第" +
                                                j.substring(4, 5) +
                                                "节"
                                    )
                                }
                                7 -> {
//                    println(j.substring(j.length - 2, j.length-1))
                                    if (j.substring(j.length - 2, j.length - 1).equals("0")) {
                                        kebiaoList.add(
                                            Schedule(
                                                i.kecheng,
                                                "",
                                                i.teacher,
                                                arrayListOf(1),
                                                j.substring(3, 4).toInt(),
//                                                j.substring(4, 6).toInt()
                                                2
                                                , weeknum(j.substring(0, 3)),1
                                            )
                                        )
                                        Log.i("aaaa",
                                            "科目：" +
                                                    i.kecheng +
                                                    j.substring(0, 3) +
                                                    "的第" +
                                                    j.substring(3, 4) +
                                                    "和第" +
                                                    j.substring(4, 6) +
                                                    "节"
                                        )
                                    } else {
                                        kebiaoList.add(
                                            Schedule(
                                                i.kecheng,
                                                "",
                                                i.teacher,
                                                arrayListOf(1),
                                                j.substring(3, 4).toInt(),
//                                                j.substring(5, 6).toInt()
                                                3
                                                , weeknum(j.substring(0, 3)),1
                                            )
                                        )

                                        Log.i("aaaa",
                                            "科目：" +
                                                    i.kecheng +
                                                    j.substring(0, 3) +
                                                    "的第" +
                                                    j.substring(3, 4) +
                                                    "和第" +
                                                    j.substring(4, 5) +
                                                    "节" +
                                                    "和第" +
                                                    j.substring(5, 6) + "节"
                                        )
                                    }

                                }
                            }

                        }
                    }
                }
            }
        }
    }
    for (i in kebiaoList){
        Log.i("bbbb",i.day.toString()+"//"+i.start.toString()+"//"+i.step)
    }
    return kebiaoList
}

fun weeknum(string: String): Int {
    when (string) {
        "星期一" -> return 1
        "星期二" -> return 2
        "星期三" -> return 3
        "星期四" -> return 4
        "星期五" -> return 5
        "星期六" -> return 6
        else -> return 7
    }
}