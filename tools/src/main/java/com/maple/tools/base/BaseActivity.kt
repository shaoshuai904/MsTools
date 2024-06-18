package com.maple.tools.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity


/**
 * 基类Activity
 */
abstract class BaseActivity : AppCompatActivity() {
    lateinit var mContext: Context
    override fun attachBaseContext(newBase: Context) {
//        val newContext = LanguageUtils.getNewLocalContext(newBase)
        super.attachBaseContext(newBase)
        mContext = newBase
    }

}