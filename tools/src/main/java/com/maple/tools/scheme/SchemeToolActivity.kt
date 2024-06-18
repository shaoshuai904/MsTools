package com.maple.tools.scheme

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.maple.tools.base.BaseActivity
import com.maple.tools.base.ItemType
import com.maple.tools.base.KeyValueAdapter
import com.maple.tools.base.KeyValueBean
import com.maple.tools.databinding.ActivitySchemeToolBinding

/**
 * Scheme 工具集合
 */
class SchemeToolActivity : BaseActivity() {
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
        with(binding) {
            with(topBar) {
                ivBack.setOnClickListener { onBackPressed() }
                tvTitle.text = "Scheme 工具"
            }
            btSend.setOnClickListener {
                val inputText = etText.text.toString()
                sendScheme(inputText)
            }
            rvData.adapter = adapter
        }
        // init data
        adapter.refreshData(WeiboScheme.SCHEME_LIST)
    }

    private fun sendScheme(scheme: String?) {
        if (scheme.isNullOrEmpty())
            return
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(scheme))
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}