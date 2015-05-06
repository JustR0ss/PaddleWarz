package com.paddlewarz.game.android;

public class Returns {
	  private int _id;
	  private String name;
	  private String date;
	  private int score;
	  
 public int getId() {
	    return _id;
	  	}

	  public void setId(int id) {
	    this._id = id;
	  	}

	  public String getName() {
	    return name;
	  	}

	  public void setName(String name) {
	    this.name = name;
	  	}
	  public String getDate() {
		    return date;
	  	}

	  public void setDate(String date) {
		    this.date = date;
	  	}
	  
	  public int getScore() {
			    return score;
		}

	  public void setScore(int score) {
			    this.score = score;
		}
	  
	  
	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return name+ ":" + date +":" + score;
	  }
	  
	} 
