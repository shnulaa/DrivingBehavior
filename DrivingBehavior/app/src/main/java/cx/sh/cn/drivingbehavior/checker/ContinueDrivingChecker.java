package cx.sh.cn.drivingbehavior.checker;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import cx.sh.cn.drivingbehavior.DrivingBehaviorApplication;
import cx.sh.cn.drivingbehavior.data.ViolContDrivingData;
import cx.sh.cn.drivingbehavior.utils.FileTools;
import cx.sh.cn.drivingbehavior.utils.SharedPreferencedTools;

/**
 * Created by ZengChao on 2016/6/28.
 * <p>
 * 连续驾驶违反检查
 */
public class ContinueDrivingChecker {
    private Context mContext;
    /**
     * 更新开始时间
     */
    private int beginTimeSecUpdate;

    /**
     * 更新开始时间(毫秒)
     */
    private short beginTimeMillUpdate;

    /**
     * 文件临时路径
     */
    private static final String FILE_PATH = Environment
            .getExternalStorageDirectory()
            + "/temp/";
    /**
     * 是否提示用户休息
     */
    private boolean isContDrving = false;
    /**
     * 是否语音播报
     */
    private boolean isBroadcast = false;
    /**
     * 文件操作Flag
     */
    private boolean isRepeat = false;

    private boolean updateTimeFlg = false;

    /**
     * 语音播报Format
     */
    private static String voiceString = "您已连续驾驶%d分钟,请休息%d分钟吧";

    /**
     * 连续驾驶时间基准值
     */
    private int mConDriving;
    /**
     * 连续驾驶后休息时间基准值
     */
    private int mReset;

    private static String TAG = "--ContinueDrivingChecker--";

    /**
     * 构造函数
     */
    public ContinueDrivingChecker(Context context) {

        mContext = context;
        beginTimeSecUpdate = 0;
        beginTimeMillUpdate = 0;

        mConDriving = DrivingBehaviorApplication.getmInstance().getmSharedTools().getValue(SharedPreferencedTools.KEY_STD_CON_MIN);
        mReset = DrivingBehaviorApplication.getmInstance().getmSharedTools().getValue(SharedPreferencedTools.KEY_STD_CON_RELEASE_MIN);
    }

