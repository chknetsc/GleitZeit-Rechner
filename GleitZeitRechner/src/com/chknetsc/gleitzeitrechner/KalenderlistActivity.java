package com.chknetsc.gleitzeitrechner;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class KalenderlistActivity extends Activity {
	
	//private Button calc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent nextR = new Intent(getApplicationContext(), RechnerActivity.class);
		startActivity(nextR);
		/*
		setContentView(R.layout.activity_kalenderlist);
		 
		calc = (Button) findViewById(R.id.rechnen);
		calc.setText(R.string.calculate);
		
		//TODO Kalendertage in Liste eintragen und mit Indent den Tag an Rechner übergeben.
		
		calc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent nextR = new Intent(getApplicationContext(), RechnerActivity.class);
				startActivity(nextR);
			}
		});
		*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kalenderlist, menu);
		return true;
	}
}
