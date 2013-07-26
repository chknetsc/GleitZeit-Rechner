package com.chknetsc.gleitzeitrechner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import android.widget.TextView;

public class TimeLineActivity extends Activity {
	
	private static final String TAG = TimeLineActivity.class.getSimpleName();
	
	// Nestet Class mit List Elementen
	public class ListElement {
		public String listNr = "";
		public String date = "";
		public String worktime = "";
		
		public ListElement() {
			worktime = "--hinzufügen--";
		}
		
		public void setListElement(String Nr, String time) {
			listNr = Nr + ".";
			worktime = time;
			date = DateFormat.getDateInstance().format(new Date());
		}
	}
	
	TextView weekTime;
	List<ListElement> list = new ArrayList<ListElement>();
	ArrayAdapter<ListElement> adapter;
	ListView lv;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		log("TimeLine erstellt");
		
		weekTime = (TextView) findViewById(R.id.weekTimeT);
		
		// Listenelement setzen
		list.add(new ListElement());
		log("Liste mit Werten gefüllt");
		
		// ViewListe Init
		lv = (ListView) findViewById(R.id.listView);
		// Adapter Init mit Layout und Liste 
		adapter = new TimeLineAdapter(this, R.layout.activity_time_line, list);
		// Adapter mit Liste verknüpfen
		lv.setAdapter(adapter);
		log("ListView und Adapter erstellt");
		
		calculateWeekTime();
		log("Calulate WeekTime beendet");
		
		lv.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent nextCalc = new Intent(getApplicationContext(), RechnerActivity.class);
				log("Auf Rechner wechseln");
				startActivityForResult(nextCalc,1);
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {	  
			if(resultCode == RESULT_OK){      
				String result = data.getStringExtra("com.chknetsc.RechnerActivity.result");    
				log("AktivitiyEmpfang = " + result);
				
				Integer position = adapter.getCount();
				adapter.getItem(position-1).setListElement((position).toString(), result);
				adapter.add( new ListElement());
				calculateWeekTime();
				log("Calulate WeekTime beendet");
		     }
		  }
		}
	
	public void calculateWeekTime() {
		long weekWork = 0;
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		long tmp = 0;
		// Sum the single WokingTimes to a WeekTime in ms
		for(int i = 0; i < (adapter.getCount()-1); i++) {
			try {
				tmp = (df.parse(list.get(i).worktime)).getTime();
				log("Temporärere Zeit :" + tmp + "ms");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			weekWork = weekWork + tmp;
		}
		log("WeekTime Zeit :" + weekWork + "ms");
		
		int days = (int) (weekWork / (1000*60*60*24));
		int hour = (int) ((weekWork - (1000*60*60*24*days)) / (1000*60*60));
		int min = (int) (weekWork - (1000*60*60*24*days) - (1000*60*60*hour)) / (1000*60);
		
		log("Hour = " + hour);
		log("Minute = " + min);
		
		weekTime.setText(hour + ":" + min);
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
