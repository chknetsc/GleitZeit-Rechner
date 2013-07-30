package com.chknetsc.gleitzeitrechner;

import java.text.DateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TimeLineOpenHandler extends SQLiteOpenHelper {
	
	private static final String TAG = TimeLineOpenHandler.class.getSimpleName();
	
	// Datenbank anlegen
		// Name Datenbank
	private static String NAME = "timeline.db";
	public static String DATAB_NAME = "timeline";
		// TabellenEinträgeNamen
	public String ID = "id";
	private String DATE = "date";
	public static String WORK_TIME = "worktime";
	
	// Tabelle anlegen als String befehl
	private String CREATE_TABLE_TIMELINE = "CREATE TABLE "+ DATAB_NAME + " (" + 
			ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			DATE + " VARCHAR(20), " + 
			WORK_TIME + " VARCHAR(20));";
	// Tabelle löschen
	private String DROP_TABLE_TIMELINE = "DROP TABLE IF EXISTS " + DATAB_NAME;
	

	TimeLineOpenHandler(Context context) {
		super(context, NAME, null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		log("Tabelle erstellt");
		db.execSQL(CREATE_TABLE_TIMELINE);
	}
	
	public void drop() {
		log("Tabelle löschen");
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(DROP_TABLE_TIMELINE);
		onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		log("Tabelle : " + oldVersion + " durch Tabelle : " + newVersion + " ersetzt");
		db.execSQL(DROP_TABLE_TIMELINE);
		onCreate(db);
	}
	
	public void insert(String worktime) {
		long rowId = -1;
		try {
			// Datenbank öffnen
			SQLiteDatabase db = getWritableDatabase();
			log("Datenbank geöffnet");
			// Übergebende Werte eintragen
			ContentValues values = new ContentValues();
			values.put(DATE, DateFormat.getDateInstance().format(new Date()));
			values.put(WORK_TIME, worktime);
			log("Values eingefügt");
			// In Tabelle einfügen
			rowId = db.insert(DATAB_NAME, null, values);
		} catch (SQLiteException e) {
			Log.e(TAG, "insert()", e);
		} finally {
			log("insert(): rowId = " + rowId);
		}
	}
	
	public Cursor select() {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cu = db.query(DATAB_NAME, null, null, null, null, null, null);
		return cu;
	}
	
	private void log(String output) {
		Log.i(TAG, output);
	}
	
	public void deleteDB() {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(DATAB_NAME, null, null);
		db.close();
	}
}
