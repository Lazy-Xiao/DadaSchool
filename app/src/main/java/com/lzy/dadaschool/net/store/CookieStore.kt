package com.lzy.dadaschool.net.store

interface CookieStore {
    fun put(name: String, value: String)
    fun putAll(cookies: Map<String, String>)
    operator fun get(name: String): String?
    fun remove(name: String)
    fun removeAll()
    fun getCookies(): Map<String, String>
}