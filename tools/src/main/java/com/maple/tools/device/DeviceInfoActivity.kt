package com.maple.tools.device

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import com.maple.msdialog.DividerItemDecoration
import com.maple.msdialog.utils.DensityUtils
import com.maple.tools.base.BaseActivity
import com.maple.tools.base.ItemInfoBean
import com.maple.tools.base.ItemType
import com.maple.tools.base.KeyValueAdapter
import com.maple.tools.base.KeyValueBean
import com.maple.tools.base.NoDataBean
import com.maple.tools.base.TitleBean
import com.maple.tools.databinding.BaseKvListBinding
import com.maple.tools.utils.DisplayUtil

/**
 * 设备信息
 */
class DeviceInfoActivity : BaseActivity() {
    private lateinit var binding: BaseKvListBinding
    private val adapter by lazy {
        KeyValueAdapter(this@DeviceInfoActivity).apply {
            setOnItemClickListener { item, _ ->
                if (item.getType() == ItemType.KeyValue) {
//                    val kvItem = item as KeyValueBean
//                    binding.etText.setText(kvItem.value)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BaseKvListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        with(binding) {
            with(topBar) {
                ivBack.setOnClickListener { onBackPressed() }
                tvTitle.text = "设备信息"
            }
            rvData.adapter = adapter
            rvData.addItemDecoration(DividerItemDecoration(DensityUtils.dp2px(mContext, 10f)))
            // init data
            adapter.refreshData(getConfigListData())
        }
    }

    private fun getConfigListData(): MutableList<ItemInfoBean> {
        val kvs = mutableListOf<ItemInfoBean>()
        addDeviceInfoData(kvs)
        addDeviceScreenInfoData(kvs)
        addBuildData(kvs)
        addAppInfoData(kvs)
        addOtherData(kvs)
        return kvs
    }

    private fun addAppInfoData(kvs: MutableList<ItemInfoBean>) {
        kvs.add(TitleBean("App信息"))
        val pi = DeviceUtils.getPackageInfo(mContext)
        if (pi != null) {
            kvs.add(KeyValueBean("包名", pi.packageName))
            kvs.add(KeyValueBean("应用版本名", pi.versionName))
            kvs.add(KeyValueBean("应用版本号", pi.versionCode.toString()))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            kvs.add(KeyValueBean("最低系统版本", mContext.applicationInfo.minSdkVersion.toString()))
        }
        kvs.add(KeyValueBean("目标系统版本", mContext.applicationInfo.targetSdkVersion.toString()))

        kvs.add(KeyValueBean("android_id", DeviceUtils.getAndroidId(mContext)))
        kvs.add(KeyValueBean("UUID1", DeviceUtils.getUUID(mContext)))
    }

    private fun addDeviceInfoData(kvs: MutableList<ItemInfoBean>) {
        kvs.add(TitleBean("设备信息"))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            kvs.add(KeyValueBean("Android版本", Build.VERSION.RELEASE_OR_CODENAME))
        } else {
            kvs.add(KeyValueBean("Android版本", "${Build.VERSION.RELEASE} - ${Build.VERSION.CODENAME}"))
        }
        // kvs.add(KeyValueBean("SDK", Build.VERSION.SDK))
        kvs.add(KeyValueBean("Android SDK", Build.VERSION.SDK_INT.toString()))

        kvs.add(KeyValueBean("品牌", "${Build.BRAND} - ${Build.MODEL}"))
        kvs.add(KeyValueBean("设备ID", Build.ID))
        kvs.add(KeyValueBean("系统版本", Build.DISPLAY))
        kvs.add(KeyValueBean("系统软件版本", Build.VERSION.INCREMENTAL))

        kvs.add(KeyValueBean("CPU架构", Build.CPU_ABI))
        if (!TextUtils.isEmpty(Build.CPU_ABI2)) {
            kvs.add(KeyValueBean("CPU_ABI2", Build.CPU_ABI2))
        }
        kvs.add(KeyValueBean("支持的架构", Build.SUPPORTED_ABIS.toList().toString()))
        // kvs.add(KeyValueBean("支持的32位架构", Build.SUPPORTED_32_BIT_ABIS.toList().toString()))
        // kvs.add(KeyValueBean("支持的64位架构", Build.SUPPORTED_64_BIT_ABIS.toList().toString()))
    }

    private fun addDeviceScreenInfoData(kvs: MutableList<ItemInfoBean>) {
        kvs.add(TitleBean("屏幕信息"))
        val rect = DisplayUtil.getDeviceScreenRect(mContext, 0)
        kvs.add(KeyValueBean("屏幕像素", "${rect.width()} * ${rect.height()}"))
        kvs.add(KeyValueBean("像素密度", "${DisplayUtil.getDensityDpi(mContext)}"))
        kvs.add(KeyValueBean("顶部状态栏高度", "${DisplayUtil.getStatusBarHeight(mContext)}"))
        // val isShow = if (DisplayUtil.hasNavBar(mContext)) "- (显示中)" else ""// todo 华为有问题！
        kvs.add(KeyValueBean("底部导航栏高度", "${DisplayUtil.getNavigationBarHeight(mContext)}"))
    }

    private fun addBuildData(kvs: MutableList<ItemInfoBean>) {
        kvs.addAll(
            arrayListOf(
                TitleBean("主板信息"),
                KeyValueBean("产品型号", Build.PRODUCT),
                KeyValueBean("制造商", Build.MANUFACTURER),
                KeyValueBean("DEVICE", Build.DEVICE),
                KeyValueBean("主板", Build.BOARD),
            )
        )
        kvs.add(KeyValueBean("处理器", Build.HARDWARE))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // SOC 制造商 + 型号
            kvs.add(KeyValueBean("SOC型号", "${Build.SOC_MANUFACTURER} - ${Build.SOC_MODEL}"))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!TextUtils.isEmpty(Build.VERSION.BASE_OS)) {
                kvs.add(KeyValueBean("BASE_OS", Build.VERSION.BASE_OS))
            }
            kvs.add(KeyValueBean("安全补丁", Build.VERSION.SECURITY_PATCH))
            if (Build.VERSION.PREVIEW_SDK_INT > 0) {
                kvs.add(KeyValueBean("预览SDK", Build.VERSION.PREVIEW_SDK_INT.toString()))
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (isNotEmpty(Build.SKU)) {
                kvs.add(KeyValueBean("SKU", Build.SKU))
            }
            if (Build.VERSION.MEDIA_PERFORMANCE_CLASS > 0) {
                kvs.add(KeyValueBean("媒体性能类", Build.VERSION.MEDIA_PERFORMANCE_CLASS.toString()))
            }
            // kvs.add(KeyValueBean("IS_EMULATOR", Build.IS_EMULATOR))
            // KeyValueBean("getSerial", Build.getSerial()),
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            kvs.add(KeyValueBean("显示版本", Build.VERSION.RELEASE_OR_PREVIEW_DISPLAY))
        }
        if (isNotEmpty(Build.BOOTLOADER)) {
            kvs.add(KeyValueBean("系统启动程序版本", Build.BOOTLOADER))
        }
        if (isNotEmpty(Build.RADIO)) {
            kvs.add(KeyValueBean("RADIO", Build.RADIO))
        }
        if (isNotEmpty(Build.SERIAL)) {
            kvs.add(KeyValueBean("SERIAL", Build.SERIAL))
        }
    }

    private fun isNotEmpty(str: String?): Boolean {
        return !TextUtils.isEmpty(str) && "unknown" != str
    }

    private fun addOtherData(kvs: MutableList<ItemInfoBean>) {
        kvs.addAll(
            arrayListOf(
                TitleBean("其他信息"),
                NoDataBean("暂无数据"),
                // 添加一个空白行，保证最后一条信息 不至于贴底边
                TitleBean("")
            )
        )
    }

}