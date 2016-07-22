package cx.sh.cn.drivingbehavior.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ZengChao on 2016/7/18.
 */
public class SharedPreferencedTools {

    public SharedPreferences mShared;
    // 连续驾驶时间基准
    public static String KEY_STD_CON_MIN = "KEY_STD_CON_MIN";
    // 连续驾驶休息时间基准
    public static String KEY_STD_CON_RELEASE_MIN = "KEY_STD_CON_RELEASE_MIN";
    // 怠速时间基准
    public static String KEY_STD_IDING = "KEY_STD_IDING";
    // 超速时间基准
    public static String KEY_STD_OVERSPEED = "KEY_STD_OVERSPEED";
    // 急转弯基准
    public static String KEY_STD_RUSH_TURN = "KEY_STD_RUSH_TURN";
    // 急加速基准
    public static String KEY_STD_RUSH_ACC = "KEY_STD_RUSH_ACC";
    // 急起步基准
    public static String KEY_STD_RUSH_LAUNCH = "KEY_STD_RUSH_LAUNCH";
    // 急减速基准
    public static String KEY_STD_RUSH_SPEED_DOWN = "KEY_STD_RUSH_SPEED_DOWN";
    // 縱震動速基准
    public static String KEY_STD_SHAKE = "KEY_STD_SHAKE";

    public SharedPreferencedTools(Context context) {
        mShared = context.getSharedPreferences("stdValues", Context.MODE_PRIVATE);
    }

    public void putIntValue(String key, int value) {
        mShared.edit().putInt(key, value).commit();
    }

    public int getValue(String key) {
       return mShared.getInt(key, Integer.MIN_VALUE);
    }
}
