package com.example.cardio_v1;

import java.io.IOException;
import java.util.HashMap;


import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool;
import android.media.AudioManager;
import android.media.SoundPool.OnLoadCompleteListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.media.*;

import android.os.IBinder;
import android.content.ServiceConnection;
import android.content.ComponentName;

public class DisplayMessageActivity extends Activity 
{

	private String speed;
	private TextView textView3;

	float currentSpeed;
	String cSpd;
	private int count = 0;
    private GPSservice sbinder;
    Intent intent;
    private Handler UIhandler = new Handler();
    
    SeekBar volControl;
	
	TextView volume;
	
	AudioManager am;

	
    private ServiceConnection svcconnect = new ServiceConnection() {
    	public void onServiceConnected(ComponentName name, IBinder svc) {
    		sbinder = ((GPSservice.gpsbinder)svc).getService();
    		startService(intent);
    	}
    	public void onServiceDisconnected(ComponentName name) {
    		sbinder = null;
    	}
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        intent = getIntent();
        //use new intent
        intent = new Intent(DisplayMessageActivity.this, GPSservice.class);
        //Start/bind GPS service
        bindService(intent, svcconnect, Context.BIND_AUTO_CREATE);
       

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
        
        //old---startService(new Intent(getBaseContext(), GPSservice.class));
        cSpd = "initializing...";
        textView3.setText(cSpd);     
        
        
        
        volControl = (SeekBar)findViewById(R.id.volCont);
        
        
        
        volControl.setOnSeekBarChangeListener((OnSeekBarChangeListener) this);
        
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
       
    }
        
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_display_message, menu);
        
        startService(new Intent(getBaseContext(), soundService.class));
        //Get updated GPS stats
        gpsUpdate();
      
      
        //this is where it should crash currently
        return true;
    }
   
    public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		
		volume.setText(String.valueOf(progress));

		am.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0);
	}
    
    public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}



	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
    
    
    public void gpsUpdate() {
    	int delay = 1000;   	
        	Timer timer = new Timer();
        	timer.scheduleAtFixedRate(new TimerTask() {
        		public void run() {
       			     currentSpeed = sbinder.getCurrentSpeed();
        			 cSpd = Float.toString(currentSpeed);
        			 //Update Textview on the UI thread
        			 UIhandler.post(new Runnable() {
        				 public void run() {
        					 textView3.setText(cSpd);
        					 textView3.refreshDrawableState();
        				 }
        			 });
        		}
          	},0, delay);
    }
    
    
    
    public void onStop()
    {
    	super.onStop();
    	stopService(new Intent(getBaseContext(), soundService.class));
    }

    
    
  
}

