package com.chknetsc.gleitzeitrechner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RechnerActivity extends Activity {
	
	private static final String TAG = RechnerActivity.class.getSimpleName();
	
	private EditText startTime;
	private EditText breakTime;
	private EditText endTime;
	private TextView result;
	private Button calc;
	private Button save;
	private TextWatcher watch;
	private boolean calcready;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rechner);
		
		log("Rechner gestartet");
		startTime = (EditText) findViewById(R.id.startTime);
		log("Start ini");
		breakTime = (EditText) findViewById(R.id.breakTime);
		log("break ini");
		endTime = (EditText) findViewById(R.id.endTime);
		result = (TextView) findViewById(R.id.textTotalTime);
		calc = (Button) findViewById(R.id.buttonCalc);
		save = (Button) findViewById(R.id.speichern);
		log("View Elmente Intit");
		
		watch = new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.length() > 0) {
					calcready = true;
					calc.setEnabled(true);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}
		};
		
		startTime.addTextChangedListener(watch);
		breakTime.addTextChangedListener(watch);
		endTime.addTextChangedListener(watch);
		
		calc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(calcready) {
					calculateTotalTime();
				}
			}
		});
		
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO Setze Result Text in auferufenes ListenElement Object
				Intent returnIntent = new Intent();
				returnIntent.putExtra("com.chknetsc.RechnerActivity.result",result.getText().toString());
				setResult(RESULT_OK,returnIntent);     
				finish();
			}
		});
	}
	
	private void calculateTotalTime() {
		// Values Intit
		long gesamt = 0;
		Integer min = 0;
		Integer hour = 0;
		
		SimpleDateFormat df = new SimpleDateFormat("HHmm");
		Date breakD = null;
		Date startD = null;
		Date endD = null;
		
		try {
			// Check if Text Field is empty an fill it with a Time
			if(!startTime.getText().toString().isEmpty()) {
				startD = df.parse(startTime.getText().toString());
			} else {
				startD = df.parse("0000");
			}
			log("startD = " + startD);
			
			if(!endTime.getText().toString().isEmpty()) {
				endD = df.parse(endTime.getText().toString());
			} else {
				endD = df.parse("0000");
			}
			log("endD = " + endD);
			
			if(!breakTime.getText().toString().isEmpty()) {
				breakD = df.parse(breakTime.getText().toString());
			} else {
				breakD = df.parse("0000");
			}
			log("breakD = " + breakD);
		
		} catch (ParseException e) {
			Log.e(TAG, "Fehler Zeitumwandlung nicht möglich", e);
			e.printStackTrace();
		}
		
		// Calc Total Working Time and convert it in Hour and Minutes
		gesamt = (endD.getTime() - startD.getTime()) - breakD.getTime();
		log("Gesamt = " + gesamt + "ms");
		hour = (int) ((gesamt) / (1000*60*60));
		min = (int) (gesamt - (1000*60*60*hour)) / (1000*60);
		log("Gesamt = " + hour + "Stunden");
		log("Gesamt = " + min + "min");
		
		if(min.toString().length() < 2) {
			result.setText(hour + ":" + min + "0");
		} else {
			result.setText(hour + ":" + min);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rechner, menu);
		return true;
	}
	
	private void log(String output) {
		Log.i(TAG, output);
	}

}
