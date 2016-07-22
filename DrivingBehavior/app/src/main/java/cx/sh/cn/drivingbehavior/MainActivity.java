package cx.sh.cn.drivingbehavior;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cx.sh.cn.drivingbehavior.checker.ContinueDrivingChecker;
import cx.sh.cn.drivingbehavior.checker.HandBrakeChecker;
import cx.sh.cn.drivingbehavior.checker.IdlingChecker;
import cx.sh.cn.drivingbehavior.checker.OverSpeedChecker;
import cx.sh.cn.drivingbehavior.checker.RushAccChecker;
import cx.sh.cn.drivingbehavior.checker.RushLaunchChecker;
import cx.sh.cn.drivingbehavior.checker.RushSpeedDownChecker;
import cx.sh.cn.drivingbehavior.data.GpsData;
import cx.sh.cn.drivingbehavior.service.GetDataService;
import cx.sh.cn.drivingbehavior.utils.LogUtils;
import cx.sh.cn.drivingbehavior.utils.SharedPreferencedTools;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "--MainActivity--";
    private Button mAccOn;
    private Button mAccOff;
    private Button mEditBt;
    private Button mConfirmBt;
    private CheckBox mHandbrake;

    private EditText mStdConMin;
    private EditText mStdConReleaseMin;
    private EditText mStdIdingMin;
    private EditText mStdOverSpeed;
    private EditText mStdRushTurn;
    private EditText mStdRushAcc;
    private EditText mStdRushLaunch;
    private EditText mStdRushSpeedDown;
    private EditText mShake;

    private Intent mServiceIntent;
    private LocationReceiver mReceiver;
    private boolean mIsAccOn = false;
    // true: 手刹放下  false:手刹拉起
    private boolean mIsHandbrake = false;
    private ContinueDrivingChecker mConDrivingChecker;
    private OverSpeedChecker mOverSpeedChecker;
    private Timer mCheckTimer;
    private RushAccChecker mRushAccChecker;
    private RushLaunchChecker mRushLaunchChecker;
    private RushSpeedDownChecker mSpeedDownChecker;
    private IdlingChecker mIdlingChecker;
    private HandBrakeChecker mHandBrakeChecker;

    private TextToSpeech mTextToSpeech = null;
    private List<String> mMsgList = new ArrayList<String>();
    private HashMap<String, String> map = new HashMap<String, String>();
    private SharedPreferencedTools mSharedTools;

    private GpsData gpsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtils logUtils = new LogUtils();
        DrivingBehaviorApplication.getmInstance().setmLogUtils(logUtils);

        mSharedTools = new SharedPreferencedTools(this);
        DrivingBehaviorApplication.getmInstance().setmSharedTools(mSharedTools);

        initControls();
        setEnableFromConfrimBtOrEditBt(false);

        DrivingBehaviorApplication.getmInstance().getmLogUtils().print("画面启动开始...onCreate");
        // 启动GPS服务
        mServiceIntent = new Intent(MainActivity.this, GetDataService.class);
        startService(mServiceIntent);

        mReceiver = new LocationReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("NEW LOCATION SENT");
        intentFilter.addAction("TOAST_ACTION");
        registerReceiver(mReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DrivingBehaviorApplication.getmInstance().getmLogUtils().print("画面关闭...onDestroy");
        // 停止GPS服务
        stopService(mServiceIntent);
        unregisterReceiver(mReceiver);
        if (mCheckTimer != null) {
            mCheckTimer.cancel();
            mCheckTimer = null;
        }

        try {
            DrivingBehaviorApplication.getmInstance().getmLogUtils().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initControls() {
        mAccOn = (Button) findViewById(R.id.acc_on);
        mAccOff = (Button) findViewById(R.id.acc_off);
        mEditBt = (Button) findViewById(R.id.editButton);
        mConfirmBt = (Button) findViewById(R.id.confirmButton);
        mAccOn.setOnClickListener(this);
        mAccOff.setOnClickListener(this);
        mConfirmBt.setOnClickListener(this);
        mEditBt.setOnClickListener(this);

        mStdConMin = (EditText) findViewById(R.id.stdConMin);
        mStdConReleaseMin = (EditText) findViewById(R.id.stdConReleaseMin);
        mStdIdingMin = (EditText) findViewById(R.id.stdIdingMin);
        mStdOverSpeed = (EditText) findViewById(R.id.stdOverSpeed);
        mStdRushAcc = (EditText) findViewById(R.id.stdRushAcc);
        mStdRushLaunch = (EditText) findViewById(R.id.stdRushLaunch);
        mStdRushSpeedDown = (EditText) findViewById(R.id.stdRushSpeedDown);
        mStdRushTurn = (EditText) findViewById(R.id.stdRushTurn);
        mShake = (EditText) findViewById(R.id.stdShake);

        mStdConMin.setText((mSharedTools.getValue(SharedPreferencedTools.KEY_STD_CON_MIN) == Integer.MIN_VALUE ?
                String.valueOf(2) : String.valueOf(mSharedTools.getValue(SharedPreferencedTools.KEY_STD_CON_MIN))));
        mStdConReleaseMin.setText((mSharedTools.getValue(SharedPreferencedTools.KEY_STD_CON_RELEASE_MIN) == Integer.MIN_VALUE ?
                String.valueOf(1) : String.valueOf(mSharedTools.getValue(SharedPreferencedTools.KEY_STD_CON_RELEASE_MIN))));
        mStdIdingMin.setText((mSharedTools.getValue(SharedPreferencedTools.KEY_STD_IDING) == Integer.MIN_VALUE ?
                String.valueOf(1) : String.valueOf(mSharedTools.getValue(SharedPreferencedTools.KEY_STD_IDING))));
        mStdOverSpeed.setText((mSharedTools.getValue(SharedPreferencedTools.KEY_STD_OVERSPEED) == Integer.MIN_VALUE ?
                String.valueOf(2) : String.valueOf(mSharedTools.getValue(SharedPreferencedTools.KEY_STD_OVERSPEED))));
        mStdRushAcc.setText((mSharedTools.getValue(SharedPreferencedTools.KEY_STD_RUSH_ACC) == Integer.MIN_VALUE ?
                String.valueOf(2) : String.valueOf(mSharedTools.getValue(SharedPreferencedTools.KEY_STD_RUSH_ACC))));
        mStdRushLaunch.setText((mSharedTools.getValue(SharedPreferencedTools.KEY_STD_RUSH_LAUNCH) == Integer.MIN_VALUE ?
                String.valueOf(2) : String.valueOf(mSharedTools.getValue(SharedPreferencedTools.KEY_STD_RUSH_LAUNCH))));
        mStdRushSpeedDown.setText((mSharedTools.getValue(SharedPreferencedTools.KEY_STD_RUSH_SPEED_DOWN) == Integer.MIN_VALUE ?
                String.valueOf(2) : String.valueOf(mSharedTools.getValue(SharedPreferencedTools.KEY_STD_RUSH_SPEED_DOWN))));
        mStdRushTurn.setText((mSharedTools.getValue(SharedPreferencedTools.KEY_STD_RUSH_TURN) == Integer.MIN_VALUE ?
                String.valueOf(75) : String.valueOf(mSharedTools.getValue(SharedPreferencedTools.KEY_STD_RUSH_TURN))));
        mShake.setText((mSharedTools.getValue(SharedPreferencedTools.KEY_STD_SHAKE) == Integer.MIN_VALUE ?
                String.valueOf(3) : String.valueOf(mSharedTools.getValue(SharedPreferencedTools.KEY_STD_SHAKE))));

        mConfirmBt.setEnabled(true);
        mEditBt.setEnabled(false);

        mHandbrake = (CheckBox) findViewById(R.id.handbrake);
        mHandbrake.setChecked(mIsHandbrake);
        DrivingBehaviorApplication.getmInstance().setmHandbrake(mIsHandbrake);
        mHandbrake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsHandbrake = isChecked;
                DrivingBehaviorApplication.getmInstance().setmHandbrake(mIsHandbrake);
            }
        });

        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
                    //设置朗读语言
                    int supported = mTextToSpeech.setLanguage(Locale.CHINESE);
                    if ((supported != TextToSpeech.LANG_AVAILABLE) && (supported != TextToSpeech.LANG_COUNTRY_AVAILABLE)) {
                        Toast.makeText(MainActivity.this, "不支持当前语言！", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        mTextToSpeech.setOnUtteranceProgressListener(new TtsUtteranceListener());

        mConDrivingChecker = new ContinueDrivingChecker(MainActivity.this);
        mOverSpeedChecker = new OverSpeedChecker(MainActivity.this);
        mRushAccChecker = new RushAccChecker(MainActivity.this);
        mRushLaunchChecker = new RushLaunchChecker(MainActivity.this);
        mSpeedDownChecker = new RushSpeedDownChecker(MainActivity.this);
        mIdlingChecker = new IdlingChecker(MainActivity.this);
        mHandBrakeChecker = new HandBrakeChecker(MainActivity.this);
    }

    public class TtsUtteranceListener extends UtteranceProgressListener {
        @Override
        public void onDone(String utteranceId) {
            Log.d(TAG, "----onDone----");
            mMsgList.remove(0);
            notifyStartTTS();
        }

        @Override
        public void onStart(String utteranceId) {
            Log.d(TAG, "----onStart----");
        }

        @Override
        public void onStop(String utteranceId, boolean interrupted) {
            super.onStop(utteranceId, interrupted);
            Log.d(TAG, "----onStop----");
        }

        @Override
        public void onError(String utteranceId, int errorCode) {
            super.onError(utteranceId, errorCode);
            Log.d(TAG, "----onError----errorCode:" + errorCode);
        }

        @Override
        public void onError(String utteranceId) {
            Log.d(TAG, "----onError----");
        }
    }

    /*
     *设置界面控件能否可以点击
     *
     * confirmBt true: 确定按钮按下时  false：编辑按钮按下时
     */
    private void setEnableFromConfrimBtOrEditBt(boolean confirmBt) {

        mStdConMin.setEnabled(!confirmBt);
        mStdConReleaseMin.setEnabled(!confirmBt);
        mStdIdingMin.setEnabled(!confirmBt);
        mStdOverSpeed.setEnabled(!confirmBt);
        mStdRushTurn.setEnabled(!confirmBt);
        mStdRushAcc.setEnabled(!confirmBt);
        mStdRushLaunch.setEnabled(!confirmBt);
        mStdRushSpeedDown.setEnabled(!confirmBt);
        mShake.setEnabled(!confirmBt);

        mAccOn.setEnabled(false);
        mAccOff.setEnabled(false);
        mHandbrake.setEnabled(false);

        if (confirmBt) {
            mConfirmBt.setEnabled(false);
            mEditBt.setEnabled(true);

            mAccOn.setEnabled(true);
            mAccOff.setEnabled(false);
            mHandbrake.setEnabled(true);
            mIsAccOn = false;
            DrivingBehaviorApplication.getmInstance().setmAccStatus(mIsAccOn);


            mSharedTools.putIntValue(SharedPreferencedTools.KEY_STD_CON_MIN,
                    Integer.valueOf(mStdConMin.getText().toString()));
            mSharedTools.putIntValue(SharedPreferencedTools.KEY_STD_CON_RELEASE_MIN,
                    Integer.valueOf(mStdConReleaseMin.getText().toString()));
            mSharedTools.putIntValue(SharedPreferencedTools.KEY_STD_IDING,
                    Integer.valueOf(mStdIdingMin.getText().toString()));
            mSharedTools.putIntValue(SharedPreferencedTools.KEY_STD_OVERSPEED,
                    Integer.valueOf(mStdOverSpeed.getText().toString()));
            mSharedTools.putIntValue(SharedPreferencedTools.KEY_STD_RUSH_TURN,
                    Integer.valueOf(mStdRushTurn.getText().toString()));
            mSharedTools.putIntValue(SharedPreferencedTools.KEY_STD_RUSH_ACC,
                    Integer.valueOf(mStdRushAcc.getText().toString()));
            mSharedTools.putIntValue(SharedPreferencedTools.KEY_STD_RUSH_LAUNCH,
                    Integer.valueOf(mStdRushLaunch.getText().toString()));
            mSharedTools.putIntValue(SharedPreferencedTools.KEY_STD_RUSH_SPEED_DOWN,
                    Integer.valueOf(mStdRushSpeedDown.getText().toString()));
            mSharedTools.putIntValue(SharedPreferencedTools.KEY_STD_SHAKE,
                    Integer.valueOf(mShake.getText().toString()));
        } else {
            mConfirmBt.setEnabled(true);
            mEditBt.setEnabled(false);

            mIsAccOn = false;
            DrivingBehaviorApplication.getmInstance().setmAccStatus(mIsAccOn);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mConDrivingChecker.checkWhenAccOff();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).run();
            if (mCheckTimer != null) {
                mCheckTimer.cancel();
                mCheckTimer = null;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acc_on:
                DrivingBehaviorApplication.getmInstance().getmLogUtils().print("点击ACC_ON...");
                mIsAccOn = true;
                DrivingBehaviorApplication.getmInstance().setmAccStatus(mIsAccOn);
                mAccOn.setEnabled(false);
                mAccOff.setEnabled(true);
                // 定时连续驾驶检查
                toConDrivingCheck();

                break;
            case R.id.acc_off:
                DrivingBehaviorApplication.getmInstance().getmLogUtils().print("点击ACC_OFF...");
                mIsAccOn = false;
                DrivingBehaviorApplication.getmInstance().setmAccStatus(mIsAccOn);
                mAccOn.setEnabled(true);
                mAccOff.setEnabled(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mConDrivingChecker.checkWhenAccOff();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).run();
                if (mCheckTimer != null) {
                    mCheckTimer.cancel();
                    mCheckTimer = null;
                }
                break;
            case R.id.confirmButton:
                DrivingBehaviorApplication.getmInstance().getmLogUtils().print("点击确认按钮...");
                setEnableFromConfrimBtOrEditBt(true);
                break;
            case R.id.editButton:
                DrivingBehaviorApplication.getmInstance().getmLogUtils().print("点击编辑按钮...");
                setEnableFromConfrimBtOrEditBt(false);
                break;
            default:
                break;
        }
    }

    class LocationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("NEW LOCATION SENT".equals(action)) {
                // TODO Auto-generated method stub
                gpsData = (GpsData) intent.getSerializableExtra("newLoca");
                DrivingBehaviorApplication.getmInstance().getmLogUtils().print("收到GPS定位通知...");
                if (mIsAccOn) {
//                    Toast.makeText(MainActivity.this,locationMsg,Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 未放手刹起步违反检查
                            mHandBrakeChecker.doCheck(gpsData);
                        }
                    }).run();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 怠速检查
                            mIdlingChecker.doCheck(gpsData);
                        }
                    }).run();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 超速检查
                            mOverSpeedChecker.doCheck(gpsData);
                        }
                    }).run();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 急加速驾驶检查
                            mRushAccChecker.doCheck(gpsData);
                        }
                    }).run();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 急起步驾驶检查
                            mRushLaunchChecker.doCheck(gpsData);
                        }
                    }).run();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 急减速驾驶检查
                            mSpeedDownChecker.doCheck(gpsData);
                        }
                    }).run();

                }
            } else if ("TOAST_ACTION".equals(action)) {
                String msg = intent.getStringExtra("TOAST_MSG");
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();

                mMsgList.add(msg);
                notifyStartTTS();
            }


        }
    }

    private void notifyStartTTS() {
        if (!mTextToSpeech.isSpeaking() && mMsgList.size() > 0) {
            mTextToSpeech.speak(mMsgList.get(0), TextToSpeech.QUEUE_FLUSH, map);
        }

    }

    private void toConDrivingCheck() {
        mCheckTimer = new Timer();
        mCheckTimer.schedule(new CheckConDrivingTask(), 1000, 60 * 1000);
    }

    private class CheckConDrivingTask extends TimerTask {
        @Override
        public void run() {
            Log.i(TAG, "CheckConDrivingTask...连续驾驶检查");
            // 连续驾驶检查
            mConDrivingChecker.doCheck();
        }

    }

}
