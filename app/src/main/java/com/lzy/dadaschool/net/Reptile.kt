package com.lzy.dadaschool.net
import com.lzy.dadaschool.data.ChengjiBean
import com.lzy.dadaschool.data.DataBean
import com.lzy.dadaschool.data.KaoshiTimeBean
import com.lzy.dadaschool.net.store.DefaultCookieStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.net.URLEncoder
import javax.inject.Inject
import javax.security.auth.callback.Callback
    @Suppress("BlockingMethodInNonBlockingContext")
    object Reptile {
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

        suspend fun login(username: String, password: String): DataBean {
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
                            return@withContext DataBean(
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
                                arrayListOf(),-1
                            )
                        } else {

                            var xinxi: ArrayList<String> = ArrayList()
                            var chengji: ArrayList<ChengjiBean> = ArrayList()
                            var kaoshiTime: ArrayList<KaoshiTimeBean> = ArrayList()
                            var response1 = response
                            response1 = Jsoup.newSession()
                                .url("http://jwmis.dzvtc.edu.cn/jiaoshi/xslm/info/bjiben")
                                .timeout(ReptileManager.timeout)
                                .headers(header)
                                .cookies(ReptileManager.cookieStore.getCookies())
                                .method(Connection.Method.GET)
                                .data(data)
                                .execute()
                            //            println(response.body())
                            response1.bodyAsBytes().let {
                                val document = Jsoup.parse(String(it, charset("gb2312")))
                                /*
                                * 学生信息
                                * */
                                for (i in 0..11) {
                                    xinxi.add(document.select(".g_body")[i].text())
//                    print("${document.select(".g_body")[i].text()}     ")
                                }

                            }
                            return@withContext DataBean(
                                xinxi[0],
                                xinxi[1],
                                xinxi[2],
                                xinxi[3],
                                xinxi[4],
                                xinxi[5],
                                xinxi[6],
                                xinxi[7],
                                xinxi[8],
                                xinxi[9],
                                xinxi[10],
                                getKeBiao(
                                    response,
                                    header,
                                    data, "http://jwmis.dzvtc.edu.cn/jiaoshi/xslm/gongxuan/kai"
                                ),1
                            )
                        }
                    }
//            ReptileManager.cookieStore.putAll(response.cookies())


                } catch (e: Exception) {
                    return@withContext DataBean(
                        e.toString(),
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
                        arrayListOf(),-1
                    )
                }

            }
        }
    }