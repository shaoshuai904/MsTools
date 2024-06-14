package com.maple.tools.scheme

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.maple.tools.base.ItemType
import com.maple.tools.base.KeyValueAdapter
import com.maple.tools.base.KeyValueBean
import com.maple.tools.databinding.ActivitySchemeToolBinding

/**
 * Scheme 工具集合
 */
class SchemeToolActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySchemeToolBinding
    private val adapter by lazy {
        KeyValueAdapter(this@SchemeToolActivity).apply {
            setOnItemClickListener { item, _ ->
                if (item.getType() == ItemType.KeyValue) {
                    val kvItem = item as KeyValueBean
                    binding.etText.setText(kvItem.value)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchemeToolBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.run {
            btSend.setOnClickListener {
                val inputText = etText.text.toString()
                sendScheme(inputText)
            }
            rvData.adapter = adapter
        }
        adapter.refreshData(WeiboScheme.SCHEME_LIST)
    }

    private fun sendScheme(scheme: String?) {
        Log.e("ms_log", "send $scheme")
        if (scheme.isNullOrEmpty())
            return
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(scheme))
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        applicationContext.startActivity(intent)
    }

}