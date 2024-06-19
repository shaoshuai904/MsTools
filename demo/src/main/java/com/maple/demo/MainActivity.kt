package com.maple.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.maple.demo.databinding.ActivityMainBinding
import com.maple.tools.base.BaseActivity

/**
 * 工具集主页面
 */
class MainActivity : BaseActivity() {
    private val adapter by lazy {
        ToolsAdapter(mContext).apply {
            setOnItemClickListener { item, _ ->
                startActivity(Intent(this@MainActivity, item.clsName))
            }
            setOnItemLongClickListener { _, position ->
                val record = getItem(position)
                Toast.makeText(mContext, "long click ${record.title}", Toast.LENGTH_SHORT).show()
                true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI(binding)
    }

    private fun initUI(binding: ActivityMainBinding) {
        with(binding) {
            with(topBar) {
                ivBack.visibility = View.GONE
                tvTitle.text = "开发工具集"
            }
            rvData.adapter = adapter
        }
        adapter.refreshData(Constant.toolData)
    }

}