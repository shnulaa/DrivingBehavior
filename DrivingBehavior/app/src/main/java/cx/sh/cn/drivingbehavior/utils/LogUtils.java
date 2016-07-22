package cx.sh.cn.drivingbehavior.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZengChao on 2016/7/12.
 */
public class LogUtils {
    private static LogUtils mLogUtils;

    private static Writer mWriter;

    private static SimpleDateFormat df;

    public LogUtils() {
        String strPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "DemoLog" + File.separator ;
        File dirFile = new File(strPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(d);
        File logf = new File(strPath, "DemoLog"+dateStr+".txt");
        try {
            if (!logf.createNewFile()) {
                Log.d("LogUtils--", "文件已经创建...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String file_path = logf.getAbsolutePath();
            mWriter = new BufferedWriter(new FileWriter(file_path, true), 2048);
            df = new SimpleDateFormat("[yy-MM-dd hh:mm:ss]: ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void close() throws IOException {
        mWriter.close();
    }

    public void print(final String log){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mWriter.write(df.format(new Date()));
                    mWriter.write(log);
                    mWriter.write("\n");
                    mWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).run();

    }

    public void print(final Class cls, final String log) { //如果还想看是在哪个类里可以用这个方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mWriter.write(df.format(new Date()));
                    mWriter.write(cls.getSimpleName() + " ");
                    mWriter.write(log);
                    mWriter.write("\n");
                    mWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).run();
    }
}
