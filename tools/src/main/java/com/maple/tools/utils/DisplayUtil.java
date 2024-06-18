package com.maple.tools.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 手机屏幕显示工具类
 * 兼容 Android 11、12
 *
 * @author shaoshuai
 */
public class DisplayUtil {
    public static final int DEF_VALUE = 0;// 默认值

    // 是否使用通用方法
    public static boolean isUseGeneralMethod() {
        return true;
    }

    // 获取App显示区 宽高
    public static int getAppDisplayWidth(Context context) {
        return getAppDisplayRect(context, DEF_VALUE).width();
    }

    public static int getAppDisplayWidth(Context context, int defValue) {
        return getAppDisplayRect(context, defValue).width();
    }

    public static int getAppDisplayHeight(Context context) {
        return getAppDisplayRect(context, DEF_VALUE).height();
    }

    public static int getAppDisplayHeight(Context context, int defValue) {
        return getAppDisplayRect(context, defValue).height();
    }

    // 获取设备屏幕 宽高
    public static int getDeviceScreenWidth(Context context) {
        return getDeviceScreenRect(context, DEF_VALUE).width();
    }

    public static int getDeviceScreenWidth(Context context, int defValue) {
        return getDeviceScreenRect(context, defValue).width();
    }

    public static int getDeviceScreenHeight(Context context) {
        return getDeviceScreenRect(context, DEF_VALUE).height();
    }

    public static int getDeviceScreenHeight(Context context, int defValue) {
        return getDeviceScreenRect(context, defValue).height();
    }

    public static void test(Context context) {
        getAppDisplaySize(context);
        getAppDisplayRect(context);
        getDeviceScreenRect(context);

        int top = getStatusBarHeight(context);
        int nav = getNavigationBarHeight(context);
        Log.i("Display12", "top:" + top + "  nav:" + nav);

        getDensityDpi(context);
    }

    @NonNull
    public static Size getAppDisplaySize(Context context, int defValue) {
        Size size = getAppDisplaySize(context);
        if (size == null)
            size = new Size(defValue, defValue);
        return size;
    }

    /**
     * 获得App可用的显示区域大小
     * 不包含 导航条 和 刘海屏 区域。
     */
    @Nullable
    public static Size getAppDisplaySize(Context context) {
        WindowManager wm = getWindowManager(context);
        if (wm == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && isUseGeneralMethod()) {
            final WindowMetrics metrics = wm.getCurrentWindowMetrics();
            // Gets all excluding insets
            final WindowInsets windowInsets = metrics.getWindowInsets();
            Insets insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout());
            int insetsWidth = insets.right + insets.left;
            int insetsHeight = insets.top + insets.bottom;
            // Legacy size that Display#getSize reports
            final Rect bounds = metrics.getBounds();
            Size size = new Size(bounds.width() - insetsWidth, bounds.height() - insetsHeight);
            Log.i("Display12", "new-app-size: " + size.getWidth() + " - " + size.getHeight());
            return size;
        } else {
            // 获得App可用的显示区域信息，表示显示器的大小减去任何系统装饰。
            Display display = wm.getDefaultDisplay();
            if (display != null) {
                DisplayMetrics dm = new DisplayMetrics();
                // Android 11 弃用 Display.getSize()、Display.getMetrics()
                display.getMetrics(dm);
                Log.i("Display12", "app-size: " + dm.widthPixels + "   " + dm.heightPixels);
                return new Size(dm.widthPixels, dm.heightPixels);
            }
        }
        return null;
    }

    @NonNull
    public static Rect getAppDisplayRect(Context context, int defValue) {
        Rect rect = getAppDisplayRect(context);
        if (rect == null)
            rect = new Rect(defValue, defValue, defValue, defValue);
        return rect;
    }

    /**
     * 获得App可用的显示区域
     * 表示显示器的大小减去任何系统装饰，不包含 导航条 和 刘海屏 区域。
     */
    @Nullable
    public static Rect getAppDisplayRect(Context context) {
        // androidx.window.layout.WindowMetricsCalculatorCompat
        WindowManager wm = getWindowManager(context);
        if (wm == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && isUseGeneralMethod()) {
            final WindowMetrics metrics = wm.getCurrentWindowMetrics();
            // Gets all excluding insets
            final WindowInsets windowInsets = metrics.getWindowInsets();
            Insets insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout());
            // Legacy size that Display#getSize reports
            final Rect bounds = metrics.getBounds();
            Rect rect = new Rect(
                    bounds.left + insets.left,
                    bounds.top + insets.top,
                    bounds.right - insets.right,
                    bounds.bottom - insets.bottom
            );
            Log.i("Display12", "new-app-rect: " + rect.width() + " - " + rect.height());
            return rect;
        } else {
            // 获得App可用的显示区域信息，表示显示器的大小减去任何系统装饰。
            Display display = wm.getDefaultDisplay();
            if (display != null) {
                DisplayMetrics dm = new DisplayMetrics();
                // Android 11 弃用 Display.getSize()、Display.getMetrics()
                display.getMetrics(dm);
                Log.i("Display12", "app-rect: " + dm.widthPixels + "   " + dm.heightPixels);
                return new Rect(0, 0, dm.widthPixels, dm.heightPixels);
            }
        }
        return null;
    }

    @NonNull
    public static Rect getDeviceScreenRect(Context context, int defValue) {
        Rect rect = getDeviceScreenRect(context);
        if (rect == null)
            rect = new Rect(defValue, defValue, defValue, defValue);
        return rect;
    }

    /**
     * 获得设备屏幕大小，包含所有系统栏区域
     * 当系统模拟较小的显示器时，它可能小于物理尺寸。
     * <p>
     * Mi 11 : (0, 0 - 1440, 3200)
     * 1+ 9  : (0, 0 - 1080, 2400)
     */
    @Nullable
    public static Rect getDeviceScreenRect(Context context) {
        // androidx.window.layout.WindowMetricsCalculatorCompat
        WindowManager wm = getWindowManager(context);
        if (wm == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && isUseGeneralMethod()) {
            Rect rect = wm.getCurrentWindowMetrics().getBounds();
            Log.i("Display12", "new-device: " + rect.width() + " - " + rect.height());
            return rect;
        } else {
            Display display = wm.getDefaultDisplay();
            if (display != null) {
                DisplayMetrics dm = new DisplayMetrics();
                // Android 12 弃用 Display.getRealSize()、Display.getRealMetrics()
                display.getRealMetrics(dm);
                Log.i("Display12", "device: " + dm.widthPixels + "   " + dm.heightPixels);
                return new Rect(0, 0, dm.widthPixels, dm.heightPixels);
            }
        }
        return null;
    }

