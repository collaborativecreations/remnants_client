package org.remnants;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class RemnantsService extends Service {

	static Notification _notification;
	private RemnantsDatabase remnantsDatabase;
	
	public static void start(Activity activity, Class objclass)
	{	
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		
		_notification = new Notification(R.drawable.ic_launcher, getText(R.string.app_name),
				System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, RemnantsActivity.class);
		PendingIntent  pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		_notification.setLatestEventInfo(this, getText(R.string.app_name), 
				getText(R.string.hello), pendingIntent);
		startForeground(5, _notification);
		remnantsDatabase = new RemnantsDatabase(getBaseContext());
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
