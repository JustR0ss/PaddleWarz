package com.paddlewarz.game.android;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.paddlewarz.game.android.R;
import com.paddlewarz.game.android.R.id;
import com.paddlewarz.game.android.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

	public class JoinGame extends Activity {
	 
	 TextView textResponse;
	 EditText editTextAddress, editTextPort; 
	 Button buttonConnect, buttonClear;

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.joinview);
	  
	  editTextAddress = (EditText)findViewById(R.id.address);
	  editTextPort = (EditText)findViewById(R.id.port);
	  buttonConnect = (Button)findViewById(R.id.connect);
	  buttonClear = (Button)findViewById(R.id.clear);
	  textResponse = (TextView)findViewById(R.id.response);
	  
	  buttonConnect.setOnClickListener(buttonConnectOnClickListener);
	  
	  buttonClear.setOnClickListener(new OnClickListener(){

	   @Override
	   public void onClick(View v) {
	    textResponse.setText("");
	   }});
	 }
	 
	 OnClickListener buttonConnectOnClickListener = 
	   new OnClickListener(){

	    @Override
	    public void onClick(View arg0) {
	    	Intent i = new Intent(JoinGame.this, AndroidLauncher2.class);
   			Bundle b = new Bundle();
	      	b.putString("flag","false");
	      	b.putString("address",""+editTextAddress.getText());
	      	b.putString("port",""+editTextPort.getText());
	      	i.putExtras(b);
	      	startActivity(i);
	    }};



	}

