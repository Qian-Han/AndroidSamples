package edu.ntu.android.learning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Context mContext;
	
	private Button httpGetButton;
	private Button httpRangeButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mContext = getApplicationContext();

		httpGetButton = (Button) findViewById(R.id.http_get);
		httpRangeButton = (Button) findViewById(R.id.http_range);

		httpGetButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
			}

		});

		httpRangeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				HttpRangeTask hrt = new HttpRangeTask(mContext);
				//hrt.execute("https://raw.githubusercontent.com/impillar/AndroidSamples/master/data/downloadfile.txt");
				//hrt.execute("http://www.google.com");
				//hrt.execute("http://pat.sce.ntu.edu.sg/~pillar/android/downloadfile.txt");
				hrt.execute("http://download.thinkbroadband.com/100MB.zip");
			}
		});
	}

	public static class HttpGetTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	/**
	 * Please ensure the server where the file is deployed supports the range request property.
	 * @author pillar
	 *
	 */
	public static class HttpRangeTask extends AsyncTask<String, Integer, String> {

		protected Context mContext;
		
		public HttpRangeTask(Context context){
			this.mContext = context;
		}
		
		@Override
		protected String doInBackground(String... params) {
			for (int i = 0; i < params.length; i++){
				URL url = null;
				HttpURLConnection urlConn = null;
				BufferedReader br = null;
				try {
					url = new URL(params[i]);
					urlConn = (HttpURLConnection)url.openConnection();
					
					urlConn.setRequestProperty("RANGE", "bytes=20-30");
					urlConn.setDoInput(true);
					urlConn.setConnectTimeout(5000);
					urlConn.setRequestMethod("GET");
					urlConn.connect();

					br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
					String line = null;
					StringBuilder sb = new StringBuilder();
					while ((line = br.readLine())!=null){
						sb.append(line+"\n");
					}
					
					return sb.toString();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if (br != null) {
					      try {
					        br.close();
					      } catch (IOException e) {
					        e.printStackTrace();
					        }
					    }
				}
				
			}
			return null;
		}

		protected void onProgressUpdate(Integer... progress) {
			//setProgressPercent(progress[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(mContext, "Result:" + result, Toast.LENGTH_LONG).show();
		}

	}
}