//    public static int getDensityDpi() {
//        return getDensityDpi(IShareApp.getApp());
//    }

    /**
     * 获取屏幕密度
     */
    public static int getDensityDpi(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && isUseGeneralMethod()) {
            return context.getResources().getConfiguration().densityDpi;
        } else {
            // int metrics = context.getResources().getDisplayMetrics().densityDpi;
            WindowManager wm = getWindowManager(context);
            if (wm == null)
                return 0;
            Display display = wm.getDefaultDisplay();
            if (display == null)
                return 0;
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            return outMetrics.densityDpi;
        }
    }

    @Nullable
    public static WindowManager getWindowManager(Context context) {
        if (context == null)
            return null;
        if (context instanceof Activity) {
            return ((Activity) context).getWindowManager();
        } else {
            return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
    }

    public static int getStatusBarHeight(Context context) {
        return getStatusBarHeight(context, 0);
    }

    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Context context, int defValue) {
        if (context == null)
            return defValue;
        Resources resources = context.getResources();
        int resId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return resources.getDimensionPixelOffset(resId);
        }
        return defValue;
        // return resources.getDimensionPixelOffset(R.dimen.status_bar_height);
    }

    public static int getNavigationBarHeight(Context context) {
        return getNavigationBarHeight(context, 0);
    }

    /**
     * 获取底部导航栏的高度
     */
    public static int getNavigationBarHeight(Context context, int defValue) {
        if (context == null || !hasNavBar(context)) {
            return defValue;
        }
        Resources resources = context.getResources();
        int resId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resId > 0) {
            return resources.getDimensionPixelSize(resId);
        }
        return defValue;
    }

    /**
     * 隐藏NavBar
     */
    public static void hideNavBar(Context mContext, Window window) {
        if (!hasNavBar(mContext) || window == null) {
            return;
        }
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        window.getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        //布局位于状态栏下方
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        //全屏
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        //隐藏导航栏
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                if (Build.VERSION.SDK_INT >= 19) {
                    uiOptions |= 0x00001000;
                } else {
                    uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
                }
                window.getDecorView().setSystemUiVisibility(uiOptions);
            }
        });
    }

    /**
     * 是否有导航栏
     */
    public static boolean hasNavBar(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            for (int i = 0; i < decorView.getChildCount(); i++) {
                View view = decorView.getChildAt(i);
                if (view.getId() != -1 &&
                        "navigationBarBackground".equals(activity.getResources().getResourceEntryName(view.getId()))
                        && view.getVisibility() == View.VISIBLE) {
                    return true;
                }
            }
            return false;
        } else {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (wm != null) {
                Display display = wm.getDefaultDisplay();
                Point size = new Point();
                Point realSize = new Point();
                display.getSize(size);
                display.getRealSize(realSize);
                return realSize.y != size.y;
            } else {
                return true;
            }
        }
    }

    /**
     * 当前是否是横屏
     */
    public static boolean isHengPing(Context context) {
        int curOrientation = context.getResources().getConfiguration().orientation; // 获取屏幕方向
        if (curOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            return true;// 横屏
        } else if (curOrientation == Configuration.ORIENTATION_PORTRAIT) {
            return false;// 竖屏
        }
        return false;
    }
}
