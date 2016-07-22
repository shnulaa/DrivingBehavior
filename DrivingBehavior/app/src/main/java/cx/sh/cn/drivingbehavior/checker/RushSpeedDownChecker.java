package cx.sh.cn.drivingbehavior.checker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cx.sh.cn.drivingbehavior.DrivingBehaviorApplication;
import cx.sh.cn.drivingbehavior.data.GpsData;
import cx.sh.cn.drivingbehavior.data.ViolRushSpeedDownData;
import cx.sh.cn.drivingbehavior.utils.SharedPreferencedTools;

/**
 * Created by ZengChao on 2016/7/5.
 *
 * 急减速驾驶检查
 *
 */
public class RushSpeedDownChecker {

    private static final String TAG = "-RushSpeedDownChecker-";

    private Context mContext;
    /**
     * 前条数据
     */
    private GpsData mPreGpsData;

    /**
     * 急减速状态
     */
    private boolean isViol = false;

    private boolean isStart = false;

    private int stdAcceleration;

    private ViolRushSpeedDownData mSpeedDownData;

    /**
     * RushSpeedDownChecker: 构造函数
     *
     * @param context
     *            Context
     */
    public RushSpeedDownChecker(Context context) {
        mContext = context;
        stdAcceleration = -DrivingBehaviorApplication.getmInstance().getmSharedTools().getValue(SharedPreferencedTools.KEY_STD_RUSH_SPEED_DOWN);
    }

    /**
     * 是否急减速
     *
     * @param acc
     *            short
     * @return boolean 是否急减速
     */
    private boolean isDeceleration(short acc) {
//        int stdValue = StandardValue.getInstance().getSvSpeedDown();
        return acc <= stdAcceleration ? true : false;
    }

    /**
     * 是否开始急减速
     *
     * @param data
     *            ObdData
     * @param acc
     *            short
     */
    private void isStart(GpsData data, short acc) {

        if (isDeceleration(acc)) {

            isStart = true;

//            // 急减速违反记录
//            super.violData = new ViolRushSpeedDownData();
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

            String msg = "急减速违反开始,加速度为:"+acc;
            DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
            Intent intent  = new Intent("TOAST_ACTION");
            intent.putExtra("TOAST_MSG", msg);
            mContext.sendBroadcast(intent);
            Log.d(TAG, msg);

            // 急减速违反记录
            mSpeedDownData = new ViolRushSpeedDownData();

            // 开始时间(秒)
            mSpeedDownData.setStartSec((int) data.getSeconds());

            // 开始时间(毫秒)
            mSpeedDownData.setStartMilliSec((short) data.getMilliseconds());

            // 纬度
            mSpeedDownData.setLatitude((int)data.getLatitude());

            // 经度
            mSpeedDownData.setLongitude((int)data.getLongitude());

            // 最大加速度
            mSpeedDownData.setMaxAcceleration(acc);

        }
    }

    /**
     * doCheck： 判断急减速违反
     *
     * @param data
     *            GpsData
     */
    public void doCheck(GpsData data) {

        // 车速信号未取到
//        if (data.getSpeed() == Integer.MIN_VALUE) {
        if (data.getSpeed() == Float.MIN_VALUE || data.getSpeed() == 0.0f) {
            // LogDog.w("急减速违反判断：车速信号未取到");
            return;
        }

//        // wujia Add start
//        if (this.preObdData == null) {
//            // 设置前条记录
//            this.preObdData = data;
//            return;
//
//        }

//        float curSpeed = data.getSpeed() / 3.6F;
//        float preSpeed = this.preObdData.getSpeed() / 3.6F;
//
//        // 计算急减速加速度
//        short acc = (short) Math.abs(((curSpeed - preSpeed) * 10 / (1.0 * 9.8)));

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

        if (!isStart) {
            isStart(data, acc);
        } else {
            if (isViol) {
                if (!isDeceleration(acc)) {

//                    super.addHisDataFileMng(super.violData);
//
//                    LogDog.i("急减速违反,基准值为" + StandardValue.getInstance().getSvSpeedDown());

                    String msg = "急减速违反结束,基准值为:"+stdAcceleration;
                    DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
                    Intent intent  = new Intent("TOAST_ACTION");
                    intent.putExtra("TOAST_MSG", msg);
                    mContext.sendBroadcast(intent);
                    Log.d(TAG, msg);

                    isViol = false;
                    isStart = false;
//                    super.violData = null;
                    mSpeedDownData = null;
                } else {
//                    if (acc > super.violData.getMaxAcceleration()) {
                    if (acc < mSpeedDownData.getMaxAcceleration()) {
//                        super.violData.setMaxAcceleration(acc);
                        mSpeedDownData.setMaxAcceleration(acc);
                    }
                }
            } else {
                if (isDeceleration(acc)) {
                    // 计算连续时间
//                    int curTimeSec = (int) ((data.getSeconds() - super.violData.getStartSec()) * 1000
//                            + data.getMilliseconds() - super.violData.getStartMilliSec());
                    int curTimeSec = (int) ((data.getSeconds() - mSpeedDownData.getStartSec()) * 1000
                            + data.getMilliseconds() - mSpeedDownData.getStartMilliSec());

                    // 计算转速持续时间基准(毫秒)
                    int rpmConTimeSec = 1 * 1000;

                    if (curTimeSec >= rpmConTimeSec) {
                        isViol = true;
                    }

//                    if (acc > super.violData.getMaxAcceleration()) {
                    if (acc < mSpeedDownData.getMaxAcceleration()) {
//                        super.violData.setMaxAcceleration(acc);
                        mSpeedDownData.setMaxAcceleration(acc);
                    }
                } else {
                    isViol = false;
                    isStart = false;
//                    super.violData = null;
                    mSpeedDownData = null;
                }
            }

//            // 设置前条记录
//            this.preObdData = data;
        }
        // wujia add end

        // 设置前条记录
        mPreGpsData = data;
    }


}
