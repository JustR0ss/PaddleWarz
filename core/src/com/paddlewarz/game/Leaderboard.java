package com.paddlewarz.game;

public interface Leaderboard {
	   public void submitScore(String user, int score);
	   public void addScore(String name, String date,int score);
}
