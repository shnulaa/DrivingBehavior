package cx.sh.cn.drivingbehavior.service;

/**
 * Created by ZengChao on 2016/6/27.
 */

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import cx.sh.cn.drivingbehavior.utils.AccelerometerListener;
import cx.sh.cn.drivingbehavior.utils.GpsServiceListener;
import cx.sh.cn.drivingbehavior.utils.GyroscopeListener;

public class GetDataService extends Service {

    private static final long minTime = 1 * 1000;
    private static final float minDistance = /*0.00001F*/0F;
    String tag = this.toString();
    private LocationManager locationManager;
    private LocationListener locationListener;

    private SensorManager sensorManager;
    // 陀螺仪传感器
    private Sensor gyroscopeSensor;
    private GyroscopeListener gyroscopeListener;
    // 加速度传感器
    private Sensor accelerometerSensor;
    private AccelerometerListener accListener;

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GpsServiceListener(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 陀螺仪传感器
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gyroscopeListener = new GyroscopeListener(getApplicationContext(), gyroscopeSensor);
        sensorManager.registerListener(gyroscopeListener, gyroscopeSensor,
                SensorManager.SENSOR_DELAY_GAME);
        // 加速度传感器
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accListener = new AccelerometerListener(getApplicationContext());
        sensorManager.registerListener(accListener, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);


        // android通过criteria选择合适的地理位置服务
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);// 高精度
        criteria.setAltitudeRequired(true);// 设置不需要获取海拔方向数据
        criteria.setBearingRequired(true);// 设置不需要获取方位数据
        criteria.setCostAllowed(true);// 设置允许产生资费
        criteria.setPowerRequirement(Criteria.POWER_HIGH);// 高功耗
        String provider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(provider,
                minTime, minDistance, locationListener);
        Log.v(tag, "GetDataService started");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null && locationListener != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationManager.removeUpdates(locationListener);
        }
        sensorManager.unregisterListener(gyroscopeListener, gyroscopeSensor);
        Log.v(tag, "GetDataService ended");
    }

    @Override
    public IBinder onBind(Intent arg0) {

        return null;
    }
}
