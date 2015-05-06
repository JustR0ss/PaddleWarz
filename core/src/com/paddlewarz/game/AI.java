package com.paddlewarz.game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;

public class AI implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//Texture texture;
	Vector2 position;
	int textureheight;
	int texturewidth;
	Rectangle bounds;
	double velx;
	double vely;
	float boundryY;
	
	public AI(Vector2 position,int texturewidth,int textureheight){
		this.position=position;
		this.texturewidth = texturewidth;
		this.textureheight = textureheight;
		velx=4;
		vely=4;
		this.bounds=new Rectangle(this.position.x,this.position.y,this.texturewidth,this.textureheight);
		System.out.println(this.bounds);
		boundryY=mainactivity.newBallP_y;
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
	public double getVelx() {
		return velx;
	}

	public void setVelx(int velx) {
		System.out.println("For AI Speed increased to on x : "+velx);
		this.velx = velx*1.2;
	}

	public double getVely() {
		return vely;
	}

	public void setVely(int vely) {
		
		System.out.println("For AI Speed increased to on y : "+vely);
		this.vely = vely*1.2;
	}
	
	public void update(float y){
		if(this.position.y+(this.textureheight/2)>y){
			if(y>this.textureheight/2+boundryY){
				this.position.y =this.position.y-(float)vely;
				this.bounds.set(position.x,position.y,texturewidth,textureheight);
				
			}
			
		}else if(this.position.y+(this.textureheight/2)<y){
			if(y<Gdx.graphics.getHeight()-this.textureheight/2-boundryY){
				this.position.y =this.position.y+(float)vely;
				this.bounds.set(position.x,position.y,texturewidth,textureheight);
				
			}
		}else if(this.position.y+(this.textureheight/2)==y){
			
		}
		
	}
			
}
	
	

