package org.remnants;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class RemnantsService extends Service {

	Notification _notification;
	private RemnantsSocket remnantsSocket;
	private BroadcastReceiver wifiStatusReceiver;
	private IntentFilter filter;
	private RemnantsDatabase remnantsDatabase;
	boolean _initialized = false;
	
	private final String SERVER_URL = "http://192.168.1.6:8080/";
	
	Toast reconnectMsg;
	Toast netStateMsg;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		reconnectMsg = Toast.makeText(getBaseContext(), "Trying to reconnect", Toast.LENGTH_LONG);
		netStateMsg = Toast.makeText(getBaseContext(), "Network State Changed", Toast.LENGTH_LONG);

		//BroadcastReceiver.  Reconnects to server after wifi has been restablished.
		wifiStatusReceiver = new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context context, Intent intent) 
			{
				netStateMsg.show();
				NetworkInfo info = (NetworkInfo)intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
				if(info.getDetailedState() == DetailedState.CONNECTED)
				{
					reconnectMsg.show();
					remnantsSocket.connect(SERVER_URL);
				}	
			}
		};	
		filter = new IntentFilter();
		filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		this.registerReceiver(wifiStatusReceiver, filter);
		//end of BroadcastReceiver stuff
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		if(_initialized) return super.onStartCommand(intent, flags, startId);
		_initialized = true;
		//Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		
		_notification = new Notification(R.drawable.ic_launcher, getText(R.string.app_name),
				System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, RemnantsActivity.class);
		PendingIntent  pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		_notification.setLatestEventInfo(this, getText(R.string.app_name), 
				getText(R.string.hello), pendingIntent);
		startForeground(5, _notification);
		
		remnantsDatabase = new RemnantsDatabase(getBaseContext());
		
		remnantsSocket = new RemnantsSocket(getBaseContext());
		remnantsSocket.connect(SERVER_URL);
			
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy()
	{
		this.unregisterReceiver(wifiStatusReceiver);
	}
}
