package com.paddlewarz.game.android;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class Instructions extends Activity{

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_instructions);
	    
	    TabHost tabs= (TabHost)findViewById(R.id.tabhost);
		tabs.setup();
		tabs.getTabWidget().setStripEnabled(false);
		TabHost.TabSpec spec0=tabs.newTabSpec("tag0");
		spec0.setContent(R.id.tab0);
		spec0.setIndicator("SinglePlayer");
		tabs.addTab(spec0);
        TabHost.TabSpec spec1=tabs.newTabSpec("tag1");
		spec1.setContent(R.id.tab1);
		spec1.setIndicator("MultiPlayer");
		tabs.addTab(spec1);
		
		for(int i=0;i<tabs.getTabWidget().getChildCount();i++) 
	    {
			
	        TextView tv = (TextView) tabs.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
	        tabs.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#000000"));
	        System.out.println(tv.getText());
	        tabs.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector);
	        tv.setTextColor(Color.parseColor("#12e400"));
	        tv.setGravity(Gravity.CENTER);

	        tv.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT; 
	        tv.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
	        
	    } 
	}
}
