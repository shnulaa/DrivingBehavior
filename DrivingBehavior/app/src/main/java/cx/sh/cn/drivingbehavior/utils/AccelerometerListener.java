package cx.sh.cn.drivingbehavior.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import cx.sh.cn.drivingbehavior.DrivingBehaviorApplication;
import cx.sh.cn.drivingbehavior.checker.ShakeChecker;
import cx.sh.cn.drivingbehavior.data.AccelerometerData;

/**
 * Created by ZengChao on 2016/7/21.
 */
// G-Sensor
public class AccelerometerListener  implements SensorEventListener {

    private Context mContext;

    private AccelerometerData mAccData;

    private ShakeChecker mShakeChecker;

    public AccelerometerListener(Context context) {
        mContext = context;
        mAccData = new AccelerometerData();
        mShakeChecker = new ShakeChecker(context);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float xValue = event.values[0];
            float yValue = event.values[1];
            float zValue = event.values[2];

//            System.out.println("xValue------------>" + xValue);
//            System.out.println("yValue------------>" + yValue);
//            System.out.println("zValue------------>" + zValue);

            mAccData.addToValueZList(zValue);
            // ACCON的场合检查急转弯
            if (DrivingBehaviorApplication.getmInstance().getAccStatus()) {
                mShakeChecker.doCheck(mAccData);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
