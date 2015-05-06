package com.paddlewarz.game.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper{
	
	  public static final String TABLE_SCORES = "scores";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_NAME = "name";
	  public static final String COLUMN_DATE = "date";
	  public static final String COLUMN_SCORE = "score";
	  

	  private static final String DATABASE_NAME = "scores.db";
	  private static final int DATABASE_VERSION = 3;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_SCORES + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_NAME
	      + " text, " + COLUMN_DATE + " numeric, " + COLUMN_SCORE + " integer);";

	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
		database.execSQL("DROP TABLE IF EXISTS goals");
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
	    onCreate(db);
	  }



}
