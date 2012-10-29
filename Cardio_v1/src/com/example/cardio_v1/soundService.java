package com.example.cardio_v1;

import java.io.IOException;
import java.util.ArrayList;

import com.example.cardio_v1.GPSservice.gpsbinder;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.IntentFilter;


public class soundService extends Service

{
	
	//array list for one of the lists of sounds
	private ArrayList<Uri> myArray;
	//array list for the second set of sounds
	private ArrayList<Uri> array2;
	
	private int count = 0;
	private boolean listMade = false;
	private int count2 = 0;
	private boolean listMade2 = false;
	int maxVolume = 100;
	MediaPlayer mp;
	MediaPlayer mp2;
	int volCounter = 0;
	AudioManager audioManager;
	 Uri x;
	 
	 private final IBinder bind = new soundBinder();
	
	 
	 public class soundBinder extends Binder {
			soundService getService() {
				return soundService.this;
			}
		}
	 
	public IBinder onBind (Intent arg0)
	{
		return bind;
	}
	
	public int onStartCommand (Intent intent, int flags, int startID)
	{
		Toast.makeText(this, "Sound Service Started", Toast.LENGTH_LONG).show();
		setList();
		setList2();
		looping(0);
		looping2(0);
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("volume");
		
		registerReceiver(intentReceiver, intentFilter);

		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		
		
		 x = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.track);
		 
	    	mp = MediaPlayer.create(getApplicationContext(),x);
	    	
	    	
	    	mp.start();
	    	
	    	mp = null;
		 
		return START_STICKY;
	}
	
	
	
	private BroadcastReceiver intentReceiver = new BroadcastReceiver()
	{
		

		@Override
		public void onReceive(Context context, Intent intent) 
		{
			
			//int vol = intent.getExtras().getInt("volume");
			
			//float volume =(float)(1-(Math.log(maxVolume-vol)/Math.log(maxVolume)));
			
			try
			{
				//audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,vol,0);
			}
			catch (Exception ex)
			{
				Log.d("vol", ex.toString());
			}
		}
	};
	
	
	
	private void  changeVolume()
	{
		if (volCounter == 100)
		{
		 volCounter = 0;
		}
		else
		{
		int vol = volCounter;
		
		float volume =(float)(1-(Math.log(maxVolume-vol)/Math.log(maxVolume)));
		
		try
		{
		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,vol,0);
//		mp.setVolume(volume, volume);
//		mp2.setVolume(volume, volume);
		}
		catch (Exception ex)
		{
			Log.d("vol", ex.toString());
		}
			volCounter+=10;
		}
	}
	
	
	
	public void onDestroy()
	{
		super.onDestroy();
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
		
	}
	synchronized public void audioLoop( Uri r)
    {
    	
    	
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
  
  private void setList()
  {
	  Uri a = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.a);
	  Uri b = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.b);
	  Uri c = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.c);
	  Uri d = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.f);
	  Uri e = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.e);
	  myArray = new ArrayList<Uri>();
	  
	  myArray.add(a);
	  myArray.add(b);
	  myArray.add(c);
	  myArray.add(d);
	  myArray.add(e);
	  
	  listMade = true;
  }
  
  
  private void looping(int n)
  {
//	  Uri a = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.a);
//	  Uri b = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.b);
//	  Uri c = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.c);
//	  Uri d = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.d);
//	  Uri e = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.e);
//	  ArrayList<Uri> myArray = new ArrayList<Uri>();
//	  
//	  myArray.add(a);
//	  myArray.add(b);
//	  myArray.add(c);
//	  myArray.add(d);
	 int m = myArray.size();
	  
	  if (listMade == true && n <= m - 1)
	  {
//	  
		  audioLoop(myArray.get(n));
		 
	  }
	  
	  else if (listMade == false )
	  {
		  
	  }
	  
	  else if(n >= m)
	  {
		  count = 0;
		  n=0;
		  listMade = true;
		  audioLoop(myArray.get(n));
	  }

	
  }
  
  synchronized public void audioLoop2( Uri r)
  {
  	
  	
  	mp2 = MediaPlayer.create(getApplicationContext(),r);
  	
  	//mp.setAudioStreamType(AudioManager.MODE_NORMAL);
  	mp2.setOnCompletionListener(new OnCompletionListener()
  	{
  		public void onCompletion(MediaPlayer mp2)
  		{
  			
  			count2++;
  			looping2(count2);
  		}
  	});
  	
  	try {
			mp2.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	
  	mp2.start();
  	
  	mp2 = null;
  }

private void setList2()
{
	  Uri a = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.e);
	  Uri b = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.e);
	  Uri c = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.e);
	  Uri d = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.e);
	  Uri e = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.e);
	  array2 = new ArrayList<Uri>();
	  
	  array2.add(a);
	  array2.add(b);
	  array2.add(c);
	  array2.add(d);
	  array2.add(e);
	  
	  listMade2 = true;
}


private void looping2(int n)
{
//	  Uri a = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.a);
//	  Uri b = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.b);
//	  Uri c = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.c);
//	  Uri d = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.d);
//	  Uri e = Uri.parse("android.resource://com.example.cardio_v1/" + R.raw.e);
//	  ArrayList<Uri> myArray = new ArrayList<Uri>();
//	  
//	  myArray.add(a);
//	  myArray.add(b);
//	  myArray.add(c);
//	  myArray.add(d);
	 int m = array2.size();
	  
	  if (listMade2 == true && n <= m - 1)
	  {
//	  
		  audioLoop(array2.get(n));
	  }
	  
	  else if (listMade2 == false )
	  {
		  
	  }
	  
	  else if(n >= m)
	  {
		  count2 = 0;
		  n=0;
		  listMade2 = true;
		  audioLoop(array2.get(n));
	  }

	
}
}
