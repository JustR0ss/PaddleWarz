package com.paddlewarz.game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.JsonValue;

public class Player implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//Texture texture;
	String message;
	OutputStream outputStream;
	Vector2 position;
	int textureheight;
	int texturewidth;
	Rectangle bounds;
	private static Socket clientSocket;
	int flag=0;
	PrintStream printStream;
	PrintWriter out;
	float y;
	
	public Player(Vector2 position,int texturewidth,int textureheight){
		this.position=position;
		this.texturewidth = texturewidth;
		this.textureheight = textureheight;
		this.bounds=new Rectangle(this.position.x,this.position.y,this.texturewidth,this.textureheight);
		y=mainactivity.newBallP_y;
	}
	public Player(Vector2 position,int texturewidth,int textureheight,Socket clientSocket){
		this.position=position;
		this.texturewidth = texturewidth;
		this.textureheight = textureheight;
		this.bounds=new Rectangle(this.position.x,this.position.y,this.texturewidth,this.textureheight);
		Player.clientSocket= clientSocket;
		outputStream = Player.clientSocket.getOutputStream();
		flag=1;
		y=mainactivity.newBallP_y;
	}

	public void update(){
			if(Gdx.input.isKeyJustPressed(Keys.W)){
				position.y+=1f;	
			
			}
			if(Gdx.input.isKeyJustPressed(Keys.S)){
				position.y-=1f;
			
			}
			if(Gdx.input.isKeyJustPressed(Keys.A)){
				position.x-=1f;
			
			}
			if(Gdx.input.isKeyJustPressed(Keys.D)){
				position.x+=1f;
			
			}
			if(Gdx.input.isTouched()){
				//position.x=0;
				//System.out.println(Gdx.input.getX());
				//System.out.println(Gdx.input.getY());
				if((Gdx.input.getY()-textureheight/2)<y){
					if(flag==1){
						out =
	        			        new PrintWriter(outputStream, true);
						System.out.println(100);
						out.println(100);
			             //printStream.flush();
					}
					position.y=y;
					this.bounds.set(position.x,position.y,texturewidth,textureheight);
					//System.out.println("0");
				}
				else if((Gdx.input.getY()+textureheight/2)>Gdx.graphics.getHeight()-y){
					if(flag==1){
						System.out.println(Gdx.graphics.getHeight()-textureheight-y);
						out =
	        			        new PrintWriter(outputStream, true);
						out.println(Gdx.graphics.getHeight()-textureheight-y);
			             //printStream.flush();
					}
					position.y=Gdx.graphics.getHeight()-textureheight-y;
					this.bounds.set(position.x,position.y,texturewidth,textureheight);
					//System.out.println("max");
				}
				else{
					if(flag==1){
						System.out.println(Gdx.input.getY()-textureheight/2);
						out =
	        			        new PrintWriter(outputStream, true);
						 out.println(Gdx.input.getY()-textureheight/2);
			             //printStream.flush();
					}
					position.y=Gdx.input.getY()-textureheight/2;
					this.bounds.set(position.x,position.y,texturewidth,textureheight);
					//System.out.println("Normal");
					//System.out.println(textureheight/2);
				}
				
			
			}
			
	}
	
	
	@SuppressWarnings("unused")
	private static byte[] serialize(Object obj) throws IOException{
		ByteArrayOutputStream b =  new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		o.writeObject(obj);
		return b.toByteArray();	
	}
	
	public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException{
		ByteArrayInputStream b =  new ByteArrayInputStream(bytes);
		ObjectInputStream o  = new ObjectInputStream(b);
		return o.readObject();
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

	public static void savePlayer(Player player) throws IOException{
		FileHandle file = Gdx.files.local("player.dat");
		//Player savedPlayer = null;
		OutputStream out =null;
		try{
			file.writeBytes(serialize(player), false);
			System.out.println("saved");
		}catch(Exception ex){
			System.out.println(ex.toString());
		}finally{
			if(out!=null)try{out.close();}catch(Exception e){};
		}
		System.out.println("completed");
	}
	
	public static Player realPlayer() throws IOException,ClassNotFoundException{
		Player player=null;
		System.out.println("here");
		FileHandle file  = Gdx.files.internal("player.dat");
		player = (Player)deserialize(file.readBytes());
		
		return player;
	}
}
