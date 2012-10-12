package com.example.cardio_v1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;


public class GPSservice extends Service {
	
	private LocationManager lm;
	private LocationListener ll;
	
	@Override
	//This method binds to DisplayMessageActivity
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int stID) {
		//Run service until manually stopped
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		ll = new LocListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, ll);
		Toast.makeText(this, "GPS Service Started", Toast.LENGTH_SHORT).show();
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		Toast.makeText(this, "GPS Service Stopped", Toast.LENGTH_SHORT).show();
		//call parent destroy method
		super.onDestroy();
	}
	
	private class LocListener implements LocationListener {
		public void onLocationChanged(Location l) {
			if (l != null) {
				//To Test location changes --UNCOMMENT the following line and run
				//Toast.makeText(getBaseContext(), "Location:  Lat: " + l.getLatitude() + "Long: " + l.getLongitude(), Toast.LENGTH_SHORT).show();
				//Potentially good method to compute total distance traveled
				//l.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results)
			}
		}
		
		public void onProviderDisabled(String provider) {
			Toast.makeText(getBaseContext(), "GPS Provider is Disabled", Toast.LENGTH_SHORT).show();
		}
		
		public void onProviderEnabled(String provider) {
			//
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			//Do nothing
		}
	}
}
