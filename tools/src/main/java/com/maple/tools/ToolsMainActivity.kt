package com.maple.tools

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.maple.msdialog.AlertEditDialog
import com.maple.msdialog.OnEditTextCallListener
import com.maple.msdialog.utils.DensityUtils.dp2px
import com.maple.tools.databinding.ActivityToolsMainBinding
import com.maple.tools.utils.SchemeKVManager

class ToolsMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityToolsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI(binding)
    }

    private fun initUI(binding: ActivityToolsMainBinding) {
        with(binding) {
            with(topBar) {
                ivBack.setOnClickListener { onBackPressed() }
                tvTitle.text = "更多功能"
            }
            tvOther.setOnLongClickListener {
                showProjectDevDialog()
                true
            }
        }
    }

    private fun showProjectDevDialog() {
        AlertEditDialog(
            this,
            AlertEditDialog.Config(this).apply {
                scaleWidth = 0.7
                messagePaddingBottom = 15f.dp2px(context)
            })
            .setMessage("输入口令")
            .setRightButton("确认", OnEditTextCallListener { str ->
                if ("weibo_dev" == str) {
                    doDevFunction()
                }
            })
            .setLeftButton("取消")
            .show()
    }

    private fun doDevFunction() {
        SchemeKVManager(baseContext).addCustomScheme("工程模式", "sinaweibo://projectmode")
        SchemeKVManager(baseContext).addCustomScheme("AB开关", "sinaweibo://greyconfig")

        Toast.makeText(this, "执行成功", Toast.LENGTH_SHORT).show()
    }

}

