package cx.sh.cn.drivingbehavior.utils;

/**
 * Created by ZengChao on 2016/6/27.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import cx.sh.cn.drivingbehavior.data.GpsData;

public class GpsServiceListener implements LocationListener {

    private Context context;
    private static final String tag = "GPSSERVICELISTENER";
    private static final float minAccuracyMeters = 35;
    public int gpsCurrentStatus;
    private static String TAG = "--GpsServiceListener--";

    public GpsServiceListener() {
        super();
    }

    public GpsServiceListener(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {
            if (location.hasAccuracy() && location.getAccuracy() <= minAccuracyMeters) {
                //获取参数，并post到服务器端
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(location.getTime());
                StringBuffer sb = new StringBuffer();
                sb.append("经度=").append(location.getLongitude());
                sb.append("\n纬度=").append(location.getLatitude());
//                sb.append("\n系统当前时间=").append(sdf.format(new Date())); // 经测试和下面取得的时间完全一样
                sb.append("\n时间=").append(sdf.format(calendar.getTime()));
                sb.append("\n精确度=").append(location.getAccuracy());
                sb.append("\n方向=").append(location.getBearing());
                sb.append("\n速度=").append(location.getSpeed());
                Log.v(tag, sb.toString());

                GpsData gpsData = new GpsData();
                gpsData.setAccuracy(location.getAccuracy());
                gpsData.setBearing(location.getBearing());
                gpsData.setLatitude(location.getLatitude());
                gpsData.setLongitude(location.getLongitude());
                gpsData.setSpeed(location.getSpeed());
                gpsData.setTime(location.getTime());
                gpsData.setSeconds(location.getTime() / 1000);
                gpsData.setMilliseconds(location.getTime() % 1000);

                sendToActivity(gpsData);

//                try {
//                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("longi", location.getLongitude() + "");
//                    map.put("lati", location.getLatitude() + "");
//                    map.put("time", sdf.format(new Date()));
//                    String url = HttpUtil.BASE_URL + "coords.do?method=addCoords";
//                    HttpUtil.postRequest(url, map);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }
    }

    //发送广播，提示更新界面
    private void sendToActivity(GpsData gpsData) {
        Intent intent = new Intent();
        intent.putExtra("newLoca", gpsData);
        intent.setAction("NEW LOCATION SENT");
        context.sendBroadcast(intent);
    }

    /**
     * GPS禁用时触发
     */
    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub
        Log.i(TAG, "当前GPS禁用");
        Toast.makeText(context, "当前GPS禁用", Toast.LENGTH_SHORT).show();
    }

    /**
     * GPS开启时触发
     */
    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub

        Log.i(TAG, "当前GPS开启");
        Toast.makeText(context, "当前GPS开启", Toast.LENGTH_SHORT).show();
    }

    /**
     * GPS状态变化时触发
     */
    public void onStatusChanged(String provider, int status, Bundle extras) {
        gpsCurrentStatus = status;
        switch (status) {
            //GPS状态为可见时
            case LocationProvider.AVAILABLE:
                Log.i(TAG, "当前GPS状态为可见状态");
                Toast.makeText(context, "当前GPS状态为可见状态", Toast.LENGTH_SHORT).show();
                break;
            //GPS状态为服务区外时
            case LocationProvider.OUT_OF_SERVICE:
                Log.i(TAG, "当前GPS状态为服务区外状态");
                Toast.makeText(context, "当前GPS状态为服务区外状态", Toast.LENGTH_SHORT).show();
                break;
            //GPS状态为暂停服务时
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.i(TAG, "当前GPS状态为暂停服务状态");
                Toast.makeText(context, "当前GPS状态为暂停服务状态", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}

