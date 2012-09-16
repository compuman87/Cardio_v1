package com.example.cardio_v1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

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
        		
        
        
        //textView.setTextSize(40);
        textView1.setText(message1);
        
        //textView.setTextSize(40);
        textView2.setText(message2);
        //setContentView(textView);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_display_message, menu);
        return true;
    }
}
