package com.paddlewarz.game.android;


import com.paddlewarz.game.android.R;
import com.paddlewarz.game.android.R.id;
import com.paddlewarz.game.android.R.layout;
import com.paddlewarz.game.android.R.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	static Context cs;
	int pos1,pos2,width,height;
	int imageheight,imagewidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_container);
		cs = getApplicationContext();
		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);

		final ImageView img_animation = (ImageView) findViewById(R.id.imageView1);
		final ImageView img_animation2 = (ImageView) findViewById(R.id.imageView2);
		final ImageView img_animation3 = (ImageView) findViewById(R.id.imageView3);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		pos1 = img_animation.getLeft();
		pos2 = img_animation.getTop();
		width = size.x;
		height = size.y;
		ViewTreeObserver vto = img_animation.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				img_animation.getViewTreeObserver().removeOnPreDrawListener(this);
				imageheight=img_animation.getMeasuredHeight();
				imagewidth = img_animation.getMeasuredWidth();
				TranslateAnimation animation = new TranslateAnimation(pos1,pos1,
						pos2, height-imageheight);
				animation.setDuration(1500);
				animation.setRepeatCount(20);
				animation.setRepeatMode(2);
				animation.setFillAfter(true);
				img_animation.startAnimation(animation);
				return true;
			}
		});

		pos1 = img_animation2.getLeft();
		pos2 = img_animation2.getTop();
		
		ViewTreeObserver vto2 = img_animation2.getViewTreeObserver();
		vto2.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				img_animation.getViewTreeObserver().removeOnPreDrawListener(this);
				imageheight=img_animation2.getMeasuredHeight();
				TranslateAnimation animation = new TranslateAnimation(pos1,pos1,
						pos2  , -height+imageheight);
				animation.setDuration(3000);
				animation.setRepeatCount(10);
				animation.setRepeatMode(2);
				animation.setFillAfter(true);
				img_animation2.startAnimation(animation);
				return true;
			}
		});
		pos1 = img_animation3.getLeft();
		//pos2 = img_animation3.getTop();
		
		ViewTreeObserver vto3 = img_animation3.getViewTreeObserver();
		vto3.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				img_animation3.getViewTreeObserver().removeOnPreDrawListener(this);
				imagewidth=img_animation3.getMeasuredWidth();
				imageheight=img_animation3.getMeasuredHeight();
				int imagewidth2= img_animation.getMeasuredWidth();
				TranslateAnimation animation = new TranslateAnimation(pos1+imagewidth2, width-(imagewidth+imagewidth2),
						height-imageheight, imageheight);
				animation.setDuration(3000);
				animation.setRepeatCount(10);
				animation.setRepeatMode(2);	
				animation.setFillAfter(true);
				animation.setZAdjustment(AnimationSet.ZORDER_BOTTOM);
				img_animation3.startAnimation(animation);
				return true;
			}
		});
		


		findViewById(R.id.Button02).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(MainActivity.this, AndroidLauncher.class);
				startActivity(i);
				//Toast.makeText(getApplicationContext(), "single player", Toast.LENGTH_SHORT).show();
			}

		});

		findViewById(R.id.Button01).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(MainActivity.this, Multiplayer.class);
				startActivity(i);
				//Toast.makeText(getApplicationContext(), "multiplayer", Toast.LENGTH_SHORT).show();
			}

		});
		findViewById(R.id.Button05).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				Toast.makeText(getApplicationContext(), "options", Toast.LENGTH_SHORT).show();
			}

		});
		findViewById(R.id.Button03).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(MainActivity.this, Scores.class);
				startActivity(i);
				//Toast.makeText(getApplicationContext(), "Top Scores", Toast.LENGTH_SHORT).show();
			}

		});
		findViewById(R.id.Button04).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(MainActivity.this, Instructions.class);
				startActivity(i);
				//Toast.makeText(getApplicationContext(), "instructions", Toast.LENGTH_SHORT).show();
			}

		});
		findViewById(R.id.Button06).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				MainActivity.this.finish();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



}
