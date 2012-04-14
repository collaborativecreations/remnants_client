package org.remnants;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.util.Log;

import com.couchbase.android.CouchbaseMobile;
import com.couchbase.android.ICouchbaseDelegate;
import com.couchbase.android.Intents.CouchbaseError;
import com.couchbase.android.Intents.CouchbaseStarted;

public class RemnantsDatabase {
	private ServiceConnection couchServiceConnection;
	private Context remnantsContext;
	private static final String TAG = "RemnantsDatabase";

	RemnantsDatabase(Context context){
		Log.i(TAG, "Constructor");
		remnantsContext = context;
		startCouchbase();
	}

	private final ICouchbaseDelegate mDelegate = new ICouchbaseDelegate() {
		@Override
		public void couchbaseStarted(String host, int port) {
			Log.i(TAG, "STARTED");
		}

		@Override
		public void exit(String error) {
			Log.i(TAG, "STOPPED");
		}
	};

	public void startCouchbase() {
		CouchbaseMobile couch = new CouchbaseMobile(remnantsContext, mDelegate);
		//CouchbaseMobile couch = new CouchbaseMobile(getBaseContext());
		couchServiceConnection = couch.startCouchbase();
	}
}
