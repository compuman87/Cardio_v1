package com.example.cardio_v1;

import java.io.IOException;
import java.util.HashMap;



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
//	private static final Integer SoundA = null;
//	private static final Integer SoundB = null;
//	private static final Integer SoundC = null;
//	private static final Integer SoundD = null;
//	private static final Integer SoundE = null;
	private String speed;
	private TextView textView3;
//	private LocationManager locationManager;
//	private String provider;
//	private String location1;
//	private SoundPool mySoundPool;
//	private AudioManager myAudioManager;
//	private HashMap<Integer, Integer> mySoundMap;
//	private int stream1 = 0;
//	private int stream2= 0;
//	private int stream3= 0;
//	private int stream4= 0;
//	private int stream5= 0;
//	private boolean loaded = false;
//	boolean soundDone = false;
//	
//	private Handler soundTask;
//	float streamVolume;
	float currentSpeed;
	String cSpd;
	private int count = 0;
    private GPSservice sbinder;
    Intent intent;
    private Handler UIhandler = new Handler();
	
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
        //intent = getIntent();
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
    }
        
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_display_message, menu);
        //Get updated GPS stats
        gpsUpdate();
        looping(count);
        //this is where it should crash currently
        return true;
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
    
    
  synchronized public void audioLoop( Uri r)
    {
    	MediaPlayer mp;
    	
    	mp = MediaPlayer.create(getApplicationContext(),r);
    	
    	//mp.setAudioStreamType(AudioManager.MODE_NORMAL);
    	mp.setOnCompletionListener(new OnCompletionListener()
    	{
    		public void onCompletion(MediaPlayer mp)
    		{
    			count++;
    			looping(count);
    		}
    	});
    	
    	try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	mp.start();
    	
    	mp = null;
    }
  
  
  private void looping(int n)
  {
	  Uri a = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.a);
	  Uri b = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.b);
	  Uri c = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.c);
	  Uri d = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.d);
	  ArrayList<Uri> myArray = new ArrayList<Uri>();
	  
	  myArray.add(a);
	  myArray.add(b);
	  myArray.add(c);
	  myArray.add(d);
	  
	 audioLoop(myArray.get(n));
	  
	
  }
    

    
 //   private void manyAudioLoop()
//    {
//    	streamVolume = myAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        streamVolume = streamVolume / myAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        
//        if(loaded==true)
//        {
//        	int delay = 5000;
//        	
//        	
//        	Timer timer = new Timer();
//        	
//        	
//        	timer.scheduleAtFixedRate(new TimerTask()
//        	{
//        		public void run()
//        		{
//        		
//        			playSound(j);
//        			j++;
//        			
//        		}
//        	},0, delay);
//        	
//        }
//
//    }
//    
//    
//    public void playSound(int i)
//    {
//    	
//    	switch (i)
//    	{
//    	case 1:
//    		mySoundPool.play(mySoundMap.get(SoundA), streamVolume, streamVolume, 1, 0, 1f);
//    	 break;
//    	 
//    	case 2:
//    		mySoundPool.play(mySoundMap.get(SoundB), streamVolume, streamVolume, 1, 0, 1f);
//    		break;
//    		
//    	case 3:
//    		 mySoundPool.play(mySoundMap.get(SoundC), streamVolume, streamVolume, 1, 0, 1f);
//    		 soundDone = true;
//    		 break;
//    		
//    	}
//    	
//    	
//    }
//    
//    @SuppressLint("UseSparseArrays")
//	public void setAudio()
//    { 
//    	 myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//    	 
//    	 mySoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
//        
//         mySoundMap = new HashMap<Integer, Integer>();
//        	 
//         mySoundMap.put(SoundA,mySoundPool.load(this, R.raw.a,1));
//         mySoundMap.put(SoundB,mySoundPool.load(this, R.raw.b,1));
//        mySoundMap.put(SoundC,mySoundPool.load(this, R.raw.c,1));
//        
//       mySoundPool.setOnLoadCompleteListener(new OnLoadCompleteListener()
//       {
//       	 
//       	 public void onLoadComplete(SoundPool mySoundPool, int stream1, int status)
//      	 {
//       		 loaded = true;
//       		 manyAudioLoop();
//        	 }
//        });
//          
//        
//         mySoundMap.put(SoundD,mySoundPool.load(this, R.raw.d,1));
//        mySoundMap.put(SoundE,mySoundPool.load(this, R.raw.e,1)); 
//   }
//    
//    
   
    
//    private void locationChanged(Location location)
//    {
//    	speed = Float.toString(location.getSpeed());
//    	textView3.setText(speed);
//    	
//    }
}

