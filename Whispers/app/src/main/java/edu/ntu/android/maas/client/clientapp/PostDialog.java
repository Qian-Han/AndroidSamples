package edu.ntu.android.maas.client.clientapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pillar on 5/10/2016.
 */
public class PostDialog extends Dialog {

    private EditText postContent;
    private RadioButton allRadio;
    private RadioButton someoneRadio;
    private EditText postSomeone;
    private RadioGroup radioGroup;
    private TextView postTitle;
    private Button postSubmit;
    private Context mContext;
    private PostDialog thisDialog;

    public PostDialog(Context context) {
        super(context);
        this.mContext = context;
        this.thisDialog = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_dialog);

        postTitle = (TextView) findViewById(R.id.post_title);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        postContent = (EditText) findViewById(R.id.post_content);
        allRadio = (RadioButton) findViewById(R.id.radio_all);
        someoneRadio = (RadioButton) findViewById(R.id.radio_someone);
        postSomeone = (EditText) findViewById(R.id.someone);
        postSubmit = (Button) findViewById(R.id.post_submit);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == allRadio.getId()) {
                    postSomeone.setEnabled(false);
                } else if (checkedId == someoneRadio.getId()) {
                    postSomeone.setEnabled(true);
                }
            }
        });

        postContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                postTitle.setText(String.format("Post: (%d/140)", s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        postSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postContent.getText().toString().length() > 140){
                    Toast.makeText(mContext, "The length of text exceeds 140 characters", Toast.LENGTH_SHORT).show();
                }else if (postContent.getText().toString().length() == 0) {
                    Toast.makeText(mContext, "The content is empty!", Toast.LENGTH_SHORT).show();
                }else if (someoneRadio.isChecked() && postSomeone.getText().toString().length() == 0){
                    Toast.makeText(mContext, "The name of user is empty!", Toast.LENGTH_SHORT).show();
                }else{
                    RequestQueue queue = Volley.newRequestQueue(mContext);
                    StringRequest sr = new StringRequest(Request.Method.POST, edu.ntu.android.maas.client.clientapp.Constants.POST_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            thisDialog.dismiss();
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            StringBuilder data = new StringBuilder();
                            if (allRadio.isChecked()){
                                data.append("all").append(Constants.SPECIAL_CHARACTOR);
                            }else if (someoneRadio.isChecked()){
                                data.append(postSomeone.getText().toString()).append(Constants.SPECIAL_CHARACTOR);
                            }
                            data.append(postContent.getText().toString());
                            params.put("data", data.toString());

                            //Log.e("DEBUG", data.toString());
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("Content-Type","application/x-www-form-urlencoded");
                            return params;
                        }
                    };

                    queue.add(sr);
                }
            }
        });
    }
}
