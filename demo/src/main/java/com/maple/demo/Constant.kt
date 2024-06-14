package com.maple.demo

import com.maple.tools.R
import com.maple.tools.scheme.SchemeToolActivity
import com.maple.tools.ToolsMainActivity

/**
 * 常量
 */
object Constant {

    val toolData = mutableListOf<ToolBean>(
        ToolBean(R.drawable.ic_menu_camera, "Scheme", SchemeToolActivity::class.java),
        ToolBean(R.drawable.ic_menu_camera, "标题2", ToolsMainActivity::class.java),
        ToolBean(R.drawable.ic_menu_camera, "标题3", ToolsMainActivity::class.java),
        ToolBean(R.drawable.ic_menu_gallery, "标题3", ToolsMainActivity::class.java),
        ToolBean(R.drawable.ic_menu_slideshow, "标题3", ToolsMainActivity::class.java),
        ToolBean(R.drawable.ic_menu_camera, "标题3", ToolsMainActivity::class.java),
        ToolBean(R.drawable.ic_menu_gallery, "标题3", ToolsMainActivity::class.java),
        ToolBean(R.drawable.ic_menu_slideshow, "标题3", ToolsMainActivity::class.java),
        ToolBean(R.drawable.ic_menu_camera, "标题4", ToolsMainActivity::class.java)
    )

}