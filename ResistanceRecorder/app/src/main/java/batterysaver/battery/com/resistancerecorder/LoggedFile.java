package batterysaver.battery.com.resistancerecorder;

/**
 * Created by pillar on 7/14/2016.
 */
public class LoggedFile {
    private String filePath;
    private boolean isChecked;

    public LoggedFile(String filePath, boolean isChecked){
        this.filePath = filePath;
        this.isChecked = isChecked;
    }

    public String getFilePath(){
        return this.filePath;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public boolean isChecked(){
        return this.isChecked;
    }

    public void setChecked(boolean isChecked){
        this.isChecked = isChecked;
    }

    public static LoggedFile createLoggedFile(String filePath, String isChecked){
        if (isChecked.equalsIgnoreCase("1")){
            return new LoggedFile(filePath, true);
        }else{
            return new LoggedFile(filePath, false);
        }
    }

    public String toString(){
        return filePath + " " + (isChecked?"1":"0");
    }
}
