package org.remnants;

import org.json.JSONObject;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class RemnantsSocket implements IOCallback
{
	private SocketIO socket;
	private IOCallback callback;
	private String url = "http://192.168.2.12:8080/";
	private Context remnantsContext;
	private Toast connectMsg, errorMsg, disconnectMsg;
	
	public RemnantsSocket(Context context) 
	{
		remnantsContext = context;
		connectMsg = Toast.makeText(remnantsContext, "Connected to " + url, Toast.LENGTH_LONG);
		errorMsg = Toast.makeText(remnantsContext, "Socket error", Toast.LENGTH_LONG);
		disconnectMsg = Toast.makeText(remnantsContext, "Disconnected from " + url, Toast.LENGTH_LONG);
		

		socket = new SocketIO();
		
		try
		{
			socket.connect(url, this);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}

	@Override
	public void on(String arg0, IOAcknowledge arg1, Object... arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnect() {
		// TODO Auto-generated method stub	
		Log.d("Sockets", "Connected");
		connectMsg.show();
	}

	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub
		Log.d("Sockets", "Disconnected");
		disconnectMsg.show();

	}

	@Override
	public void onError(SocketIOException arg0) {
		// TODO Auto-generated method stub
		Log.d("Sockets", arg0.toString());
		errorMsg.setText("Socket error: " + arg0.toString());
		errorMsg.show();
	}

	@Override
	public void onMessage(String arg0, IOAcknowledge arg1) {
		// TODO Auto-generated method stub
		Log.d("Sockets", "String Message");
	}

	@Override
	public void onMessage(JSONObject arg0, IOAcknowledge arg1) {
		// TODO Auto-generated method stub
		Log.d("Sockets", "JSON Message");
	}	
}
