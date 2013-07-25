package com.chknetsc.gleitzeitrechner;

import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class TimeLineAdapter extends ArrayAdapter<String> {
	
	private static final String TAG = TimeLineAdapter.class.getSimpleName();
	
	private final LayoutInflater inflator;

	/* Konstructor setzt Context von aufgerufener Klasse
	 * sowie die ID des Layout (kp wozu) 
	 * und die zu übergebenden Werte in einer Liste  */
	public TimeLineAdapter(Context context, int textViewResourceId, List<String> id) {
		super(context, textViewResourceId, id);
		inflator = LayoutInflater.from(context);
		log("Konstructor Adpater gestartet");
	}
	

	/* Erstellt einen Listeneintrag aus den gegebenen Daten */
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        
        // Erstellt eine eues View Element aus dem Listen Element Layout
        if (view == null) {
            view = inflator.inflate(R.layout.list_element, parent, false);
            log("Entfallte View " + position);
        }

        /* Erstelle einzelne ViewListenelemente 
         * anhand der PositionsNummer
         * und füllt Elemente mit den in Item enthaltenen übergebenen Daten */
        log("Lege ListenView Elmente " + position + " an");
        String item = getItem(position);
        if (item!= null) {
        	// Init Listenelemente
            TextView itemView = (TextView) view.findViewById(R.id.textView1);
            // Fülle Listenelemente
            itemView.setText(String.format("%s Produkt", item.toString()));;
         }
        return view;
    }
    
    private void log(String output) {
		Log.i(TAG, output);
	}
}
