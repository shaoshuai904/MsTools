package com.maple.tools.scheme

import com.maple.tools.base.ItemInfoBean
import com.maple.tools.base.KeyValueBean
import com.maple.tools.base.NoDataBean
import com.maple.tools.base.TitleBean

object WeiboScheme {

    val SCHEME_LIST = mutableListOf<ItemInfoBean>(
        TitleBean("微博 Scheme"),
        KeyValueBean("首页", "sinaweibo://gotohome"),
        KeyValueBean("超话", "sinaweibo://gotosg"),
        KeyValueBean("视频", "sinaweibo://gotovideo"),
        KeyValueBean("精选", "sinaweibo://gotofeatured"),
        KeyValueBean("发现", "sinaweibo://discover"),
        KeyValueBean("消息", "sinaweibo://message"),
        KeyValueBean("我的", "sinaweibo://myprofile"),
        KeyValueBean("工程模式", "sinaweibo://projectmode"),
        KeyValueBean("AB", "sinaweibo://greyconfig")
    )

}