package com.paddlewarz.game.android;

import android.content.Context;

import com.paddlewarz.game.Leaderboard;

public class AndroidLeaderboard implements Leaderboard {
	   private final LeaderboardServiceApi service;
	   public AndroidLeaderboard() {
	      // Assuming we can instantiate it like this
	      service = new LeaderboardServiceApi(MainActivity.cs);
	   }

	   public void submitScore(String user, int score) {
	      service.submitScore(user, score);
	   }
	   public void addScore(String name, String date,int score){
		   service.addScore(name, date,score);
	   }
	}
