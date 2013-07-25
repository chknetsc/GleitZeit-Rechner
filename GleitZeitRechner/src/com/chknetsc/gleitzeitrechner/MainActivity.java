package com.chknetsc.gleitzeitrechner;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private Button start;
	private Button end;
	private TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		log("Aktiviti gestartet");
		
		start = (Button) findViewById(R.id.start);
		end = (Button) findViewById(R.id.ende);
		text = (TextView) findViewById(R.id.mainName);
		
		start.setText(R.string.start_button);
		end.setText(R.string.end_button);
		text.setText(R.string.app_name);
		
		log("Button init");
		// Push Start Button to Switch to next Screen 
		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent next = new Intent(getApplicationContext(), RechnerActivity.class);
				log("Auf Kalender wechseln");
				startActivity(next);
			}
		});
		
		// Push End Button to close the App
		end.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log("App beenden");
				finish();
			}
		});
	}
	
	private void log(String output) {
		Log.i(TAG, output);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
