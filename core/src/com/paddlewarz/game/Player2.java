package com.paddlewarz.game;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.JsonValue;

public class Player2 implements Runnable {
	
	Vector2 position;
	int textureheight;
	int texturewidth;
	Rectangle bounds;
	Socket clientSocket;
	BufferedReader bufferedReader;
	private static String inputLine;
	int intinputline;
	
	public Player2(Vector2 position,int texturewidth,int textureheight,Socket clientSocket){
		this.position=position;
		this.texturewidth = texturewidth;
		this.textureheight = textureheight;
		this.clientSocket = clientSocket;
		this.bounds=new Rectangle(this.position.x,this.position.y,this.texturewidth,this.textureheight);
		bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		System.out.println("bufferreader created");
		try {
			Thread t = new Thread(this);
		    t.start();
				
			//System.out.println("Going");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void update(){
		
	}
	
	public Vector2 getPosition(){
		return position;
	}
	public void setPosition(Vector2 position){
		this.position= position;
	}


//	public Texture getTexture() {
//		// TODO Auto-generated method stub
//		return texture;
//	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("entered run");
		try {
			
			while((inputLine = bufferedReader.readLine()) != null){
				System.out.println(inputLine+" inputline from player2");
				if(isNumeric(inputLine)){
					intinputline = Integer.parseInt(inputLine);
					if(intinputline<100){
						
						intinputline=100;
						
					}
					else if((intinputline+textureheight/2)>Gdx.graphics.getHeight()-100){
						
						intinputline=Gdx.graphics.getHeight()-textureheight-100;
						
					}
					
					this.position.y = intinputline;
					this.bounds.set(position.x,position.y,texturewidth,textureheight);
				}
				}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    int d = Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}

