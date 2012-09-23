package com.example.cardio_v1;

import java.util.HashMap;

import android.R.*;
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
import android.media.SoundPool;
import android.media.AudioManager;
import android.media.SoundPool.OnLoadCompleteListener;
import java.util.Timer;
import java.util.TimerTask;


//test line to push -klip
public class DisplayMessageActivity extends Activity 
{
	private static final Integer SoundA = null;
	private static final Integer SoundB = null;
	private static final Integer SoundC = null;
	private static final Integer SoundD = null;
	private static final Integer SoundE = null;
	private String speed;
	private TextView textView3;
	private LocationManager locationManager;
	private String provider;
	private String location1;
	private SoundPool mySoundPool;
	private AudioManager myAudioManager;
	private HashMap<Integer, Integer> mySoundMap;
	private int stream1 = 0;
	private int stream2= 0;
	private int stream3= 0;
	private int stream4= 0;
	private int stream5= 0;
	private boolean loaded = false;
	boolean soundDone = false;
	
	private Handler soundTask;
	float streamVolume;
	private int j = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Intent intent = getIntent();
        
        setAudio();

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
        
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        criteria.setSpeedRequired(true);
//        provider = locationManager.getBestProvider(criteria, false);
//        Location location = locationManager.getLastKnownLocation(provider);
        
       
        
        
//        while (location == null)
//        {
//        	location = locationManager.getLastKnownLocation(provider);
//        
//        	if (location!=null)
//        		{
//        			locationChanged(location);
//        		}
//        	else
//        		{
//        			textView3.setText("Not Connected to GPS");
//        		}
//        }
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_display_message, menu);
        

        
        return true;
    }
    
    private void manyAudioLoop()
    {
    	streamVolume = myAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        streamVolume = streamVolume / myAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        
        if(loaded==true)
        {
        	int delay = 5000;
        	
        	
        	Timer timer = new Timer();
        	
        	
        	timer.scheduleAtFixedRate(new TimerTask()
        	{
        		public void run()
        		{
        		
        			playSound(j);
        			j++;
        			
        		}
        	},0, delay);
        	
        }

    }
    
    
    public void playSound(int i)
    {
    	
    	switch (i)
    	{
    	case 1:
    		mySoundPool.play(mySoundMap.get(SoundA), streamVolume, streamVolume, 1, 0, 1f);
    	 break;
    	 
    	case 2:
    		mySoundPool.play(mySoundMap.get(SoundB), streamVolume, streamVolume, 1, 0, 1f);
    		break;
    		
    	case 3:
    		 mySoundPool.play(mySoundMap.get(SoundC), streamVolume, streamVolume, 1, 0, 1f);
    		 soundDone = true;
    		 break;
    		
    	}
    	
    	
    }
    
    @SuppressLint("UseSparseArrays")
	public void setAudio()
    { 
    	 myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    	 
    	 mySoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        
         mySoundMap = new HashMap<Integer, Integer>();
        	 
         mySoundMap.put(SoundA,mySoundPool.load(this, R.raw.a,1));
         mySoundMap.put(SoundB,mySoundPool.load(this, R.raw.b,1));
         mySoundMap.put(SoundC,mySoundPool.load(this, R.raw.c,1));
         
         mySoundPool.setOnLoadCompleteListener(new OnLoadCompleteListener()
         {
        	 
        	 public void onLoadComplete(SoundPool mySoundPool, int stream1, int status)
        	 {
        		 loaded = true;
        		 manyAudioLoop();
        	 }
         });
          
        
//         mySoundMap.put(SoundD,mySoundPool.load(this, R.raw.d,1));
//         mySoundMap.put(SoundE,mySoundPool.load(this, R.raw.e,1)); 
         
    }
    
    
   
    
//    private void locationChanged(Location location)
//    {
//    	speed = Float.toString(location.getSpeed());
//    	textView3.setText(speed);
//    	
//    }
}
