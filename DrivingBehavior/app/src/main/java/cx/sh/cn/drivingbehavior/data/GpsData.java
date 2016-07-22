package cx.sh.cn.drivingbehavior.data;

import java.io.Serializable;

/**
 * Created by ZengChao on 2016/6/30.
 */
public class GpsData implements Serializable {
    private double longitude;

    private double latitude;
    // 时间
    private long time;
    // 时间--秒
    private long seconds;
    // 时间--毫秒
    private long milliseconds;
    // 精确度
    private float accuracy;
    // 方向
    private float bearing;
    // 速度
    private float speed;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }
}
