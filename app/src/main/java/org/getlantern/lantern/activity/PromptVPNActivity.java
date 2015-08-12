package org.getlantern.lantern.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import org.getlantern.lantern.Constants;
import org.getlantern.lantern.service.LanternVPN;

public class PromptVPNActivity extends Activity {

	private static final String TAG = "PromptVPNActivity";
	private final static int REQUEST_VPN = 7777;
	private	Intent intent = null;

	@Override
	public void onCreate( Bundle icicle ) {
		super.onCreate( icicle );

		Log.d(TAG, "Prompting user to start Orbot VPN");

		intent = VpnService.prepare(this);
		startVpnService();

	}
	 
	private void startVpnService ()
	{
   		if (intent != null) {
			Log.w(TAG,"Requesting VPN connection");
			startActivityForResult(intent,REQUEST_VPN);
		} else {
   			Log.d(TAG, "VPN enabled, starting Lantern...");
            
            Handler h = new Handler();
            h.postDelayed(new Runnable () {
            	
            	public void run ()
            	{
            		sendIntentToService(Constants.ENABLE_VPN);
            		finish();
            	}
            }, 1000);
   			
   		}
	}
	
	  @Override
	    protected void onActivityResult(int request, int response, Intent data) {
	        super.onActivityResult(request, response, data);
	        
	        if (request == REQUEST_VPN && response == RESULT_OK)
	        {
	            sendIntentToService(Constants.ENABLE_VPN);
	        }
	  }
	  

		private void sendIntentToService(String action) {
			Intent lanternService = new Intent(this, LanternVPN.class);
			lanternService.setAction(action);
			startService(lanternService);
		}
    
}