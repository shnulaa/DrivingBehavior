package cx.sh.cn.drivingbehavior.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import cx.sh.cn.drivingbehavior.DrivingBehaviorApplication;
import cx.sh.cn.drivingbehavior.checker.RushTurnChecker;
import cx.sh.cn.drivingbehavior.data.GyroscopeData;

/**
 * Created by ZengChao on 2016/6/28.
 */
public class GyroscopeListener implements SensorEventListener {
    private Context context;
    private static final String TAG = "--GyroscopeListener--";
    // 将纳秒转化为秒
    private static final float NS2S = 1.0f / 1000000000.0f;
    private float timestamp;
    private float angle[] = new float[3];
    private Sensor gyroscopeSensor;
    private GyroscopeData gyroscopeData;

    public GyroscopeListener() {
        super();
    }
    private RushTurnChecker mRushTurnChecker;

    public GyroscopeListener(Context context, Sensor gyroscopeSensor) {
        super();
        this.context = context;
        this.gyroscopeSensor = gyroscopeSensor;
        this.gyroscopeData = new GyroscopeData();
        this.mRushTurnChecker = new RushTurnChecker(context);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        // 陀螺仪传感器
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            // 从 x、y、z 轴的正向位置观看处于原始方位的设备，如果设备逆时针旋转，将会收到正值；否则，为负值
            if (timestamp != 0) {
                // 得到两次检测到手机旋转的时间差（纳秒），并将其转化为秒
                final float dT = (event.timestamp - timestamp) * NS2S;
                // 将手机在各个轴上的旋转角度相加，即可得到当前位置相对于初始位置的旋转弧度
                angle[0] += event.values[0] * dT;
                angle[1] += event.values[1] * dT;
                angle[2] += event.values[2] * dT;
                // 将弧度转化为角度
                float anglex = (float) Math.toDegrees(angle[0]);
                float angley = (float) Math.toDegrees(angle[1]);
                float anglez = (float) Math.toDegrees(angle[2]);

//                System.out.println("anglex------------>" + anglex);
//                System.out.println("angley------------>" + angley);
//                System.out.println("anglez------------>" + anglez);

//                DrivingBehaviorApplication.getmInstance().setmAngleZList(gyroscopeData.addToAngleZList(anglez));
                gyroscopeData.addToAngleZList(anglez);
                // ACCON的场合检查急转弯
                if (DrivingBehaviorApplication.getmInstance().getAccStatus()) {
                    mRushTurnChecker.doCheck(gyroscopeData.getFirstAngleZ(),
                            gyroscopeData.getLastAngleZ());
                }
//
//                System.out.println("gyroscopeSensor.getMinDelay()----------->"
//                        + gyroscopeSensor.getMinDelay());
            }
            // 将当前时间赋值给timestamp
            timestamp = event.timestamp;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
