package org.remnants;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class RemnantsService extends Service {

	Notification _notification;
	private RemnantsSocket remnantsSocket;
	private RemnantsDatabase remnantsDatabase;
	boolean _initialized = false;
	
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
		
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
