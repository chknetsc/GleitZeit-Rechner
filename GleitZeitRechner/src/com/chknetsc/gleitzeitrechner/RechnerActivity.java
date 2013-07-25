package com.chknetsc.gleitzeitrechner;

import android.os.Bundle;
import android.app.Activity;
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
	private TextWatcher watch;
	private boolean calcready;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rechner);
		
		startTime = (EditText) findViewById(R.id.startTime);
		breakTime = (EditText) findViewById(R.id.breakTime);
		endTime = (EditText) findViewById(R.id.endTime);
		result = (TextView) findViewById(R.id.textTotalTime);
		calc = (Button) findViewById(R.id.buttonCalc);

		watch = new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.length() > 0) {
					calcready = true;
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
					// Werte prüfen
					int ende = 0;
					int start = 0;
					int brea = 0;
					
					// Check if Text input is Empty
					if(!endTime.getText().toString().isEmpty()) {
						ende = Integer.valueOf(endTime.getText().toString());
					}
					if(!startTime.getText().toString().isEmpty()) {
						start = Integer.valueOf(startTime.getText().toString());
					}
					if(!breakTime.getText().toString().isEmpty()) {
						brea = Integer.valueOf(breakTime.getText().toString());
					}
					
					log("Ende Zeit: " + ende);
					log("start Zeit: " + start);
					log("brea Zeit: " + brea);
					
					// Gesamtzeitberechne
					ende = (ende - start) - brea;
					// Ausgabe Gesammtzeit
					result.setText("Gesamtzeit: " + ende);
				}
			}
		});
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
