package com.paddlewarz.game.android;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;

public class LeaderboardServiceApi {
	DataSource ds;

	private Context context;

	//save the context recievied via constructor in a local variable

	public LeaderboardServiceApi(Context context){
		this.context=context;
	}

	public void submitScore(String user, int score) { 
		ds = new DataSource(context);
		ds.open();
		ds.createScore(user, score);
		//System.out.println("Name : "+user+" score : "+score);
		//System.out.println("Name saved");
	}

	public void addScore(String name, String date,int score){
		String[] params = new String[4];
		params[0]="http://54.76.199.8:8080/ScoresService/services/ReturnScores/updateScores";
		params[1]=name;
		params[2]=date;
		params[3]=score+"";
		//String serverURL = "http://54.76.199.8:8080/ScoresService/services/ReturnScores/updateScores";

		// Use AsyncTask execute Method To Prevent ANR Problem
		new TopScoreAdd().execute(params);
	}

	class TopScoreAdd extends AsyncTask<String, Void, Void>{

		URL url;

		//public ProgressDialog Dialog ;

		String Error = null;
		String name;
		String date;
		String score;

		protected void onPreExecute() {

		}

		@Override
		protected Void doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				url = new URL(arg0[0]);
				name = arg0[1];
				date = arg0[2];
				score = arg0[3];
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			pairs.add(new BasicNameValuePair("str", name));
			pairs.add(new BasicNameValuePair("date", date));
			pairs.add(new BasicNameValuePair("score", score));


			HttpClient client = new DefaultHttpClient();

			HttpPost post = new HttpPost(url.toString());
			try {
				post.setEntity(new UrlEncodedFormEntity(pairs));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				HttpResponse response = client.execute(post);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.

			// Close progress dialog
			//Dialog.dismiss();

		}
	}
}
