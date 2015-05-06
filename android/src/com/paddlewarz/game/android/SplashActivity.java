package com.paddlewarz.game.android;

import com.paddlewarz.game.android.R;
import com.paddlewarz.game.android.R.layout;
import com.paddlewarz.game.android.R.raw;
import com.paddlewarz.game.android.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
 
public class SplashActivity extends Activity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;
    MediaPlayer mPlayer;
    SoundPool sp;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    		        // making it full screen
    		        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    		       
        setContentView(R.layout.activity_splash);
        
       sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		/** soundId for Later handling of sound pool **/
		 int soundId = sp.load(this, R.raw.intro, 1); // in 2nd param u have to pass your desire ringtone

		 sp.play(soundId, 1, 1, 0, 0, 1);

		 mPlayer = MediaPlayer.create(this, R.raw.intro); // in 2nd param u have to pass your desire ringtone
		 //mPlayer.prepare();
		 mPlayer.start();
		
		 new Handler().postDelayed(new Runnable() {
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
            	
            	mPlayer.stop();
            	mPlayer.release();
            	sp.release();
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
 
}
