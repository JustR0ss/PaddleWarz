package com.paddlewarz.game.android;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.paddlewarz.game.android.Scores.MultiScore;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Multiplayer extends Activity{

	TextView infoip,info;
	String ip="";
	Button b1,b2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multi_main);
		b1 = (Button) findViewById(R.id.create);
		b2 = (Button) findViewById(R.id.join);
		infoip = (TextView) findViewById(R.id.infoip);
		infoip.setText(getIpAddress());
		info = (TextView) findViewById(R.id.info);
		info.setText("Available on port 63400");
		findViewById(R.id.create).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent i = new Intent(Multiplayer.this, AndroidLauncher2.class);
				Bundle b = new Bundle();
				b.putString("flag","true");
				i.putExtras(b);
				startActivity(i);
			}

		});
		findViewById(R.id.join).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent i = new Intent(Multiplayer.this, JoinGame.class);
				System.out.println("Join game from multi menu");
				startActivity(i);
			}

		});
		findViewById(R.id.create2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent i = new Intent(Multiplayer.this, ComingSoon.class);
				
				startActivity(i);
			}

		});
		findViewById(R.id.join2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent i = new Intent(Multiplayer.this, ComingSoon.class);
				
				startActivity(i);
			}

		});

	}

	private String getIpAddress() {
		
		try {
			Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (enumNetworkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = enumNetworkInterfaces
						.nextElement();
				Enumeration<InetAddress> enumInetAddress = networkInterface
						.getInetAddresses();
				while (enumInetAddress.hasMoreElements()) {
					InetAddress inetAddress = enumInetAddress.nextElement();

					if (inetAddress.isSiteLocalAddress()) {
						ip += "Local Address is : " 
								+ inetAddress.getHostAddress() + "";
						b1.setEnabled(true);
						b2.setEnabled(true);
					}

				}

			}
			if(ip.equals("")){
				ip+="No local network connected";
				b1.setEnabled(false);
				b2.setEnabled(false);
			}
			new getPubIp().execute();
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ip += "Something Wrong! " + e.toString() + "\n";
		}
		
		return ip;
	}
	public class getPubIp extends AsyncTask<Void, Void, Void>{
		boolean available = false;
		Button b3,b4;
		
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			b3 = (Button) findViewById(R.id.create2);
			b4 = (Button) findViewById(R.id.join2);
			String str="";
			try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet("http://ip2country.sourceforge.net/ip2c.php?format=JSON");
			// HttpGet httpget = new HttpGet("http://whatismyip.com.au/");
			// HttpGet httpget = new HttpGet("http://www.whatismyip.org/");
			HttpResponse response;

			response = httpclient.execute(httpget);
			//Log.i("externalip",response.getStatusLine().toString());

			HttpEntity entity = response.getEntity();
			

			entity.getContentLength();
			str = EntityUtils.toString(entity);
			JSONObject json_data = new JSONObject(str);
			ip += "\nPublic ip is : "+json_data.getString("ip");
			available = true;
			}
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		protected void onPostExecute(Void unused) {
			infoip.setText(ip);
			if(available == true){
				b3.setEnabled(true);
				b4.setEnabled(true);
			}else{
				b3.setEnabled(false);
				b4.setEnabled(false);
			}
			
		}
	}
}
