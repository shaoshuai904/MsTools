package com.maple.demo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.maple.demo.databinding.ActivityMainBinding

/**
 * 工具集主页面
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI(binding)
    }

    private fun initUI(binding: ActivityMainBinding) {
        val adapter = ToolsAdapter(baseContext).apply {
            setOnItemClickListener { item, _ ->
                startActivity(Intent(this@MainActivity, item.clsName))
            }
            setOnItemLongClickListener { _, position ->
                val record = getItem(position)
                Toast.makeText(this@MainActivity, "long click ${record.title}", Toast.LENGTH_SHORT).show()
                true
            }
        }
        binding.rvData.adapter = adapter
        adapter.refreshData(Constant.toolData)
    }

}