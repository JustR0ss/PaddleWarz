package com.paddlewarz.game;

import com.badlogic.gdx.Input.TextInputListener;

public class MyTextInputListener implements TextInputListener {
		String name ;
		Leaderboard lb;
	   
	   public MyTextInputListener(Leaderboard lblb){
		   this.lb=lblb;
			 
	   }
	   public void input (String text) {
		   lb.submitScore(text, mainactivity.playerscore);
	   }

	   
	   @Override
	   public void canceled () {
		   System.out.println("Cancelled");
	   }
}