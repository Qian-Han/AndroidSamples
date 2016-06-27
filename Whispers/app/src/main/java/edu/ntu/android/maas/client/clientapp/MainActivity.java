package edu.ntu.android.maas.client.clientapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Button refreshButton;
    private RequestQueue queue;
    private TextView mctv;
    private TextView toWhom;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        Constants.ANDROID_ID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        setContentView(R.layout.activity_main);
        this.refreshButton = (Button)findViewById(R.id.refresh);
        this.mctv = (TextView) findViewById(R.id.display_content);
        this.toWhom = (TextView) findViewById(R.id.to_whom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.e("Constants", Constants.CURRENT_VERSION_URL);
        Log.e("Constants", Constants.FETCH_URL);
        Log.e("Constants", Constants.POST_URL);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                PostDialog pd = new PostDialog(mContext);
                pd.show();
            }
        });

        queue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, Constants.FETCH_URL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                refreshButton.setEnabled(true);

                if (response == null || response.length() == 0){
                    Toast.makeText(mContext, "Connection error, please check your network!", Toast.LENGTH_SHORT).show();
                }else {
                    String[] arrs = response.split("specialcharactor", 2);
                    if (arrs != null && arrs.length == 2) {
                        toWhom.setText("To: " + arrs[0]);
                        toWhom.setTextSize(20);
                        mctv.setText(arrs[1]);
                        mctv.setTextSize(20);
                    }
                    //Toast.makeText(mContext, "Update successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshButton.setEnabled(false);
                queue.add(stringRequest);
            }
        });

        Utils.operateOn(this, "ACTIVITY", "POINTCUT_ONCREATE");

        //Start the bot service
        Intent intent  = new Intent(this, BotService.class);
        startService(intent);

        queue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart(){
        super.onStart();
        Utils.operateOn(this, "ACTIVITY", "POINTCUT_ONSTART");
    }

    @Override
    public void onResume(){
        super.onResume();
        Utils.operateOn(this, "ACTIVITY", "POINTCUT_ONRESUME");
    }

    @Override
    public void onPause(){
        super.onPause();
        Utils.operateOn(this, "ACTIVITY", "POINTCUT_ONPAUSE");
    }

    @Override
    public void onStop(){
        super.onStop();

        Utils.operateOn(this, "ACTIVITY", "POINTCUT_ONSTOP");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        Utils.operateOn(this, "ACTIVITY", "POINTCUT_ONDESTROY");
    }
}
