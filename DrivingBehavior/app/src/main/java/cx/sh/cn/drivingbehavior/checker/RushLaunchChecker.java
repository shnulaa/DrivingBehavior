package cx.sh.cn.drivingbehavior.checker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cx.sh.cn.drivingbehavior.DrivingBehaviorApplication;
import cx.sh.cn.drivingbehavior.data.GpsData;
import cx.sh.cn.drivingbehavior.data.ViolRushLaunchData;
import cx.sh.cn.drivingbehavior.utils.SharedPreferencedTools;

/**
 * Created by ZengChao on 2016/7/5.
 *
 * 急起步驾驶违反检查
 *
 */
public class RushLaunchChecker {
    private static final String TAG = "--RushLaunchChecker--";

    private  Context mContext;

    /**
     * 起步5秒后
     */
    private static int VIOL_TIME_PARA = 5000;

    /**
     * 0车速时时间(秒)
     */
    private int zeroTimeSec = 0;

    /**
     * 0车速时时间(毫秒)
     */
    private short zeroTimeMillSec = 0;

    /**
     * 是否开始
     */
    private boolean isStart = false;

    /**
     * 急起步状态
     */
    private boolean isViol = false;

    /**
     * 前条记录
     */
    private GpsData mPreGpsData;

    private int stdAcceleration;

    private ViolRushLaunchData mLaunchData;

    /**
     * RushLaunchChecker: 构造函数
     *
     * @param context
     *
     */
    public RushLaunchChecker(Context context) {
        mContext = context;
        stdAcceleration = DrivingBehaviorApplication.getmInstance().getmSharedTools().getValue(SharedPreferencedTools.KEY_STD_RUSH_LAUNCH);
    }

    /**
     * 判断使用急加速
     *
     * @param acc
     *            short
     * @return boolean 是否急加速
     */
    private boolean isAcceleration(short acc) {
//        int stdValue = StandardValue.getInstance().getSvLaunch();
        return acc >= stdAcceleration ? true : false;
    }

    /**
     * 判断是否开始急加速
     *
     * @param data
     *            ObdData
     * @param acc
     *            short
     */
    private void isStart(GpsData data, short acc) {

        long diffTime = (data.getSeconds() - zeroTimeSec) * 1000 + data.getMilliseconds() - zeroTimeMillSec;

        if (diffTime <= VIOL_TIME_PARA && isAcceleration(acc)) {

            isStart = true;

//            // 急起步违反记录
//            super.violData = new ViolRushLaunchData();
//
//            // 开始时间(秒)
//            super.violData.setStartSec((int) data.getSeconds());
//
//            // 开始时间(毫秒)
//            super.violData.setStartMilliSec((short) data.getMilliseconds());
//
//            // 纬度
//            super.violData.setLatitude(data.getGpsData().getLatitude());
//
//            // 经度
//            super.violData.setLongitude(data.getGpsData().getLongitude());
//
//            // 最大加速度
//            super.violData.setMaxAcceleration(acc);

            String msg = "急起步违反开始,加速度为:"+acc;
            DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
            Intent intent  = new Intent("TOAST_ACTION");
            intent.putExtra("TOAST_MSG", msg);
            mContext.sendBroadcast(intent);
            Log.d(TAG, msg);

            // 急加速违反记录
            mLaunchData = new ViolRushLaunchData();
            // 开始时间(秒)
            mLaunchData.setStartSec((int) data.getSeconds());
            // 开始时间(毫秒)
            mLaunchData.setStartMilliSec((short) data.getMilliseconds());
            // 纬度
            mLaunchData.setLatitude((int)data.getLatitude());// TODO 强制转换丢失精度
            // 经度
            mLaunchData.setLongitude((int)data.getLongitude());// TODO 强制转换丢失精度
            // 最大加速度
            mLaunchData.setMaxAcceleration(acc);
        }
    }

    /**
     * doCheck: 判断急起步违反
     *
     * @param data
     *            GpsData
     */
    public void doCheck(GpsData data) {

        // 车速信号未取到
        if (data.getSpeed() == Float.MIN_VALUE || data.getSpeed() == 0.0f) {
            // LogDog.w("急起步违反判断：车速信号未取到");
            return;
        }

//        // wujia Add start
//        if (this.preObdData == null) {
//
//            // 设置前条记录
//            this.preObdData = data;
//            return;
//
//        }

        if (data.getSpeed() == 0) {

            // 0车速时时间(秒)
            zeroTimeSec = (int) data.getSeconds();

            // 0车速时时间(毫秒)
            zeroTimeMillSec = (short) data.getMilliseconds();

        }

//        float curSpeed = data.getSpeed() / 3.6F;
//        float preSpeed = this.preObdData.getSpeed() / 3.6F;


        short acc = 0;
        if (mPreGpsData != null) {
            float curSpeed = data.getSpeed();
            float preSpeed = mPreGpsData.getSpeed();
            // 时间差
            long diffSeconds = ((data.getSeconds() * 1000 + data.getMilliseconds())
                    - (mPreGpsData.getSeconds() * 1000 + mPreGpsData.getMilliseconds())) /1000;

            // 计算加速度
//        short acc = (short) Math.abs(((curSpeed - preSpeed) * 10 / (1.0 * 9.8)));
            acc = (short) ((curSpeed - preSpeed) / diffSeconds);
        }
        Log.d(TAG, "acc----:"+acc);

        if (!isStart) {

            isStart(data, acc);

        } else {

            if (isViol) {

                if (!isAcceleration(acc)) {

//                    super.addHisDataFileMng(super.violData);
//
//                    LogDog.i("急起步违反,基准值为" + StandardValue.getInstance().getSvLaunch());
                    String msg = "急起步违反结束,基准值为:"+stdAcceleration;
                    DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
                    Intent intent  = new Intent("TOAST_ACTION");
                    intent.putExtra("TOAST_MSG", msg);
                    mContext.sendBroadcast(intent);
                    Log.d(TAG, msg);

                    isViol = false;
                    isStart = false;
                    zeroTimeSec = 0;
                    zeroTimeMillSec = 0;
//                    super.violData = null;
                    mLaunchData = null;
                } else {
//                    if (acc > super.violData.getMaxAcceleration()) {
                    if (acc > mLaunchData.getMaxAcceleration()) {
//                        super.violData.setMaxAcceleration(acc);
                        mLaunchData.setMaxAcceleration(acc);
                    }
                }
            } else {
                if (isAcceleration(acc)) {

                    // 计算连续时间
//                    int curTimeSec = (int) ((data.getSeconds() - super.violData.getStartSec()) * 1000
//                            + data.getMilliseconds() - super.violData.getStartMilliSec());
                    int curTimeSec = (int) ((data.getSeconds() - mLaunchData.getStartSec()) * 1000
                            + data.getMilliseconds() - mLaunchData.getStartMilliSec());

                    // 计算转速持续时间基准(毫秒)
                    int rpmConTimeSec = 1 * 1000;

                    if (curTimeSec >= rpmConTimeSec) {
                        isViol = true;
                    }

//                    if (acc > super.violData.getMaxAcceleration()) {
                    if (acc > mLaunchData.getMaxAcceleration()) {
//                        super.violData.setMaxAcceleration(acc);
                        mLaunchData.setMaxAcceleration(acc);
                    }
                } else {
                    isViol = false;
                    isStart = false;
                    zeroTimeSec = 0;
                    zeroTimeMillSec = 0;
//                    super.violData = null;
                    mLaunchData = null;
                }
            }
//            // 设置前条记录
//            this.preObdData = data;
        }
        // wujia end
        // 设置前条记录
        mPreGpsData = data;
    }


}
