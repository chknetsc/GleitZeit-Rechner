package com.chknetsc.gleitzeitrechner;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TimeLineActivity extends Activity {
	
	private static final String TAG = TimeLineActivity.class.getSimpleName();
	
	List<String> id = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	ListView lv;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		log("TimeLine erstellt");
		
		for(int i = 1; i <= 5; i++) {
			id.add(i + ". ");
		}
		log("Liste mit Werten gefüllt");
		
		lv = (ListView) findViewById(R.id.listView);
		adapter = new TimeLineAdapter(this, R.layout.activity_time_line, id);
		lv.setAdapter(adapter);
		log("ListView und Adapter erstellt");
		
		lv.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent nextCalc = new Intent(getApplicationContext(), RechnerActivity.class);
				log("Auf Rechner wechseln");
				startActivity(nextCalc);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time_line, menu);
		return true;
	}
	
	private void log(String output) {
		Log.i(TAG, output);
	}

}
