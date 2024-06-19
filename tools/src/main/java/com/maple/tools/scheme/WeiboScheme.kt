package com.maple.tools.scheme

import android.content.Context
import com.maple.tools.base.ItemInfoBean
import com.maple.tools.base.KeyValueBean
import com.maple.tools.base.NoDataBean
import com.maple.tools.base.TitleBean
import com.maple.tools.utils.SchemeKVManager

object WeiboScheme {

    private val WB_SCHEME_LIST = mutableListOf<ItemInfoBean>(
        TitleBean("微博 Scheme"),
        KeyValueBean("首页", "sinaweibo://gotohome"),
        KeyValueBean("超话", "sinaweibo://gotosg"),
        KeyValueBean("视频", "sinaweibo://gotovideo"),
        KeyValueBean("精选", "sinaweibo://gotofeatured"),
        KeyValueBean("发现", "sinaweibo://discover"),
        KeyValueBean("消息", "sinaweibo://message"),
        KeyValueBean("我的", "sinaweibo://myprofile")
    )

    fun getSchemeData(context: Context): List<ItemInfoBean> {
        val schemeList = mutableListOf<ItemInfoBean>()
        schemeList.add(TitleBean("自定义 Scheme"))
        // 添加缓存数据
        SchemeKVManager(context).all.forEach {
            schemeList.add(KeyValueBean(it.key, it.value as String))
        }
        schemeList.add(NoDataBean("点击添加"))
        schemeList.addAll(WB_SCHEME_LIST)
        return schemeList
    }


}