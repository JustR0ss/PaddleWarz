package com.paddlewarz.game.android;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.paddlewarz.game.android.R;
import com.paddlewarz.game.android.R.id;
import com.paddlewarz.game.android.R.layout;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Scores extends Activity{
	ListView listView,listView1;
	private DataSource datasource;
	CustomAdapter adapter,adapter2;
	private String[] scoreID;
	//Date date = new Date();
	//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	
	ArrayList<HashMap<String, String>> scoresList = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map;
	String TAB_2_TAG = "tag2";
	  
	  public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_score);
		    listView = (ListView) findViewById(R.id.list0);
		    
		    
		    datasource = new DataSource(this);
		    datasource.open();
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
			TabHost.TabSpec spec2=tabs.newTabSpec("tag2");
			spec2.setContent(R.id.tab2);
			spec2.setIndicator("TopPlayer");
			tabs.addTab(spec2);
			//System.out.println(tabs.getTabWidget().getChildCount());
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
			//
			
			
			
			tabs.setOnTabChangedListener(new OnTabChangeListener(){
				@Override
				public void onTabChanged(String tabId) {
				    if(TAB_2_TAG.equals(tabId)) {
				        //destroy earth
				    	//Toast.makeText(getApplicationContext(), "Retrieving Scores...", Toast.LENGTH_SHORT).show();
				    	// WebServer Request URL
		                String serverURL = "http://54.76.199.8:8080/ScoresService/services/ReturnScores/returnScores";
		                 
		                // Use AsyncTask execute Method To Prevent ANR Problem
		                new MultiScore().execute(serverURL);
				    }
				    
				}});
		    List<Returns> values = datasource.getAllReturns();
		    adapter = new CustomAdapter(this, R.layout.layout_item, values);
		    listView.setAdapter(adapter);
		    //getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			//registerForContextMenu(getListView());
		    
	  }
	  
	  class MultiScore extends AsyncTask<String, Void, Void>{
		  
		  URL url;
			int[] rank;
			String[] name;
			String[] date;
			int[] score;
			List<Returns> values2 ;
			public ProgressDialog Dialog ;
			 
			 ListView listView1 = (ListView) findViewById(R.id.list2);
			 String Error = null;
			 
			 protected void onPreExecute() {
		            // NOTE: You can call UI Element here.
		              
		            //Start Progress Dialog (Message)
				Dialog = new ProgressDialog(Scores.this);
		            Dialog.setMessage("Please wait..");
		            Dialog.show();
		             
		            //if sending data
		            /*try{
		                // Set Request parameter
		                data +="&" + URLEncoder.encode("data", "UTF-8") + "="+serverText.getText();
		                     
		            } catch (UnsupportedEncodingException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
		             */
		        }
			 
			@Override
			protected Void doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				try {
					url = new URL(arg0[0]);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				StringReturned st = new StringReturned(url);
				try {
					String mydata = st.getContents().toString();
					Pattern pattern = Pattern.compile("<ns:return>(.*?)</ns:return>");
					Matcher matcher = pattern.matcher(mydata);
					if (matcher.find())
					{
						String prepare = matcher.group(1).toString().replace("[","").replace("]","");
						String[] split1 = prepare.split(",");
					    rank = new int[split1.length];
					    name=	new String[split1.length];
					    date=	new String[split1.length];
					    score =new int[split1.length];
					    for(int i =0; i<split1.length;i++){
					    	
						    
					    	String[] split2 = split1[i].split(":");
					    	rank[i]=i+1;
					    	name[i]=split2[0];
					    	System.out.println(split2[1]);
					    	Date datex = new SimpleDateFormat("yyyy-MM-dd").parse(split2[1]);
					    	date[i]=new SimpleDateFormat("dd-MM-yyyy").format(datex);
					    	System.out.println(date[i]);
					    	score[i]=Integer.parseInt(split2[2]);
					    }
						
					}
					
					//System.out.println(st.getContents());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
				
				 // Send data
				CreateReturns cr = new CreateReturns();
				 values2 = cr.change(rank, name, date, score);
				
				return null;
			}
			 protected void onPostExecute(Void unused) {
		            // NOTE: You can call UI Element here.
		              
		            // Close progress dialog
		            Dialog.dismiss();
				 adapter = new CustomAdapter(Scores.this, R.layout.layout_item, values2);
					listView1.setAdapter(adapter);
		            if (Error != null) {
		                  
		               // uiUpdate.setText("Output : "+Error);
		                  
		            } else {
		            	
		            }
			 }
		}
	  
}

