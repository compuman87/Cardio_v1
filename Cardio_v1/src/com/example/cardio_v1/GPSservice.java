package com.example.cardio_v1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Binder;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;


public class GPSservice extends Service {
	
	private LocationManager lm;
	private LocationListener ll;
	private final IBinder bind = new gpsbinder();
	private float speed;
	//startime is always zero
	long starttime = 0;
	float time = starttime;
	double startLong = 0;
	double startLat = 0;
	double endLong;
	double endLat;
	int count = 0;
	//starting location is null so that it can be set by the first location 
	//sent to the location manager
	Location start = null;
	
	//Create inner Binder Class
	public class gpsbinder extends Binder {
		GPSservice getService() {
			return GPSservice.this;
		}
	}
	
	@Override
	//This method binds to DisplayMessageActivity
	public IBinder onBind(Intent arg0) {
		return bind;
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
				makeUseOfNewLocation(l);
				//double metersPerSec =l.getSpeed();
				//double milesPerHour = metersPerSec*2.2369;
				//speed = (float) milesPerHour;
				speed = l.getSpeed();
			}
		}
		public void makeUseOfNewLocation(Location location){
    		count++;
    		if(count>6){
    			float[] result = new float[3];
    			if(startLong == 0 && startLat == 0){
    				//array[0] = location.getLongitude();
    				start = location;
    				startLong = location.getLongitude();
    				startLat = location.getLatitude();
    				return;
    			}
    			else{
    				endLong = location.getLongitude();
    				endLat = location.getLatitude();
    	//			float previoustime = time/(1000*60*60);
    				float preTime = time/(1000);
    				time = time+1000;
    	//			float currenttime = time/(1000*60*60);
    				float curTime = time/(1000);
    				Location dest = location;
    				//float disTo = start.distanceTo(dest);
    			/**distance divided by time get time in hours*/
    				Location.distanceBetween(startLat, startLong, endLat, endLong, result);
    				
    				float curSpeed = result[0]/(curTime-preTime);
    				location.setSpeed(curSpeed);
    			
    			//sets the starting position set to the ending positions
    				start = dest;
    				startLong = location.getLongitude();
    				startLat = location.getLatitude();
    			}
    		}
    		
    	}
		public void onProviderDisabled(String provider) {
			Toast.makeText(getBaseContext(), "GPS Provider is Disabled", Toast.LENGTH_SHORT).show();
		}
		
		public void onProviderEnabled(String provider) {
			//Do nothing
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			//Do nothing
		}
	}
	
	public float getCurrentSpeed() {
		//Convert m/s to mph
		float milesPerHour = 0;
		float convert = (float)2.2369;
		milesPerHour = speed*convert;
		speed = milesPerHour;
		return speed;
	}
}
