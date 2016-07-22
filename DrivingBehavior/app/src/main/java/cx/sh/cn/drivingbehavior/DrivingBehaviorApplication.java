package cx.sh.cn.drivingbehavior;

import android.app.Application;

import java.util.List;
import java.util.Queue;

import cx.sh.cn.drivingbehavior.utils.LogUtils;
import cx.sh.cn.drivingbehavior.utils.SharedPreferencedTools;

/**
 * Created by ZengChao on 2016/6/29.
 */
public class DrivingBehaviorApplication extends Application {
    private static DrivingBehaviorApplication mInstance;
    public boolean mAccStatus = false;
    public boolean mHandbrake = false;
    public Queue<Float> mAngleZList;
    public LogUtils mLogUtils;
    public SharedPreferencedTools mSharedTools;

    public DrivingBehaviorApplication () {

    }

    public static DrivingBehaviorApplication getmInstance() {
        if (mInstance == null) {
            mInstance = new DrivingBehaviorApplication();
        }
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }


    public boolean getAccStatus() {
        return mAccStatus;
    }

    public void setmAccStatus(boolean status) {
        this.mAccStatus = status;
    }

    public boolean ismHandbrake() {
        return mHandbrake;
    }

    public void setmHandbrake(boolean mHandbrake) {
        this.mHandbrake = mHandbrake;
    }

//    public Queue<Float> getmAngleZList() {
//        return mAngleZList;
//    }
//
//    public void setmAngleZList(Queue<Float> mAngleZList) {
//        this.mAngleZList = mAngleZList;
//    }

    public LogUtils getmLogUtils() {
        return mLogUtils;
    }

    public void setmLogUtils(LogUtils mLogUtils) {
        this.mLogUtils = mLogUtils;
    }

    public SharedPreferencedTools getmSharedTools() {
        return mSharedTools;
    }

    public void setmSharedTools(SharedPreferencedTools mSharedTools) {
        this.mSharedTools = mSharedTools;
    }
}
