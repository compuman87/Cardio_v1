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
				
				//To test getSpeed - UNCOMMENT the following line, run the program and send a location to the emulator and it should show up
				//meters per second
				//l.setSpeed(28); 
				//speed = l.getSpeed();
			    
				//To Test location changes --UNCOMMENT the following line and run
				//Toast.makeText(getBaseContext(), "Location:  Lat: " + l.getLatitude() + "Long: " + l.getLongitude(), Toast.LENGTH_SHORT).show();
				//Potentially good method to compute total distance traveled
				//l.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results)
				makeUseOfNewLocation(l);
				double metersPerSec =l.getSpeed();
				double milesPerHour = metersPerSec*2.2369;
				speed = (float) milesPerHour;
			}
		}
		public void makeUseOfNewLocation(Location location){
    		count++;
    		if(count>3){
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
    				float previoustime = time/(1000*60*60);
    				float preTime = time/(1000);
    				time = time+1000;
    				float currenttime = time/(1000*60*60);
    				float curTime = time/(1000);
    				Location dest = location;
    				//float disTo = start.distanceTo(dest);
    			/**distance divided by time get time in hours*/
    				Location.distanceBetween(startLat, startLong, endLat, endLong, result);
    			//System.out.println(start.getLatitude(),start.getLongitude(), end.getLatitude() , end.getLongitude());
    				//System.out.println("disTo.................................." + disTo);
    				//for(int i = 0; i <result.length-1;i++){
    				//if(result[i] != null){
//    					System.out.println("time hours" + currenttime + " result " +result[0]+ " conversion "+ (result[0]/1609.3));
    					//System.out.println("i " + i + " result " +result[i]);
    				//}
    				//}
    				//System.out.printf("time hours %f \n",currenttime);
    				//result .001/1sec or .00027 hours
    				double currentSpeed = (result[0]/1609.3)/((currenttime-previoustime));
    				//speed = (float) currentSpeed;
    				//location.setSpeed(speed); 
    				//System.out.println("secs" + (curTime-preTime) + " result " +result[0]+ " conversion "+ (result[0]/(curTime-preTime)));
    				speed = result[0]/(curTime-preTime);
    				location.setSpeed(speed);
    				//float metersPerSecond = result[0]/(time/1000);
    				//location.setSpeed(metersPerSecond);
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
			return speed;
	}
}
