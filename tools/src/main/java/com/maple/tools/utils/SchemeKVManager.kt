package com.maple.tools.utils

import android.content.Context

/**
 * 用户缓存数据管理者
 *
 * @author : shaoshuai27
 * @date ：2019/12/27
 */
class SchemeKVManager(context: Context) : SPUtils(context, "scheme_kv_file") {


    private val KEY_CUSTOM_SCHEME = ""
    fun addCustomScheme(name: String, scheme: String) {
        put(KEY_CUSTOM_SCHEME + name, scheme)
    }

    fun getCustomScheme(name: String, defValue: String?): String? {
        return getString(KEY_CUSTOM_SCHEME + name, defValue)
    }

    fun removeCustomScheme(name: String) {
        remove(KEY_CUSTOM_SCHEME + name)
    }

}