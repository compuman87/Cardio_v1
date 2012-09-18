package com.example.cardio_v1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


public class DisplayMessageActivity extends Activity 
{
	private String speed;
	private TextView textView3;
	private LocationManager locationManager;
	private String provider;
	private String location1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Intent intent = getIntent();
        String message1 = intent.getStringExtra(MainActivity.SEND_MESSAGE);
        String message2 = intent.getStringExtra(MainActivity.SEND_MESSAGE2);
        
        //TextView textView = new TextView(this);
        //TextView textView2 = new TextView(this);
        TextView textView1 = (TextView) findViewById(R.id.actSpeed);
        TextView textView2 = (TextView) findViewById(R.id.actTrack);
        textView3 = (TextView) findViewById(R.id.yourSpeed);
        
        		
        
        
        //textView.setTextSize(40);
        textView1.setText(message1);
        
        //textView.setTextSize(40);
        textView2.setText(message2);
        //setContentView(textView);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setSpeedRequired(true);
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        
        
        if (location!=null)
        {
        	locationChanged(location);
        }
        else
        {
        
        }
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_display_message, menu);
        return true;
    }
    
    private void locationChanged(Location location)
    {
    	speed = Float.toString(location.getSpeed());
    	textView3.setText(speed);
    	
    }
}