    /**
     * doCheck:判断连续驾驶违反
     *
     * @return ViolContDrivingData
     */
    public ViolContDrivingData doCheck() {

        ViolContDrivingData violContDrivingData = null;

        // 時間未同步完成或ACCOFF狀態下不做任何處理
        if (!DrivingBehaviorApplication.getmInstance().getAccStatus()) {
            return null;
        }
        if (filterUnSyncTime()) {
            return null;
        }

        try {
            // 判斷用戶是否滿足休息時間
            violContDrivingData = checkResumeTime();
            // 休息時間滿足並且前次違反，生成連續運行違反記錄
            if (violContDrivingData != null) {
                return violContDrivingData;
            }

            if (!this.isContDrving) {
                // 取得连续驾驶时间
                int drvingTime = this.getDrivingTime();
                // 获得连续驾驶基准值
                int stdValue = mConDriving;
                Log.d(TAG, "drvingTime = " + drvingTime + "    --stdValue = " + stdValue);

                // 用户驾驶时间 >= 基准值设定时间
                if (drvingTime >= stdValue) {
                    if (!isBroadcast) {
                        // 语音播报
//                        String voice = String.format(voiceString, mConDriving,
//                                mReset);
//                        AsReceiver.getInstance().sendAudioTtsPlayReq(voice, "运动过程中连续驾驶时间违反");
//                        Toast.makeText(mContext,
//                                "您已连续驾驶" + mConDriving + "分钟,请休息" + mReset + "分钟吧", Toast.LENGTH_LONG).show();
                        String msg = "您已连续驾驶" + mConDriving + "分钟,请休息" + mReset + "分钟吧";
                        DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
                        sendToastBroadcast(msg);
                        Log.d(TAG, msg);
                        isBroadcast = true;
                    }
                    this.isContDrving = true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return violContDrivingData;

    }

    /**
     * checkResumeTime: 读取运行状态文件
     *
     * @throws IOException
     */
    private ViolContDrivingData checkResumeTime() throws IOException {
        File file = null;
        ViolContDrivingData violData = null;
        FileInputStream fileInputStream = null;
        DataInputStream dataInputStream = null;

        try {
            file = new File(FILE_PATH, "temp.dat");
            if (!file.exists()) {
                return null;
            }
            fileInputStream = new FileInputStream(file);
            dataInputStream = new DataInputStream(fileInputStream);

            // 读取文件开始时间(秒)
            int bgnSec = dataInputStream.readInt();
            // 读取文件开始时间(毫秒)
            short bgnMillSec = dataInputStream.readShort();
            // 结束时间(秒)
            int endSec = dataInputStream.readInt();
            // 结束时间(毫秒)
            short endMillSec = dataInputStream.readShort();
            // 连续驾驶基准值
            int stdFileValue = dataInputStream.readInt();

            Log.d(TAG, "读取tmp文件:开始时间(秒)" + bgnSec + "开始时间(毫秒)" + bgnMillSec + "结束时间(秒)" + endSec + "结束时间(毫秒)"
                    + endMillSec + "连续驾驶基准值" + stdFileValue);

//            // 休息基准值
//            int stdResumeTime = StandardValue.getInstance().getContDriveParkingMinutes() * 60 * 1000;

            // 取得當前時間
            int curSec = (int) (System.currentTimeMillis() / 1000);
            short curMillSec = (short) (System.currentTimeMillis() % 1000);

            // 计算用户实际休息时间
            int resumeTime = (int) ((curSec - endSec) * 1000 + curMillSec - endMillSec);
            resumeTime = resumeTime / 1000 / 60;

            Log.d(TAG, "resumeTime = " + resumeTime + " mReset = " + mReset);
            // 休息时间不满足
            if (resumeTime < mReset) {
                // 如前次已经违反，则输出提醒信息
                if (stdFileValue != 0) {
//                    String voice = String.format(voiceString, StandardValue.getInstance().getContDrivingMinutes(),
//                            StandardValue.getInstance().getContDriveParkingMinutes());
//                    AsReceiver.getInstance().sendAudioTtsPlayReq(voice, "驾车时间太长啦,我们再休息一会吧");
                    String msg = "驾车时间太长啦,我们再休息一会吧";
                    DrivingBehaviorApplication.getmInstance().getmLogUtils().print(msg);
                    sendToastBroadcast(msg);
                    Log.d(TAG, msg);
                    isBroadcast = true;
                }
                // 继续计算从前次开始的时间
                this.beginTimeSecUpdate = bgnSec;
                this.beginTimeMillUpdate = bgnMillSec;
            } else {
                // 休息时间满足，并且前次已经违反则生成连续驾驶记录
                if (stdFileValue != 0) {
                    violData = new ViolContDrivingData();
                    violData.setStartSec(bgnSec);
                    violData.setStartMilliSec(bgnMillSec);
                    violData.setEndSec(endSec);
                    violData.setEndMilliSec(endMillSec);
                    violData.setStandardValue((short) stdFileValue);
                    Log.d(TAG, "stdFileValue != 0");
                    this.isBroadcast = false;
                    beginTimeSecUpdate = curSec;
                    beginTimeMillUpdate = curMillSec;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            // 关闭文件流
            if (dataInputStream != null) {
                dataInputStream.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (file != null) {
                file.delete();
            }
            /*--[S]--2014.05.26--M:310001917--N:凤哲--[A]---------------------------------*/
            isRepeat = false;
            /*--[E]--2014.05.26--M:310001917--N:凤哲--[A]---------------------------------*/
        }
        return violData;
    }

    /**
     * getDrivingTime: 检查连续驾驶违反
     *
     * @return int 用户运行时间
     */
    private int getDrivingTime() {
        // 当前车载机时间(秒)
        int curDataInfoSec = (int) (System.currentTimeMillis() / 1000);
        // 当前车载机时间(毫秒)
        int curDataInfomil = (int) (System.currentTimeMillis() % 1000);
        // 用户运行时间
        int curRunTimeSec = (int) ((curDataInfoSec - this.beginTimeSecUpdate) * 1000 + curDataInfomil - this.beginTimeMillUpdate);
        int resultTimeSec = curRunTimeSec / 1000 / 60;

        return resultTimeSec;
    }

    public boolean filterUnSyncTime() {
//        boolean ret = false;
//        Calendar calendar = Calendar.getInstance(Locale.CHINA);
//        calendar.set(2013, 2, 1, 12, 0);
//        long time = calendar.getTime().getTime();
//        long thisTime = System.currentTimeMillis();
//
//        if (thisTime <= time) {
//            ret = true;
//        } else {
        if (!updateTimeFlg) {
            beginTimeSecUpdate = (int) (System.currentTimeMillis() / 1000);
            beginTimeMillUpdate = (short) (System.currentTimeMillis() % 1000);
//                LogDog.i(new SimpleDateFormat("同步后的时间:yyyy/MM/dd HH:mm:ss.SSS", Locale.CHINESE).format(new Date())
//                        + " beginTimeSecUpdate = " + beginTimeSecUpdate + " beginTimeMillUpdate = "
//                        + beginTimeMillUpdate);
            updateTimeFlg = true;
        }
//        }
        return false;
    }

    /**
     * checkWhenAccOff: 当ACC状态为1(熄火)时调用
     */
    public void checkWhenAccOff() {

        try {
            /*--[S]--2014.05.26--M:310001917--N:凤哲--[M]---------------------------------*/
            Log.d(TAG, "当ACCOFF时调用:isRepeat=" + isRepeat + "(false：write)");
            if (filterUnSyncTime() || isRepeat) {
                /*--[E]--2014.05.26--M:310001917--N:凤哲--[M]---------------------------------*/
                updateTimeFlg = false;
                return;
            }
            updateTimeFlg = false;
            writeAccOffFile();
            /*--[S]--2014.05.26--M:310001917--N:凤哲--[A]---------------------------------*/
            isRepeat = true;
            /*--[E]--2014.05.26--M:310001917--N:凤哲--[A]---------------------------------*/
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    /**
     * writeFile: 连续驾驶ACCOFF写文件
     *
     * @throws IOException
     */
    public void writeAccOffFile() throws IOException {

        // 取得当前系统时间
        int accOffSec = (int) (System.currentTimeMillis() / 1000);
        short accOffMil = (short) (System.currentTimeMillis() % 1000);

        FileOutputStream fileOutputStream = null;
        DataOutputStream dataOutputStream = null;

        FileTools.mkFolder(FILE_PATH);
//        File foler = new File(FILE_PATH);
//        if (!foler.exists()) {
//            foler.mkdirs();
//        }

        // 写文件
        File file = new File(FILE_PATH, "temp.dat");

        try {
            fileOutputStream = new FileOutputStream(file);
            dataOutputStream = new DataOutputStream(fileOutputStream);

            dataOutputStream.writeInt(this.beginTimeSecUpdate);
            dataOutputStream.writeShort(this.beginTimeMillUpdate);

            dataOutputStream.writeInt((int) accOffSec);
            dataOutputStream.writeShort(accOffMil);

            // 判断当前是否违反
            if (this.isContDrving) {
                // 写入当前基准值
                dataOutputStream.writeInt(mConDriving);
            } else {
                // 写入0
                dataOutputStream.writeInt(0);
            }
            Log.d(TAG, "AccOff写入temp文件成功");
            DrivingBehaviorApplication.getmInstance().getmLogUtils().print("AccOff写入temp文件成功");
        } catch (Exception e) {
            Log.d(TAG, "写入连续驾驶文件", e);
        } finally {
            FileTools.chmod777(FILE_PATH + "temp.dat");
            // 初始化
            isContDrving = false;

            if (dataOutputStream != null) {
                dataOutputStream.close();
            }

            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }

    private void sendToastBroadcast(String msg) {
        Intent intent = new Intent("TOAST_ACTION");
        intent.putExtra("TOAST_MSG", msg);
        mContext.sendBroadcast(intent);
    }
}
