package batterysaver.battery.com.resistancerecorder;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.batterysaver.utils.Utils;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.SwitchCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    protected Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage){
            //Log.e("LoggingService", String.format("file_path:%s, value:%s", inputMessage.getData().getString("file_path"), inputMessage.getData().getString("value")));
            final LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Snackbar.make(li.inflate(R.layout.activity_main, null), inputMessage.getData().getString("file_path") + " : " + inputMessage.getData().getString("value"), Snackbar.LENGTH_LONG).show();
            Snackbar.make(findViewById(R.id.linearlayout), inputMessage.getData().getString("file_path") + " : " + inputMessage.getData().getString("value"), Snackbar.LENGTH_LONG).show();
        }
    };

    private SwitchCompat switchCompat;
    private EditText frequencyText;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tv = (TextView) findViewById(R.id.config_file);
        tv.setText(Constants.getConfigFilePath());
        tv = (TextView) findViewById(R.id.output_file);
        tv.setText(Constants.getOutputFilePath());

        switchCompat = (SwitchCompat) findViewById(R.id.switch_compat);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Constants.setRunning(true);
                    initSystemStatus(false);
                }else{
                    Constants.setRunning(false);
                    initSystemStatus(false);
                }
            }
        });
        frequencyText = (EditText) findViewById(R.id.frequency_text);

        displayListView();
        initSystemStatus();

        //Start the logging service
        Intent intent = new Intent(this, LoggingService.class);
        startService(intent);

    }

    public void initSystemStatus(){
        initSystemStatus(true);
    }
    public void initSystemStatus(boolean first){
        if (Constants.isRunning()){
            switchCompat.setChecked(true);
            frequencyText.setEnabled(false);
            Constants.setFrequecy(Long.parseLong(frequencyText.getText().toString()));
            //listView.setEnabled(false);
            for (int i = 0 ; i < Constants.fileList.size(); i++){
                if (listView.getChildAt(i)!= null) {
                    ViewHolder vh = (ViewHolder) listView.getChildAt(i).getTag();
                    vh.isChecked.setEnabled(false);
                }
            }

            if (!first) {
                Intent intent = new Intent(LoggingService.START_LOGGING);
                sendBroadcast(intent);

                Utils.saveLoggedFiles(Constants.fileList);

                Toast.makeText(MainActivity.this, "Start logging", Toast.LENGTH_SHORT).show();
            }
        }else{
            switchCompat.setChecked(false);
            frequencyText.setEnabled(true);
            Constants.setFrequecy(Long.parseLong(frequencyText.getText().toString()));
            //listView.setEnabled(true);
            for (int i = 0 ; i < listView.getChildCount(); i++){
                ViewHolder vh = (ViewHolder)listView.getChildAt(i).getTag();
                vh.isChecked.setEnabled(true);
            }

            if (!first){
                Intent intent = new Intent(LoggingService.STOP_LOGGING);
                sendBroadcast(intent);
                Toast.makeText(MainActivity.this, "Stop logging", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private List<LoggedFile> initiListView(){
        Constants.fileList = Utils.getLoggedFilesFromFile();
        if (Constants.fileList.size() == 0){
            for (String filePath : Constants.INIT_FILE_LIST){
                Constants.fileList.add(new LoggedFile(filePath, true));
            }
            Utils.saveLoggedFiles(Constants.fileList);
        }
        return Constants.fileList;
    }

    FileArrayAdapter dataAdapter = null;

    private void displayListView() {
        //Array list of countries
        List<LoggedFile> fileList = initiListView();

        //create an ArrayAdaptar from the String Array
        dataAdapter = new FileArrayAdapter(this, R.layout.file_list, fileList);
        listView = (ListView) findViewById(R.id.file_list);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ViewHolder vh = (ViewHolder)view.getTag();
                if (!Constants.isRunning()) {
                    boolean ifChecked = !vh.isChecked.isChecked();
                    vh.isChecked.setChecked(ifChecked);
                    Constants.fileList.get(position).setChecked(ifChecked);
                }
                //Toast.makeText(getApplicationContext(), "Clicked on " + vh.filePath.getText().toString(), Toast.LENGTH_LONG).show();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        String filePath = vh.filePath.getText().toString().trim();
                        String value = null;
                        try {
                            value = Utils.getFileContent(filePath);
                        } catch (IOException ex) {
                            value = ex.getMessage();
                        }
                        Message msg = Message.obtain();
                        Bundle bundle = new Bundle();
                        bundle.putString("file_path", filePath);
                        bundle.putString("value", value);
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);
                    }
                };
                thread.start();
            }
        });
    }

    public class ViewHolder{
        TextView filePath;

        CheckBox isChecked;
    }
    public class FileArrayAdapter extends ArrayAdapter<LoggedFile> {

        private ArrayList<LoggedFile> fileList = new ArrayList<LoggedFile>();

        public FileArrayAdapter(Context context, int textViewResourceId,
                                List<LoggedFile> fileList) {
            super(context, textViewResourceId, fileList);
            this.fileList.addAll(fileList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                final LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = li.inflate(R.layout.file_list, null);

                holder = new ViewHolder();
                holder.filePath = (TextView) convertView.findViewById(R.id.text_file);
                holder.isChecked = (CheckBox) convertView.findViewById(R.id.checkbox_file);
                convertView.setTag(holder);

            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            LoggedFile lf = fileList.get(position);
            holder.filePath.setText(lf.getFilePath());
            holder.isChecked.setChecked(lf.isChecked());

            return convertView;
        }

    }

}
