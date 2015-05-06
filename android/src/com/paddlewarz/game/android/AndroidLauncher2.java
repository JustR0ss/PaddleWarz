package com.paddlewarz.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.paddlewarz.game.mainactivity;

public class AndroidLauncher2 extends AndroidApplication {
	Bundle b;
	boolean flag;
	String ip;
	int port;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		b = this.getIntent().getExtras();
		if(b!=null){
			System.out.println("bundle is not null");
			flag= Boolean.parseBoolean(b.getString("flag"));
			if(flag==false){
				ip= b.getString("address");
				port= Integer.parseInt(b.getString("port"));
			}
		}
		else{
			flag=false;
		}
		System.out.println(flag);
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		if(ip!=null){
			System.out.println("joined");
			initialize(new mainactivity(new AndroidLeaderboard(),"multiplayer",flag,ip,port), config);
			
		}else{
			System.out.println("started");
			initialize(new mainactivity(new AndroidLeaderboard(),"multiplayer",flag), config);

		}
	}
}
