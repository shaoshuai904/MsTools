package com.maple.tools.device

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.StatFs
import android.provider.Settings
import android.util.Log
import com.maple.tools.utils.ConversionUtils
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.util.regex.Pattern

/**
 * 设备工具类
 */
object DeviceUtils {

    /**
     * 获取设备的唯一标识码
     */
    fun getUUID(context: Context): String {
        return DeviceUuidUtils.buildDeviceUUID(context)
    }

    /**
     * 获得设备的AndroidId
     * 备注：无需权限，极个别设备获取不到数据
     */
    @SuppressLint("HardwareIds")
    fun getAndroidId(context: Context): String {
        var androidId = ""
        try {
            androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return androidId
    }

    fun getPackageInfo(context: Context): PackageInfo? {
        var pi: PackageInfo? = null
        try {
            val pm = context.packageManager
            pi = pm.getPackageInfo(context.packageName, PackageManager.GET_CONFIGURATIONS)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return pi
    }

    /**
     * 获取当前进程内存使用信息
     */
    fun getProcessMemInfo(): MemInfo {
        return try {
            val maxMemory = Runtime.getRuntime().maxMemory()
            val totalMemory = Runtime.getRuntime().totalMemory()
            Log.e(
                "ms_story", "getProcessMemInfo: maxMemory:${ConversionUtils.convertB(maxMemory)} " +
                        " totalMemory: ${ConversionUtils.convertB(totalMemory)}"
            )
            MemInfo(maxMemory, maxMemory - totalMemory, maxMemory - totalMemory)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            MemInfo()
        }
    }

    /**
     * 获取当前设备内存使用信息
     */
    fun getMemInfo(): MemInfo {
        var fis: FileInputStream? = null
        return try {
            /*the memory information file path under linux os*/
            val mempath = "/proc/meminfo"

            /*read data in the memory information file*/
            fis = FileInputStream(mempath)
            val buffer = ByteArray(4096)
            val baos = ByteArrayOutputStream()
            var sz = fis.read(buffer)
            while (sz != -1) {
                baos.write(buffer, 0, sz)
                sz = fis.read(buffer)
            }
            fis.close()
            fis = null

            /*parse the total memory and left memory*/
            val strMemInfo = baos.toString()
            Log.e("ms_story", "getMemInfo: strMemInfo:$strMemInfo")
            val infos = HashMap<String, Long>()
            val p = Pattern.compile("(\\w+):\\s*(\\d+)")
            val m = p.matcher(strMemInfo)
            while (m.find()) {
                infos[m.group(1)] = java.lang.Long.valueOf(m.group(2))
            }
            if (infos.containsKey("MemTotal")
                && infos.containsKey("MemAvailable")
                && infos.containsKey("MemFree")
            ) {
                MemInfo(infos["MemTotal"], infos["MemAvailable"], infos["MemFree"])
            } else {
                MemInfo()
            }
        } catch (e: java.lang.Exception) {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }
            MemInfo()
        }
    }

    /**
     * memory information data structure
     */
    data class MemInfo(
        var totalBytes: Long? = -1,
        var availableBytes: Long? = -1,
        var freeBytes: Long? = -1
    ) {
        constructor() : this(-1, -1, -1)

    }

    /**
     * 获取一个路径下磁盘使用信息
     */
    fun getFileStorageInfo(path: String): MemInfo {
        return try {
            val sf = StatFs(path)
            val total = sf.totalBytes
            val available = sf.availableBytes
            val free = sf.freeBytes
            Log.e(
                "ms_story", "getFileStorageInfo $path: \n" +
                        "total:${ConversionUtils.convertB(total)}, " +
                        "available:${ConversionUtils.convertB(available)}, " +
                        "free:${ConversionUtils.convertB(free)}"
            )
            MemInfo(total, available, free)
        } catch (e: java.lang.Exception) {
            MemInfo()
        }
    }

}