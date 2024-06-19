package com.maple.tools.scheme

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import com.maple.msdialog.AlertBaseDialog
import com.maple.msdialog.AlertDialog
import com.maple.tools.base.BaseActivity
import com.maple.tools.base.ItemType
import com.maple.tools.base.KeyValueAdapter
import com.maple.tools.base.KeyValueBean
import com.maple.tools.base.NoDataBean
import com.maple.tools.databinding.ActivitySchemeToolBinding
import com.maple.tools.databinding.DialogAddSchemeBinding
import com.maple.tools.utils.SchemeKVManager

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
                } else if (item.getType() == ItemType.NoData) {
                    val kvItem = item as NoDataBean
                    if ("点击添加" == kvItem.message) {
                        showAddCustomSchemeDialog()
                    }
                }
            }
            setOnItemLongClickListener { v, position ->
                val item = getItem(position)
                if (item.getType() == ItemType.KeyValue) {
                    val kvItem = item as KeyValueBean
                    showRemoveCustomSchemeDialog(kvItem.name)
                }
                true
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
        refreshData()
    }

    private fun refreshData() {
        adapter.refreshData(WeiboScheme.getSchemeData(mContext))
    }

    private fun sendScheme(scheme: String?) {
        if (scheme.isNullOrEmpty())
            return
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(scheme))
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun showAddCustomSchemeDialog() {
        val binding = DialogAddSchemeBinding.inflate(
            LayoutInflater.from(mContext), null, false
        )
        AlertBaseDialog(this)
            .setDialogContext(binding.root)
            .setLeftButton("取消")
            .setRightButton("确认") {
                val nickName = binding.etNickname.text.toString()
                val schemeStr = binding.etScheme.text.toString()
                saveCustomSchemeData(nickName, schemeStr)
            }
            .show()
    }

    private fun showRemoveCustomSchemeDialog(name: String) {
        AlertDialog(this)
            .setMessage("是否删除【 $name 】？")
            .setLeftButton("取消")
            .setRightButton("确认") {
                SchemeKVManager(mContext).removeCustomScheme(name)
                refreshData()
            }
            .show()
    }

    private fun saveCustomSchemeData(name: String?, scheme: String?) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(scheme))
            return
        Log.e("ms_log", "收到：$name $scheme")
        SchemeKVManager(mContext).addCustomScheme(name!!, scheme!!)
        refreshData()
    }

}