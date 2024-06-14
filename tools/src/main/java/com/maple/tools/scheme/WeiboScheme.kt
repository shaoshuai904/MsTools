package com.maple.tools.scheme

import com.maple.tools.base.ItemInfoBean
import com.maple.tools.base.KeyValueBean
import com.maple.tools.base.NoDataBean
import com.maple.tools.base.TitleBean

object WeiboScheme {

    val SCHEME_LIST = mutableListOf<ItemInfoBean>(
        TitleBean("自定义 Scheme"),
        NoDataBean("暂无"),
        TitleBean("微博 Scheme"),
        KeyValueBean("首页", "sinaweibo://gotohome"),
        KeyValueBean("超话", "sinaweibo://gotosg"),
        KeyValueBean("广场", "sinaweibo://discover"),
        KeyValueBean("消息箱", "sinaweibo://message"),
        KeyValueBean("我的", "sinaweibo://myprofile"),
        KeyValueBean("精选", "sinaweibo://gotofeatured")
    )

}