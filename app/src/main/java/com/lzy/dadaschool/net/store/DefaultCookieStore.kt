package com.lzy.dadaschool.net.store


class DefaultCookieStore:  CookieStore{
    val map = mutableMapOf<String, String>()

    override fun put(name: String, value: String) {
        map.put(name, value)
    }

    override fun putAll(cookies: Map<String, String>) {
        map.putAll(cookies)
    }

    override fun get(name: String): String? {
        return map[name]
    }

    override fun remove(name: String) {
         map.remove(name)
    }

    override fun removeAll() {
        map.clear()
    }

    override fun getCookies(): Map<String, String> {
        return map
    }
}