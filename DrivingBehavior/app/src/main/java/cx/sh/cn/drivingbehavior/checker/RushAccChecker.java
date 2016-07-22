package cx.sh.cn.drivingbehavior.checker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cx.sh.cn.drivingbehavior.DrivingBehaviorApplication;
import cx.sh.cn.drivingbehavior.data.GpsData;
import cx.sh.cn.drivingbehavior.data.ViolRushAccData;
import cx.sh.cn.drivingbehavior.utils.SharedPreferencedTools;

/**
 * Created by ZengChao on 2016/7/4.
 *
 * 急加速驾驶违反检查
 *
 */
public class RushAccChecker {

    private Context mContext;

    private static final String TAG = "--RushAccChecker--";

    /**
     * 起步5秒
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
     * 违反是否开始
     */
    private boolean isStart = false;

    /**
     * 急加速状态
     */
    private boolean isViol = false;

    private short stdAcceleration;

    private ViolRushAccData mAccData;

    private GpsData mPreGpsData;

    /**
     * 构造函数
     *
     * @param context
     */
    public RushAccChecker(Context context) {
        mContext = context;
        stdAcceleration = (short) DrivingBehaviorApplication.getmInstance().getmSharedTools().getValue(SharedPreferencedTools.KEY_STD_RUSH_ACC);
    }

    /**
     * 是否急加速
     *
     * @param acc
     *            short
     * @return boolean 是否急加速
     */
    private boolean isAccelerate(short acc) {

//        int stdValue = StandardValue.getInstance().getSvLaunch();
        return acc >= stdAcceleration ? true : false;

    }

    /**
     * 是否开始急加速
     *
     * @param data
     *            ObdData
     * @param acc
     *            short
     */
    private void isStart(GpsData data, short acc) {

        // 计算从车速为0至当前车速的时间
        long diffTime = (data.getSeconds() - zeroTimeSec) * 1000 + data.getMilliseconds() - zeroTimeMillSec;

        // 判断时间是否大于5秒 && 为急加速状态
        if (diffTime > VIOL_TIME_PARA && isAccelerate(acc)) {

            // 设置开始状态
            isStart = true;

//            // 急加速违反记录
//            super.violData = new ViolRushAccData();
//            // 开始时间(秒)
//            super.violData.setStartSec((int) data.getSeconds());
//            // 开始时间(毫秒)
//            super.violData.setStartMilliSec((short) data.getMilliseconds());
//            // 纬度
//            super.violData.setLatitude(data.getGpsData().getLatitude());
//            // 经度
//            super.violData.setLongitude(data.getGpsData().getLongitude());
//            // 最大加速度
//            super.violData.setMaxAcceleration(acc);

            String msg = "急加速违反开始,加速度为:"+acc;
            DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
            Intent intent  = new Intent("TOAST_ACTION");
            intent.putExtra("TOAST_MSG", msg);
            mContext.sendBroadcast(intent);
            Log.d(TAG, msg);

            // 急加速违反记录
            mAccData = new ViolRushAccData();
            // 开始时间(秒)
            mAccData.setStartSec((int) data.getSeconds());
            // 开始时间(毫秒)
            mAccData.setStartMilliSec((short) data.getMilliseconds());
            // 纬度
            mAccData.setLatitude((int)data.getLatitude());// TODO 强制转换丢失精度
            // 经度
            mAccData.setLongitude((int)data.getLongitude());// TODO 强制转换丢失精度
            // 最大加速度
            mAccData.setMaxAcceleration(acc);
        }

    }

    /**
     * doCheck: 判断急加速违反
     *
     * @param data
     *            data
     */
    public void doCheck(GpsData data) {

        // 车速信号未取到
        if (data.getSpeed() == Float.MIN_VALUE || data.getSpeed() == 0.0f) {
            // LogDog.w("判断急加速违反：车速信号未取到");
            return;
        }

//        // wujia Add start
//        ObdData preObdData = (ObdData) SafeService.getInstance().getObdGenerator().getPreWorkData();
//
//        if (preObdData == null) {
//            return;
//        }

        if (data.getSpeed() == 0) {

            // 0车速时时间(秒)
            zeroTimeSec = (int) data.getSeconds();

            // 0车速时时间(毫秒)
            zeroTimeMillSec = (short) data.getMilliseconds();

        }

//        float curSpeed = data.getSpeed() / 3.6F;
//        float preSpeed = preObdData.getSpeed() / 3.6F;

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

        // LogDog.i("前条车速=" + preSpeed + "\t当前车速=" + curSpeed +
        // "\t加速度=" + acc);

        if (!this.isStart) {

            isStart(data, acc);

        } else {

            if (this.isViol) {

                if (!isAccelerate(acc)) {

//                    // 生成急加速违反记录
//                    super.addHisDataFileMng(super.violData);

//                    LogDog.i("急加速违反,基准值为" + StandardValue.getInstance().getSvLaunch());
                    String msg = "急加速违反结束,基准值为:"+stdAcceleration;
                    DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
                    Intent intent  = new Intent("TOAST_ACTION");
                    intent.putExtra("TOAST_MSG", msg);
                    mContext.sendBroadcast(intent);
                    Log.d(TAG, msg);

                    // 初始化
                    this.isViol = false;
                    this.isStart = false;
                    this.zeroTimeSec = 0;
                    this.zeroTimeMillSec = 0;
//                    super.violData = null;
                    mAccData = null;

                } else {

//                    if (acc > super.violData.getMaxAcceleration()) {
                    if (acc > mAccData.getMaxAcceleration()) {

                        // 更新最大加速度
//                        super.violData.setMaxAcceleration(acc);
                        mAccData.setMaxAcceleration(acc);
                    }
                }
            } else {
                if (isAccelerate(acc)) {

                    // 计算连续时间
//                    int curTimeSec = (int) ((data.getSeconds() - super.violData.getStartSec()) * 1000
//                            + data.getMilliseconds() - super.violData.getStartMilliSec());
                    int curTimeSec = (int) ((data.getSeconds() - mAccData.getStartSec()) * 1000
                            + data.getMilliseconds() - mAccData.getStartMilliSec());

                    // 计算转速持续时间基准(毫秒)
                    int rpmConTimeSec = 1 * 1000;

                    if (curTimeSec >= rpmConTimeSec) {

                        isViol = true;
                    }
//                    if (acc > super.violData.getMaxAcceleration()) {
                    if (acc > mAccData.getMaxAcceleration()) {

                        // 更新最大加速度
//                        super.violData.setMaxAcceleration(acc);
                        mAccData.setMaxAcceleration(acc);
                    }
                } else {
                    isViol = false;
                    isStart = false;
                    zeroTimeSec = 0;
                    zeroTimeMillSec = 0;
//                    super.violData = null;
                    mAccData = null;
                }
            }
        }
        mPreGpsData = data;
    }


}
