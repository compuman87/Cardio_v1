package com.example.cardio_v1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class GPSservice extends Service {
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int stID) {
		//Run service until manually stopped
		Toast.makeText(this, "GPS Service Started", Toast.LENGTH_SHORT).show();
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		//call parent destroy method
		super.onDestroy();
		Toast.makeText(this, "GPS Service Stopped", Toast.LENGTH_SHORT).show();
	}
}
