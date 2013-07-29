package com.chknetsc.gleitzeitrechner;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TimeLineActivity extends Activity {
	
	private static final String TAG = TimeLineActivity.class.getSimpleName();
	private boolean mockup = true;
	
	// Nestet Class mit List Elementen
	public class ListElement {
		public String listNr = "";
		public String date = "";
		public String worktime = "";
		
		public ListElement() {
			worktime = "--hinzufügen--";
		}
		
		public ListElement(String nr, String save) {
			listNr = nr;
			date = DateFormat.getDateInstance().format(new Date());
			worktime = save;
		}
		
		public void setListElement(String Nr, String time) {
			listNr = Nr + ".";
			worktime = time;
			date = DateFormat.getDateInstance().format(new Date());
		}
	}
	
	private TextView weekTime;
	private Button delete;
	private List<ListElement> list = new ArrayList<ListElement>();
	private ArrayAdapter<ListElement> adapter;
	private ListView lv;
	private TimeLineOpenHandler timelinehandler;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		log("TimeLine erstellt");
		
		weekTime = (TextView) findViewById(R.id.weekTimeT);
		delete = (Button) findViewById(R.id.delete);
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				timelinehandler.deleteDB();
			}
			
		});
		
		// ViewListe Init
		lv = (ListView) findViewById(R.id.listView);
		// Adapter Init mit Layout und Liste 
		adapter = new TimeLineAdapter(this, R.layout.activity_time_line, list);
		// Adapter mit Liste verknüpfen
		lv.setAdapter(adapter);
		log("ListView und Adapter erstellt");
		
		// Elemente aus Datenspeicher hollen
		timelinehandler = new TimeLineOpenHandler(this);
		loadWorkTimes();
		
		// Wochenzeit berechnen
		calculateWeekTime();
		log("Calulate WeekTime beendet");
		
		// Ablageort der Dateien ermitteln und ausgeben
		File f = getFilesDir();
		log("Speicherort der Elemente : " + f.getAbsolutePath());
		
		lv.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(mockup == true) {
					String result = "8:20";
					
					// Anlegen von neuem Eintrag in Liste
					Integer position = adapter.getCount();
					adapter.getItem(position-1).setListElement((position).toString(), result);
					adapter.add( new ListElement());
					
					// Eintrag speichern
					timelinehandler.insert(result);
					//Toast.makeText(, "Gespeichert", Toast.LENGTH_SHORT).show();
					
					// Wochenzeit berechnen
					calculateWeekTime();
					log("Calulate WeekTime beendet");
					
				} else {
					Intent nextCalc = new Intent(getApplicationContext(), RechnerActivity.class);
					log("Auf Rechner wechseln");
					startActivityForResult(nextCalc,1);
				}
			}
		});
	}

	private void loadWorkTimes() {
		log(" LoadWorkTimes gestartet");
		Cursor cu = timelinehandler.select();
		Integer position = 0;
		while(cu.moveToNext()) {
			log("Position = " + position);
			log("EintragNr: " + cu.getString(0) + " Arbeitszeit = " + cu.getString(1));
			adapter.add(new ListElement(cu.getString(0), cu.getString(1)));
			position++;
		}
		adapter.add( new ListElement());
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {	  
			if(resultCode == RESULT_OK){      
				String result = data.getStringExtra("com.chknetsc.RechnerActivity.result");    
				log("AktivitiyEmpfang = " + result);
				// Anlegen von neuem Eintrag in Liste
				Integer position = adapter.getCount();
				adapter.getItem(position-1).setListElement((position).toString(), result);
				adapter.add( new ListElement());
				
				// Eintrag speichern
				timelinehandler.insert(result);
				Toast.makeText(this, "Gespeichert", Toast.LENGTH_SHORT).show();
				
				// Wochenzeit berechnen
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
		int hour = (int) ((weekWork) / (1000*60*60));
		Integer min = (int) (weekWork - (1000*60*60*hour)) / (1000*60);
		
		log("Hour = " + hour);
		log("Minute = " + min);
		
		if(min.toString().length() < 2) {
			weekTime.setText(hour + ":" + min + "0");
		} else {
			weekTime.setText(hour + ":" + min);
		}
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
	
	protected void onDestroy() {
		super.onDestroy();
		timelinehandler.close();
	}

}
