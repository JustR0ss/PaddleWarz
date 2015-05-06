package com.paddlewarz.game.android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;

public class DataSource {
	// Database fields
		String today;
		int counter;
		  Date date = new Date();
		  Returns newReturns;
		  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		  private SQLiteDatabase database;
		  private MySQLiteHelper dbHelper;
		  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
	      MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_DATE,
	      MySQLiteHelper.COLUMN_SCORE };

	  public DataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	    
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Returns createScore(String name,int score) {
	    ContentValues values = new ContentValues();
	    today=dateFormat.format(date).toString();
	    
	    List<Returns> returnall = new ArrayList<Returns>();
	    Cursor cursor2 = database.query(MySQLiteHelper.TABLE_SCORES,
	        allColumns, "score >"+score, null, null, null, null);
	    counter=0;
	    cursor2.moveToFirst();
	    while (!cursor2.isAfterLast()) {
	    	Returns returnsx = cursorToComment(cursor2);
	    	counter++;
	    	returnall.add(returnsx);
	      cursor2.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor2.close();
			  
		  if(counter<10){
			  values.put(MySQLiteHelper.COLUMN_NAME, name);
			    values.put(MySQLiteHelper.COLUMN_DATE, today);
			    values.put(MySQLiteHelper.COLUMN_SCORE, score);
			    
			    long insertId = database.insert(MySQLiteHelper.TABLE_SCORES, null,
			        values);
			    Cursor cursor = database.query(MySQLiteHelper.TABLE_SCORES,
			        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
			        null, null, null);
			    //System.out.println("Goal added with id: " + insertId);
			    cursor.moveToFirst();
			    newReturns = cursorToComment(cursor);
			    cursor.close();
			    String[] args={MySQLiteHelper.TABLE_SCORES};
			    Cursor crs=database.rawQuery("DELETE FROM ? ORDER BY score DESC,date desc LIMIT 11, 18446744;",args);
			    
		  }
		  return newReturns;
	  }

	  
	  
	  public List<Returns> getAllReturns() {
		  
		List<Returns> returnall = new ArrayList<Returns>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_SCORES,
	        allColumns, null, null, null, null, MySQLiteHelper.COLUMN_SCORE +" DESC");

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	Returns returnsx = cursorToComment(cursor);
	    	returnall.add(returnsx);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return returnall;
	  }

	  public void updateGoal(int score, String name){
		  ContentValues values = new ContentValues();
		  //values.put(MySQLiteHelper.COLUMN_RESULT, result);
		  //String where = "_id ="+Integer.toString(id);
		  //database.update(MySQLiteHelper.TABLE_SCORES, values, where, null);
		  
	  }
	  

	  public List<Returns> getAllReturns(Integer id) {
		  String where = "_id = '"+id+"'";
		  
	    List<Returns> returnall = new ArrayList<Returns>();
	    
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_SCORES,
	        allColumns, where, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	Returns returnsx = cursorToComment(cursor);
	    	returnall.add(returnsx);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return returnall;
	  }

	  private Returns cursorToComment(Cursor cursor) {
		  Returns returns = new Returns();
		  returns.setId(cursor.getInt(0));
		  returns.setName(cursor.getString(1));
		  returns.setDate(cursor.getString(2));
		  returns.setScore(cursor.getInt(3));
		  return returns;
	  }

	
}
