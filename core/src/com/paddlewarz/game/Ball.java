package com.paddlewarz.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.Socket;

public class Ball implements Runnable{
	
	Vector2 position;
	int textureheight;
	int texturewidth;
	Rectangle bounds;
	int velx;
	int vely;
	int dirx;
	int diry;
	int num;
	PrintWriter out;
	private static Socket clientSocket;
	OutputStream outputStream;
	BufferedReader bufferedReader;
	String inputLine;
	
	public int getDiry() {
		return diry;
	}

	public void setDiry(int diry) {
		this.diry = diry;
	}

	public int getDirx() {
		return dirx;
	}

	public void setDirx(int dir) {
		this.dirx = dir;
	}

	public Ball(Vector2 position,int textureheight,int texturewidth){
		this.position=position;
		this.texturewidth = texturewidth;
		this.textureheight = textureheight;
		this.bounds=new Rectangle(this.position.x,this.position.y,this.textureheight,this.texturewidth);
		velx=4;
		vely=4;
		dirx=randInt(0,1);
		diry=randInt(0,1);
	}
	public Ball(Vector2 position,int textureheight,int texturewidth,Socket clientSocketx,int num){
		System.out.println("create new multi ball");
		this.position=position;
		this.texturewidth = texturewidth;
		this.textureheight = textureheight;
		this.bounds=new Rectangle(this.position.x,this.position.y,this.textureheight,this.texturewidth);
		this.num=num;
		velx=4;
		vely=4;
		dirx=randInt(0,1);
		diry=randInt(0,1);
		this.clientSocket=clientSocketx;
		outputStream = this.clientSocket.getOutputStream();
		bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		System.out.println("bufferreader created");
		try {
			Thread t = new Thread(this);
		    t.start();
		    System.out.println("multi ball start");
			//System.out.println("Going");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void newBall(Vector2 position,int textureheight,int texturewidth){
		System.out.println("new multi ball after thread started");
		this.position=position;
		this.texturewidth = texturewidth;
		this.textureheight = textureheight;
		this.bounds=new Rectangle(this.position.x,this.position.y,this.textureheight,this.texturewidth);
		velx=4;
		vely=4;
		dirx=randInt(0,1);
		diry=randInt(0,1);
	}
	
	public void update(){
		
		if(num==1||clientSocket==null){
			
			
		if(dirx==0){
			//System.out.println(position.x);
			position.x=position.x-velx;
			//System.out.println(position.x);
		}else{
			position.x=position.x+velx;
		}
		if(diry==0){
			position.y=position.y-vely;
		}else{
			position.y=position.y+vely;
		}
		bounds.set(position.x,position.y,mainactivity.BALL_VAL,mainactivity.BALL_VAL);
		if(outputStream!=null){
			System.out.println("send");
			out =
			        new PrintWriter(outputStream, true);
			out.println(position.x+":"+position.y+":"+getDirx()+":"+getDiry()+":"+getVelx()+":"+getVely());
		
		}
		}
	}
	
	
	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public Vector2 getPosition(){
		return position;
	}
	public void setPosition(Vector2 position){
		this.position= position;
	}

	public int getVelx() {
		return velx;
	}

	public void setVelx(int velx) {
		System.out.println("For Ball Speed increased to on x : "+velx);
		if(velx<12){
			this.velx = velx;
		}
		
	}

	public int getVely() {
		return vely;
	}

	public void setVely(int vely) {
		System.out.println("For Ball Speed increased to on y : "+vely);
		this.vely = vely;
	}
	public static int randInt(int min, int max) {

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public void updateOtherPos(){
		System.out.println("sending new  ball loc");
		out =
		        new PrintWriter(outputStream, true);
		out.println(position.x+":"+position.y+":"+getDirx()+":"+getDiry()+":"+getVelx()+":"+getVely());
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			while((inputLine = bufferedReader.readLine()) != null){
				System.out.println(inputLine+" inputline from player2");
					String str[] = inputLine.split(":");
					for(int i =0; i<str.length;i++){
						if(i==0){
							this.position.x=Float.parseFloat(str[i]);
						}else if(i==1){
							this.position.y=Float.parseFloat(str[i]);
						}else if(i==2){
							setDirx(Integer.parseInt(str[i]));
						}else if(i==3){
							setDiry(Integer.parseInt(str[i]));
						}else if (i==4){
							setVelx(Integer.parseInt(str[i]));
						}else if (i==5){
							setVely(Integer.parseInt(str[i]));
						}
					}
					this.bounds.set(position.x,position.y,texturewidth,textureheight);
				
				}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
