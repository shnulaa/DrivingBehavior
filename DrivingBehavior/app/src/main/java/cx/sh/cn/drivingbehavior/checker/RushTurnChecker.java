package cx.sh.cn.drivingbehavior.checker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import cx.sh.cn.drivingbehavior.DrivingBehaviorApplication;
import cx.sh.cn.drivingbehavior.utils.LogUtils;
import cx.sh.cn.drivingbehavior.utils.SharedPreferencedTools;

/**
 * Created by ZengChao on 2016/7/1.
 *
 * 急转弯驾驶违反检查
 *
 */
public class RushTurnChecker {

    private static final String TAG = "--RushTurnChecker--";

    private Context mContext;
    /**
     * 连续记录条数
     */
    private static final int MAXITEM = 30;

    /**
     * 当前是否急转弯
     */
    private boolean isRushTurn = false;

    /**
     * 获得当前基准值(角度)
     */
//    private int stdAngle = 0;
    private int stdAngle;

    /**
     * 获得当前基准值(面积)
     */
    private int stdArea = 0;


    /**
     * 构造函数
     */
    public RushTurnChecker(Context context) {
        mContext = context;
        stdAngle = DrivingBehaviorApplication.getmInstance().getmSharedTools().getValue(SharedPreferencedTools.KEY_STD_RUSH_TURN);
    }

    /**
     * doCheck: 判断急转弯违反
     *
     */
    public void doCheck(float firstAngleZ, float lastAngleZ) {

        int maxSpeed = 0;

//        if (SafeService.getInstance().getObdGenerator() != null) {
//            ObdData curObdData = (ObdData) SafeService.getInstance().getObdGenerator().getCurData();
//            maxSpeed = curObdData.getSpeed();
//        }

//        List<SensorData> listSensorDatas = SafeService.getInstance().getSensorGenerator().getHisSensorDataList();

        // 判断记录条数不足30条
        if (lastAngleZ == Float.MAX_VALUE) {
            DrivingBehaviorApplication.getmInstance().getmLogUtils().print("急转弯驾驶违反检查---记录条数不足30条...");
            return;
        }

//        // 计算面积
//        double curArea = 0;
//
//        for (double tmp : SafeService.getInstance().getSensorGenerator().getSensorXAreaTurn()) {
//            curArea += tmp;
//        }
//
//        // 获取角度差
//        int curAngleDValue = listSensorDatas.get(listSensorDatas.size() - 1).getGpsData().getHeading()
//                - listSensorDatas.get(0).getGpsData().getHeading();
//
//        if (curAngleDValue > 1800) {
//            curAngleDValue -= 3600;
//        }
//
//        if (curAngleDValue < -1800) {
//            curAngleDValue += 3600;
//        }
//
//        curAngleDValue = Math.abs(curAngleDValue);

        // 计算Z轴角度差(连续30条数据最大角度差)
        float diffAngle = Math.abs(lastAngleZ - firstAngleZ);
//        Log.d(TAG, "急转弯判断，角度差为："+diffAngle);

//        DrivingBehaviorApplication.getmInstance().getmLogUtils().print("急转弯判断，角度差为："+diffAngle);

        // 判断当前是否为急转弯状态
        if (!this.isRushTurn) {
//            // 获得基准值
//            this.stdArea = StandardValue.getInstance().getTurnG();
//
//            // 判断面积/角度
//            // if ((curArea >= this.stdArea && curAngleDValue >= this.stdAngle)
//            // || (curArea <= -this.stdArea && curAngleDValue >= this.stdAngle))
//            // {
//            if (curArea >= this.stdArea || curArea <= -this.stdArea) {
            if (diffAngle >= stdAngle) {

                // 获得最高车速和最大加速度
                this.isRushTurn = true;
//                short maxSensorX = 0;
//                LogDog.i("急转弯违反开始,基准值为：" + stdArea);

                String msg = "急转弯违反开始,转弯角度为：" + diffAngle;
                DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
                Log.d(TAG, msg);
                Intent intent = new Intent("TOAST_ACTION");
                intent.putExtra("TOAST_MSG", msg);
                mContext.sendBroadcast(intent);

//                for (SensorData sensorData : listSensorDatas) {
//                    // 更新绝对值！！！
//                    if (Math.abs(sensorData.getSensorX()) > maxSensorX) {
//                        maxSensorX = (short) Math.abs(sensorData.getSensorX());
//                    }
//                }
//                if (SafeService.getInstance().getObdGenerator() != null) {
//                    ObdData curObdData = (ObdData) SafeService.getInstance().getObdGenerator().getCurData();
//                    if (curObdData.getSpeed() > maxSpeed) {
//                        maxSpeed = curObdData.getSpeed();
//                    }
//                }
//
//                if (super.violData == null) {
//
//                    super.violData = new ViolRushTurnData();
//                }
//
//                // 设置急转弯违反开始时间/经纬度
//                super.violData.setStartSec((int) listSensorDatas.get(0).getSecondTicks());
//                super.violData.setStartMilliSec((short) listSensorDatas.get(0).getMillisecondTicks());
//                super.violData.setLatitude(listSensorDatas.get(0).getGpsData().getLatitude());
//                super.violData.setLongitude(listSensorDatas.get(0).getGpsData().getLongitude());
//                super.violData.setMaxSpeed((short) maxSpeed);
//                super.violData.setMaxAcceleration(maxSensorX);

            }
        } else {

//            // 判断左/右急转
//            if (curArea >= this.stdArea || curArea <= -this.stdArea) {
//
//                // 更新最高SensorX
//                if (data.getSensorX() > super.violData.getMaxAcceleration()) {
//                    super.violData.setMaxAcceleration(data.getSensorX());
//                }
//
//            } else {
//
//                // 生成急转弯违反
//                super.addHisDataFileMng(super.violData);
//                LogDog.i("急转弯违反结束,基准值为：" + stdArea);
//
//                // 初始化
//                this.isRushTurn = false;
//                super.violData = null;
//
//            }
            if (diffAngle < stdAngle) {
                String msg = "急转弯违反结束,基准值为：" + stdAngle;
                DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
                Log.d(TAG, msg);
                Intent intent = new Intent("TOAST_ACTION");
                intent.putExtra("TOAST_MSG", msg);
                mContext.sendBroadcast(intent);
                // 初始化
                this.isRushTurn = false;
            }
        }
    }


}
