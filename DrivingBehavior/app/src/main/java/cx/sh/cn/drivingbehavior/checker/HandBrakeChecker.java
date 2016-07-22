package cx.sh.cn.drivingbehavior.checker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cx.sh.cn.drivingbehavior.DrivingBehaviorApplication;
import cx.sh.cn.drivingbehavior.data.GpsData;
import cx.sh.cn.drivingbehavior.data.ViolHandBrakeData;

/**
 * Created by ZengChao on 2016/7/7.
 *
 * 未放手刹违反检测
 *
 */
public class HandBrakeChecker {

    private Context mContext;

    private static final String TAG = "--HandBrakeChecker--";

    private ViolHandBrakeData mHandBrakeData;

    /**
     * 开始时间(秒)
     */
    private int beginSeconds;

    /**
     * 开始时间(毫秒)
     */
    private short beginMil;

    /**
     * 纬度
     */
    private int latitude;

    /**
     * 经度
     */
    private int longitude;

    /**
     * 手刹状态(0:OFF 1:ON)
     */
    private static final byte ON = 1;
    private static final byte OFF = 0;

    /**
     * 是否未拉手刹
     */
    private boolean isHandBrack = false;

    /**
     * 构造函数
     *
     * @param context
     */
    public HandBrakeChecker(Context context) {
        mContext = context;
    }

    /**
     * doCheck: 判断未拉手刹违反
     *
     * @param data
     *            GpsData
     */
    public void doCheck(GpsData data) {

//        // 当手刹信号未取到
////        if (data.getBreakHand() == Byte.MAX_VALUE) {
//        if (data == null) {
//            // LogDog.w("未拉手刹违反判断：手刹信号未取到");
//            return;
//        }

        if (!this.isHandBrack) {

            // 判断当前是否 车速、转速>0并且手刹状态为开启状态
//            if (data.getSpeed() > 0 && data.getEngineSpeed() > 0 && data.getBreakHand() == HandBrakeChecker.ON) {
            if (data.getSpeed() > 0 && DrivingBehaviorApplication.getmInstance().getAccStatus()
                    && !DrivingBehaviorApplication.getmInstance().ismHandbrake()) {

                // 设置当前为未放手刹起步状态违反
                this.isHandBrack = true;

                // 获取违反信息
                this.beginSeconds = (int) data.getSeconds();
                this.beginMil = (short) data.getMilliseconds();
//                this.latitude = data.getGpsData().getLatitude();
//                this.longitude = data.getGpsData().getLongitude();
                this.latitude = (int) data.getLatitude();
                this.longitude = (int) data.getLongitude();

                String msg = "未放手刹起步违反开始。";
                DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
                Log.d(TAG, msg);
                Intent intent  = new Intent("TOAST_ACTION");
                intent.putExtra("TOAST_MSG", msg);
                mContext.sendBroadcast(intent);

            }
        } else {

            // 判断当前是否 手刹状态为关闭状态
//            if (data.getBreakHand() == HandBrakeChecker.OFF) {
            // 放下手刹
            if (DrivingBehaviorApplication.getmInstance().ismHandbrake()) {

//                if (super.violData == null) {
//                    super.violData = new ViolHandBrakeData();
//                }
                if (mHandBrakeData == null) {
                    mHandBrakeData = new ViolHandBrakeData();
                }

                // 设置违反信息
//                super.violData.setStartSec(this.beginSeconds);
//                super.violData.setStartMilliSec(this.beginMil);
//                super.violData.setLatitude(this.latitude);
//                super.violData.setLongitude(this.longitude);


                mHandBrakeData.setStartSec(this.beginSeconds);
                mHandBrakeData.setStartMilliSec(this.beginMil);
                mHandBrakeData.setLatitude(this.latitude);
                mHandBrakeData.setLongitude(this.longitude);

                // 生成违反记录文件
//                super.addHisDataFileMng(super.violData);
//                LogDog.i("未拉手刹起步违反:违反位置(" + super.violData.getLatitude() + "," + super.violData.getLongitude() + ")");


                String msg = "未放手刹起步违反结束。";
                DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
                Log.d(TAG, msg);
                Intent intent  = new Intent("TOAST_ACTION");
                intent.putExtra("TOAST_MSG", msg);
                mContext.sendBroadcast(intent);

                // 初始化
                this.isHandBrack = false;
//                super.violData = null;
                mHandBrakeData = null;
            }
        }
    }


}
