package com.maple.demo

import com.maple.tools.R
import com.maple.tools.ToolsMainActivity
import com.maple.tools.device.DeviceInfoActivity
import com.maple.tools.scheme.SchemeToolActivity

/**
 * 常量
 */
object Constant {

    val toolData = mutableListOf<ToolBean>(
        ToolBean(R.drawable.svg_link, "Scheme", SchemeToolActivity::class.java),
        ToolBean(R.drawable.svg_device_dev, "设备信息", DeviceInfoActivity::class.java),
        ToolBean(R.drawable.svg_device_dev, "标题3", ToolsMainActivity::class.java)
    )

}