package cx.sh.cn.drivingbehavior.checker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cx.sh.cn.drivingbehavior.DrivingBehaviorApplication;
import cx.sh.cn.drivingbehavior.data.AccelerometerData;
import cx.sh.cn.drivingbehavior.utils.SharedPreferencedTools;

/**
 * Created by ZengChao on 2016/7/21.
 */
public class ShakeChecker {

    /**
     * 纵震动状态
     */
    private boolean isViol = false;

    /**
     * 纵震动的标准值
     */
    private int stdValue = 0;

    private Context mContext;

    private static final String TAG = "--ShakeChecker--";

    /**
     * 构造函数
     */
    public ShakeChecker(Context context) {
        mContext = context;
    }

    /**
     * doCheck: 纵震动违反判断
     *
     * @param data
     *            SensorData
     */
    public void doCheck(AccelerometerData data) {

        // G-sensorZ轴最大值
        float maxZ = 0;

        // G-sensorZ轴最小值
        float minZ = 0;

        // 最高速度
        int maxSpeed = 0;

//        if (SafeService.getInstance().getObdGenerator() != null) {
//            ObdData curObdData = (ObdData) SafeService.getInstance().getObdGenerator().getCurData();
//            maxSpeed = curObdData.getSpeed();
//        }

        // 最大加速度
        float maxAcc = 0;

        // 车辆信息历史记录
//        List<SensorData> hisVehicleDataList = SafeService.getInstance().getSensorGenerator().getHisSensorDataList();

//        // 读取3条连续数据
//        if (hisVehicleDataList.size() < 3) {
//            return;
//        }
        //
        if (data.getValueZList().size() < 12) {
            return;
        }

//        maxZ = hisVehicleDataList.get(0).getSensorZ();
//        minZ = hisVehicleDataList.get(0).getSensorZ();
//
//        // G-sensorZ轴最大最小差的绝对值
//        maxAcc =  Math.abs(maxZ - minZ);

//        // 读取3条连续数据
//        for (int i = 0; i < 3; i++) {
//
//            // 取得3条中G-sensorZ轴最大值
//            if (hisVehicleDataList.get(i).getSensorZ() > maxZ) {
//                maxZ = hisVehicleDataList.get(i).getSensorZ();
//            }
//
//            // 取得3条中G-sensorZ轴最小值
//            if (hisVehicleDataList.get(i).getSensorZ() < minZ) {
//                minZ = hisVehicleDataList.get(i).getSensorZ();
//            }
//
//            // 取得3条中最大加速度
//            if ((float) Math.abs(maxZ - minZ) > maxAcc) {
//                maxAcc = (float) Math.abs(maxZ - minZ);
//            }
//
//        }

        float minValueZ = data.getValueZList().element();
        float maxValueZ = data.getValueZList().element();
        for (float f : data.getValueZList()) {
            if (f < minValueZ) {
                minValueZ = f;
            }
            if (f > maxValueZ) {
                maxValueZ = f;
            }
        }
        maxZ = maxValueZ;
        minZ = minValueZ;
        maxAcc =  Math.abs(maxZ - minZ);

        // 当前不是纵震动
        if (!isViol) {

//            violStart(data, maxAcc, maxSpeed);
            violStart(maxAcc);

            // 当前是纵震动
        } else {

            // 最大最小差大于标准值，标准违反
            if (maxAcc >= stdValue) {

//                // 更新最高速度
//                if ((short) Math.abs(data.getSensorZ() - minZ) > maxAcc) {
//                    super.violData.setMaxSpeed((short) Math.abs(data.getSensorZ() - minZ));
//                }
//
//                if (SafeService.getInstance().getObdGenerator() != null) {
//                    ObdData curObdData = (ObdData) SafeService.getInstance().getObdGenerator().getCurData();
//                    if (curObdData.getSpeed() > violData.getMaxSpeed()) {
//                        violData.setMaxSpeed((short) curObdData.getSpeed());
//                    }
//                }

            } else {

//                // 生成纵震动违反记录
//                super.addHisDataFileMng(super.violData);
//                LogDog.i("纵震动违反结束");
                // 初始化信息
                isViol = false;
//                super.violData = null;

                String msg = "纵震动违反结束!" ;
                DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
                Log.d(TAG, msg);
                Intent intent = new Intent("TOAST_ACTION");
                intent.putExtra("TOAST_MSG", msg);
                mContext.sendBroadcast(intent);
            }
        }
    }

    /**
     * violStart: 判断当前是否为纵震动违反
     *
     * @param maxAcc
     *            short
     */
    private void violStart(/*SensorData data, short*/ float maxAcc/*, int maxSpeed*/) {

        // 取得纵震动的标准值
//        stdValue = StandardValue.getInstance().getShake();
        stdValue = DrivingBehaviorApplication.getmInstance().getmSharedTools().getValue(SharedPreferencedTools.KEY_STD_SHAKE);

        // 最大最小差大于标准值，标准违反
        if (maxAcc >= stdValue) {

            // 违反开始
            this.isViol = true;

//            // 纵震动违反信息
//            super.violData = new ViolShakeData();
//
//            // 开始时间(秒)
//            super.violData.setStartSec((int) data.getSecondTicks());
//
//            // 开始时间(毫秒)
//            super.violData.setStartMilliSec((short) data.getMillisecondTicks());
//
//            // 纬度
//            super.violData.setLatitude(data.getGpsData().getLatitude());
//
//            // 经度
//            super.violData.setLongitude(data.getGpsData().getLongitude());
//
//            // 最高车速
//            super.violData.setMaxSpeed((short) maxSpeed);
//
//            // 最大加速度
//            super.violData.setMaxAcceleration(maxAcc);
//
//            LogDog.i("纵震动违反开始!");
            String msg = "纵震动违反开始!" ;
            DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
            Log.d(TAG, msg);
            Intent intent = new Intent("TOAST_ACTION");
            intent.putExtra("TOAST_MSG", msg);
            mContext.sendBroadcast(intent);

        }
    }


}
