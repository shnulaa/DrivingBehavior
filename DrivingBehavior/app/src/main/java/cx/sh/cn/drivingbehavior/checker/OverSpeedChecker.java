package cx.sh.cn.drivingbehavior.checker;

import android.animation.BidirectionalTypeConverter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import cx.sh.cn.drivingbehavior.DrivingBehaviorApplication;
import cx.sh.cn.drivingbehavior.data.GpsData;
import cx.sh.cn.drivingbehavior.data.ViolOverSpeedData;
import cx.sh.cn.drivingbehavior.utils.ConvTools;
import cx.sh.cn.drivingbehavior.utils.SharedPreferencedTools;

/**
 * Created by ZengChao on 2016/6/30.
 *
 * 超速驾驶违反检查
 */
public class OverSpeedChecker {

    private static final String TAG = "--OverSpeedChecker--";
    private ViolOverSpeedData mViolOverSpeedData;
    private Context mContext;

    /**
     * 是否超速违反
     */
    private boolean isViol = false;

    /**
     * 速度违反基准
     */
    private short stdSpeedValue = 0;

    /**
     * tmp
     */
    private int iCount = 0;

    /**
     * 构造函数
     *
     */
    public OverSpeedChecker(Context context) {
        mContext = context;
        mViolOverSpeedData = new ViolOverSpeedData();
    }

    /**
     * doCheck:判断超速违反
     *
     * @param data
     *            GpsData
     */
    public void doCheck(GpsData data) {

        // 车速信号未取到
        if (data.getSpeed() == Float.MIN_VALUE || data.getSpeed() == 0.0f) {
            Log.d(TAG, "超速违反判断：车速信号未取到");
            return;
        }

        // 取得当前车机信息
        int curSec =  (int) (data.getTime() / 1000);
        short curMillSec = (short) (data.getTime() % 1000);
//        int curLatitude = data.getGpsData().getLatitude();
//        int curLongitude = data.getGpsData().getLongitude();
        short currentSpeed = (short)data.getSpeed();
//        short currentRpm = (short) data.getEngineSpeed();


        // 判断当前是否为超速状态
        if (this.isViol) {
            if (currentSpeed > this.stdSpeedValue) {
                // 当前为超速状态
                if (currentSpeed >= mViolOverSpeedData.getMaxSpeed()) {
                    // 更新最高速度
                    mViolOverSpeedData.setMaxSpeed(currentSpeed);
                }
//                if (currentRpm > violData.getMaxRotationalSpeed()) {
//                    // 更新最高转速
//                    super.violData.setMaxRotationalSpeed(currentRpm);
//                }
            } else {

                // 记录结束时间、毫秒、经纬度
                mViolOverSpeedData.setEndSeconds(curSec);
                mViolOverSpeedData.setEndMilliSec(curMillSec);
//                super.violData.setEndLatitude(curLatitude);
//                super.violData.setEndLongitude(curLongitude);
                mViolOverSpeedData.setSpeedReferenceValue((short) this.stdSpeedValue);

                // 记录文件
//                super.addHisDataFileMng(super.violData);//TODO
                Log.d(TAG,"超速违反结束:最高车速为" + mViolOverSpeedData.getMaxSpeed());
                Intent intent = new Intent("TOAST_ACTION");
                String msg = "超速违反结束:最高车速为" + mViolOverSpeedData.getMaxSpeed();
                DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
                intent.putExtra("TOAST_MSG", msg);
                mContext.sendBroadcast(intent);

                // 初始化
                this.isViol = false;
                mViolOverSpeedData = null;
            }
        } else {

//            // 获得道路种别 1:高速/0:普通
//            int roadType = ExtCCPInfoEx.ROAD_CLASS_OTHER;
//            try {
//                if (GpsVirtualReceiver.getInstance().isNaviInfoServiceReady()) {
//                    ExtCCPInfoEx ex = GpsVirtualReceiver.getInstance().getNaviInfoService().getExtCCPInfo();
//                    if (ex != null) {
//                        roadType = ex.getRoadClass();
//                        if (iCount == 100000) {
//                            iCount = 0;
//                        }
//                        if ((iCount++) % 5 == 0) {
//                            LogDog.i("roadType = " + roadType);
//                        }
//                    }
//                }
//            } catch (RemoteException e) {
//                LogDog.e("超速违反判断，获取道路种别", e);
//            }
//
//            // 取得道路基准值
//            if (roadType == ExtCCPInfoEx.ROAD_CLASS_CITY_EXPRESSWAY || roadType == ExtCCPInfoEx.ROAD_CLASS_HIGHWAY) {
//                stdSpeedValue = (short) StandardValue.getInstance().getSpeedMotoWay();
//            } else {
//                stdSpeedValue = (short) StandardValue.getInstance().getSpeedRoadWay();
//            }
            stdSpeedValue = (short)DrivingBehaviorApplication.getmInstance().getmSharedTools().getValue(SharedPreferencedTools.KEY_STD_OVERSPEED);

            if (currentSpeed >= stdSpeedValue) {

                Log.d(TAG, "超速违反开始:超速基准值为" + stdSpeedValue + "当前车速为" + currentSpeed);
                Intent intent = new Intent("TOAST_ACTION");
                String msg = "超速违反开始:超速基准值为" + stdSpeedValue + "当前车速为" + currentSpeed;
                DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
                intent.putExtra("TOAST_MSG", msg);
                mContext.sendBroadcast(intent);

                if (mViolOverSpeedData == null) {
                    mViolOverSpeedData = new ViolOverSpeedData();
                }

                // 设置超速状态，超速违反开始
                this.isViol = true;

                // 记录当前的车辆信息
                mViolOverSpeedData.setStartSeconds(curSec);
                mViolOverSpeedData.setStartMilliSec(curMillSec);
//                super.violData.setStartLatitude(curLatitude);
//                super.violData.setStartLongitude(curLongitude);
                mViolOverSpeedData.setMaxSpeed(currentSpeed);
//                super.violData.setMaxRotationalSpeed(currentRpm);
            }
        }
    }

}
