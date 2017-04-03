package batterysaver.battery.com.resistancerecorder;

import android.os.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pillar on 7/14/2016.
 */
public class Constants {

    public static final String CONFIG_FILE = "file_list.txt";
    public static final String OUTPUT_FILE = "default.log";

    public static String getConfigFolderPath(){
        return Environment.getExternalStorageDirectory().getPath() + "/batterysaver/";
    }

    public static String getConfigFilePath(){
        return Environment.getExternalStorageDirectory().getPath() + "/batterysaver/" + CONFIG_FILE;
    }

    public static String getOutputFilePath(){
        return Environment.getExternalStorageDirectory().getPath() + "/batterysaver/logs/" + OUTPUT_FILE;
    }


    public static final String CAPACITY = "/sys/class/power_supply/bms/capacity";
    public static final String CAPACITY_RAW = "/sys/class/power_supply/bms/capacity_raw";
    public static final String CURRENT_NOW = "/sys/class/power_supply/bms/current_now";
    public static final String RESISTANCE = "/sys/class/power_supply/bms/resistance";
    public static final String TEMP = "/sys/class/power_supply/bms/temp";
    public static final String VOLTAGE_NOW = "/sys/class/power_supply/bms/voltage_now";
    public static final String VOLTAGE_OCV = "/sys/class/power_supply/bms/voltage_ocv";

    public static final String[] INIT_FILE_LIST = {CAPACITY, CAPACITY_RAW, CURRENT_NOW, RESISTANCE, TEMP, VOLTAGE_NOW, VOLTAGE_OCV};

    public static final int LOGGING_SERVICE_ID = 101;

    public static List<LoggedFile> fileList = new ArrayList<LoggedFile>();

    public static int getSelectedFileCount(){
        int cnt = 0;
        for (LoggedFile lf : fileList){
            if (lf.isChecked()){
                cnt++;
            }
        }
        return cnt;
    }

    private static long _frequency;
    public static long getFrequency(){
        return _frequency;
    }

    public static void setFrequecy(long frequency){
        _frequency = frequency;
    }

    private static boolean _running = false;
    public static boolean isRunning(){
        return _running;
    }

    public static void setRunning(boolean running){
        _running = running;
    }
}
