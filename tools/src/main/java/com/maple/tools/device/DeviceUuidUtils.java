package com.maple.tools.device;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.util.UUID;

/**
 * 设备工具类
 */
public class DeviceUuidUtils {
    private static final String SP_FILE_NAME = "device_id.xml";
    private static final String KEY_UUID = "device_id";

    /**
     * 获取设备的唯一标识码
     */
    public static String getUUID(Context context) {
        // 数据持久化，将 buildDeviceUUID 生产存储到sp文件，如果有直接获取，没有才生成。
        final SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME, 0);
        String deviceId = sp.getString(KEY_UUID, null);
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = buildDeviceUUID(context);
            sp.edit().putString(KEY_UUID, deviceId).commit();
        }
        return deviceId;
    }

    /**
     * 以AndroidId和设备build相关信息生产唯一UUID
     * <p>
     * <a href="https://developer.android.com/training/articles/user-data-ids?hl=zh-cn#java">Android 官方推荐做法</a>
     */
    public static String buildDeviceUUID(Context context) {
        // ffffffff-8df3-1ee3-0000-000049542248
        String androidId = getAndroidId(context);
        String deviceUUID = getDeviceUUID().toString();
        String uuid = new UUID(androidId.hashCode(), deviceUUID.hashCode()).toString();
        Log.e("ms_logs", "androidId: " + androidId
                + "\ndeviceUUID: " + deviceUUID
                + "\nuuid: " + uuid
        );
        return uuid;
    }

    /**
     * 获得设备的AndroidId
     * 备注：无需权限，极个别设备获取不到数据
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidId(Context context) {
        String androidId = "";
        try {
            // d55e1d246a7b4bba
            androidId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("ms_logs", "androidId: " + androidId);
        // 特殊处理，如果获取不到AndroidId或获取的值是返回同样的特定字符串
        if (TextUtils.isEmpty(androidId)
                || TextUtils.equals("000000000000000", androidId)
                || TextUtils.equals("00000000000000", androidId)
                || TextUtils.equals("9774d56d682e549c", androidId)
        ) {
            androidId = UUID.randomUUID().toString();
        }
        return androidId;
    }

    /**
     * 选用了几个不会随系统更新而改变的值
     * 备注：无需权限，同型号设备相同
     */
    private static UUID getDeviceUUID() {
        // cepheus|Xiaomi|cepheus|cepheus|qcom|MI 9|V12.5.6.0.RFACNXM
        String buildInfo = Build.BOARD +
                "|" + Build.BRAND +
                "|" + Build.PRODUCT +
                "|" + Build.DEVICE +
                "|" + Build.HARDWARE +
                "|" + Build.MODEL +
                "|" + Build.VERSION.INCREMENTAL;
        Log.e("ms_logs", "buildInfo: " + buildInfo);
        try {
            // Build.ID:RKQ1.200826.002
            return new UUID(
                    Build.ID.hashCode(),
                    buildInfo.hashCode()
            );
        } catch (Exception e) {
        }
        return new UUID(
                UUID.randomUUID().toString().hashCode(),
                buildInfo.hashCode()
        );
    }

}
