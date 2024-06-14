package com.maple.tools

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maple.tools.databinding.ActivityToolsMainBinding

class ToolsMainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityToolsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI(binding)
    }

    private fun initUI(binding: ActivityToolsMainBinding) {
//        binding.rvData.adapter = adapter
//        adapter.refreshData(toolData)
    }

}