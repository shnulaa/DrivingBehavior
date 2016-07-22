package cx.sh.cn.drivingbehavior.checker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import cx.sh.cn.drivingbehavior.DrivingBehaviorApplication;
import cx.sh.cn.drivingbehavior.data.GpsData;
import cx.sh.cn.drivingbehavior.data.ViolIdlingData;
import cx.sh.cn.drivingbehavior.utils.SharedPreferencedTools;

/**
 * Created by ZengChao on 2016/7/6.
 *
 * 怠速违反检测
 *
 */
public class IdlingChecker {

    private static final String TAG = "--IdlingChecker--";

    private Context mContext;

    private ViolIdlingData mViolIdlingData;

    /**
     * 是否怠速开始
     */
    private boolean isIdling = false;

    /**
     * 是否怠速违反
     */
    private boolean isIdingViol = false;

    /**
     * 开始时间(秒)
     */
    private int startSeconds;

    /**
     * 开始时间(毫秒)
     */
    private short startMil;

    /**
     * 维度
     */
    private int latitude;

    /**
     * 经度
     */
    private int longitude;

    /**
     * 怠速基准值
     */
    private byte stdIdling;

    private boolean logPrint = false;

    /**
     * 构造函数
     */
    public IdlingChecker(Context context) {
        mContext = context;
        stdIdling = (byte)DrivingBehaviorApplication.getmInstance().getmSharedTools().getValue(SharedPreferencedTools.KEY_STD_IDING);
    }

//    private static IdlingChecker _instance = null;
//
//    public static void checkWhenAccOff() {
//        if (_instance != null) {
//            if (_instance.isIdingViol) {
//                LogDog.i("-------------ACCOFF怠速违反生成！-------------");
//                long curTime = System.currentTimeMillis();
//                // 写文件
//                ViolIdlingData violData = new ViolIdlingData();
//                violData.setStartSec(_instance.startSeconds);
//                violData.setStartMilliSec(_instance.startMil);
//                violData.setLatitude(_instance.latitude);
//                violData.setLongitude(_instance.longitude);
//                violData.setEndSec((int) (curTime / 1000));
//                violData.setEndMilliSec((short) (curTime % 1000));
//                violData.setStandardValue(_instance.stdIdling);
//
//                // 添加违反
//                LogDog.i("怠速违反结束:基准值为" + _instance.stdIdling + "分钟");
//                HisDataFileMng.getInstance().addViolBufListItem(violData);
//            }
//            _instance.resetStatus();
//        }
//    }
//
    private void resetStatus() {
        // 初始化
//        LogDog.i("-------------重置怠速违反状态-------------");
        Log.d(TAG, "重置怠速违反状态");
        this.isIdingViol = false;
        this.isIdling = false;
        this.startSeconds = 0;
        this.startMil = 0;
    }

    /**
     * doCheck:判断怠速违反
     *
     * @param data
     *            GpsData
     */
    public void doCheck(GpsData data) {

        // 转速/车速信号未取到
        if (data == null) {
            // LogDog.w("怠速违反判断：转速/车速信号未取到");
            Toast.makeText(mContext,"怠速违反判断时信号未取到", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean accOn = DrivingBehaviorApplication.getmInstance().getAccStatus();
        if (!accOn) {
            Log.d(TAG, "怠速监测，当前发动机还未启动。");
            if (!logPrint) {
                DrivingBehaviorApplication.getmInstance().getmLogUtils().print("怠速监测，当前发动机还未启动。");
                logPrint = true;
            }
            return;
        } else {
            logPrint = false;
        }

        if (!isIdling) {

            // 获取当前车速
            short curSpeed = (short) data.getSpeed();
            Log.d(TAG, "怠速监测，当前车速为："+curSpeed);

//            // 获取当前转速
//            short curEngineRpm = (short) data.getEngineSpeed();


            // 判断当前车速是否为0 && 转速>0
//            if (curSpeed == 0 && curEngineRpm > 0) {
            if (curSpeed == 0) {

                // 怠速开始状态设为true
                isIdling = true;

                // 获取怠速信息
                startSeconds = (int) data.getSeconds();
                startMil = (short) data.getMilliseconds();
//                latitude = data.getGpsData().getLatitude();
//                longitude = data.getGpsData().getLongitude();
                latitude = (int)data.getLatitude();
                longitude = (int)data.getLongitude();
            }
        } else {

//            if (data.getSpeed() == 0 && data.getEngineSpeed() > 0) {
            if (data.getSpeed() == 0) {

                // 获取怠速基准值（分钟）
//                stdIdling = (byte) StandardValue.getInstance().getIdlingTime();

                // 将基准值转换为毫秒
                int stdIdlingToMill = stdIdling * 60 * 1000;

                // 获取当前时间
                int curSec = (int) data.getSeconds();
                short cuMil = (short) data.getMilliseconds();

                // 计算实际怠速时间(毫秒)
                int userIdlingSec = (curSec - startSeconds) * 1000 + cuMil - startMil;

                // 怠速时间 >= 基准值时间
                if (userIdlingSec >= stdIdlingToMill) {

                    if (!isIdingViol) {
//                        LogDog.i("-------------怠速违反开始:基准值为" + stdIdling + "分钟-------------");
                        int userIdlingMin = userIdlingSec /1000 / 60;
                        String msg = "怠速违反开始:已经怠速" + userIdlingMin + "分钟";
                        DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
                        Log.d(TAG, msg);
                        Toast.makeText(mContext,msg, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent("TOAST_ACTION");
                        intent.putExtra("TOAST_MSG", msg);
                        mContext.sendBroadcast(intent);
                        isIdingViol = true;
                    }
                }

            } else {
                // 判断是否怠速违反
                if (isIdingViol) {
                    // 写文件
//                    super.violData = new ViolIdlingData();
//                    super.violData.setStartSec(this.startSeconds);
//                    super.violData.setStartMilliSec(this.startMil);
//                    super.violData.setLatitude(this.latitude);
//                    super.violData.setLongitude(this.longitude);
//                    super.violData.setEndSec((int) data.getSeconds());
//                    super.violData.setEndMilliSec((short) data.getMilliseconds());
//                    super.violData.setStandardValue(this.stdIdling);

                    mViolIdlingData = new ViolIdlingData();
                    mViolIdlingData.setStartSec(this.startSeconds);
                    mViolIdlingData.setStartMilliSec(this.startMil);
                    mViolIdlingData.setLatitude(this.latitude);
                    mViolIdlingData.setLongitude(this.longitude);
                    mViolIdlingData.setEndSec((int) data.getSeconds());
                    mViolIdlingData.setEndMilliSec((short) data.getMilliseconds());
                    mViolIdlingData.setStandardValue(this.stdIdling);

                    // 添加违反
//                    super.addHisDataFileMng(super.violData);
//                    LogDog.i("-------------怠速违反结束:基准值为" + stdIdling + "分钟-------------");
//                    super.violData = null;

                    String msg = "怠速违反结束:基准值为" + stdIdling + "分钟";
                    DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
                    Log.d(TAG, msg);
                    Toast.makeText(mContext,msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent("TOAST_ACTION");
                    intent.putExtra("TOAST_MSG", msg);
                    mContext.sendBroadcast(intent);

                    this.resetStatus();
                }
                isIdling = false;
            }
        }
    }


}
