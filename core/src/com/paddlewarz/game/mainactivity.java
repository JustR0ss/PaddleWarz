package com.paddlewarz.game;


import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class mainactivity extends ApplicationAdapter implements ApplicationListener,Runnable {
	
	String today;
	Date date = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SpriteBatch batch;
	Texture img;
	Vector2 position;
	Player player;
	Player2 player2;
	Leaderboard lb;
	TextButton button;
	Ball ball;
	MyTextInputListener listener;
	AI ai;
	boolean flag;
	boolean wait;
	Vector2 v1,v2,v3,v4;
	boolean connectionFlag;
	
	//int
	int newspeedx;
	int newspeedy;
	int end;
	int num1,num2,num3,num4;
	private int score;
	private int score2;
	
	//new sizes
	int width;
	int height;
	int size_w;
	int size_h;
	float screenDiff_x;
	float screenDiff_y;
	float newBallP_x;
	static float newBallP_y;
	float playpos;
	float textpos;
	float ballsize;
	float paddleheight;
	float paddlewidth;
	float screenPos;
	
	//string
	String win,mode;
	String message;
	private String yourScoreName,hisScoreName;
	private static String inputLine;
	
	BitmapFont yourBitmapFontName;
	Skin skin;
	private String aiScoreName;
	TextButtonStyle textButtonStyle;
    BitmapFont font;
    Stage stage;
	BitmapFont aiBitmapFontName;
	BitmapFont winBitmapFontName;
	ShapeRenderer srdr;
	static int playerscore;
	static int player2score;
	private TextureAtlas atlasImages;
	public static TextureRegion logic;
	public static TextureRegion ballx;
	OrthographicCamera camera;
	Sound sound,sound1,sound2;
	static ServerSocket serverSocket;
	static ServerSocket serverSocket2;
    static BufferedReader bufferedReader;
    FreeTypeFontGenerator generator;
	SocketHints sh;
    String carriedIP;
    int carriedPort;
    Socket clientSocket;
    Socket clientSocket2;
    DecimalFormat df = new DecimalFormat("#.##");
    
    static final int MAIN_WIDTH = 1920;
    static final int MAIN_HEIGHT = 1080;
    static final int BALL_PLACE = 100;
    static final int PLAY_POS1 = 300;
    static final int BALL_VAL = 7;
    static final int PADDLE_WIDTH = 10;
    static final int PADDLE_HEIGHT = 80;
    static final int SCREEN_TEXT = 40;
    static final int SCORE_TEXT=500;
    
	@Override
	public void create () {
		
		setupScreen();
		
		wait=false;
		
		ballPlacement();
		
		setupSound();
		
		setupCamera();
		// Create a full-screen camera:
		
		// Create a full screen sprite renderer and use the above camera
		createSpriteBatch();
		
		//FreeTypeFontGenerator generator1= new FreeTypeFontGenerator(Gdx.files.internal("fonts/PTC55F.ttf"));
		fontGenerator();
		
		
		//start scores
		playerscore=0;
		player2score=0;
		yourScoreName = "Score: 0";
	    aiScoreName = "Lifes: 2";
	    hisScoreName="Score: 0";
	    win="";
	    //System.out.println("Scores setup");
	    
	    // bounds for ball and paddle
	    v1 = new Vector2(0,newBallP_y);
	    v2 = new Vector2(width,newBallP_y);
	    v3 = new Vector2(0,height-newBallP_y);
	    v4 = new Vector2(width,height-newBallP_y);
	    stage = new Stage();
        Gdx.input.setInputProcessor(stage);
	    font=new BitmapFont();
	    skin = new Skin();
	    //System.out.println("Bound lines setup");
	    
	    end=0;
	    atlasImages=new TextureAtlas(Gdx.files.internal("data/pack2.atlas"));
		skin.addRegions(atlasImages);
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("paddled2");
        //System.out.println("Button texture setup");
        
	    button = new TextButton("Restart", textButtonStyle);
	    button.setPosition(width/2-100, height/2-50); //** Button location **//
        button.setHeight(100); //** Button Height **//
        button.setWidth(200); //** Button Width **//
	    button.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				 // TODO Auto-generated method stub
				 end=0;
				 
				 
				 if(mode=="singleplayer"){
					 score=2;
					 ball = new Ball(new Vector2(randInt(num1,num2),randInt(num3,num4)),ballx.getRegionHeight(),ballx.getRegionWidth());
					 ball.velx=4;
					 ball.vely=4;
					 win = "";
					 playerscore=0;
					 yourScoreName = "Score: " + playerscore;
					 aiScoreName = "Lifes: " + score;
					 Gdx.graphics.setContinuousRendering(true);
					 Gdx.graphics.requestRendering();
				 }
				 else{
					 if(wait){
						 score=0;
						 score2=0;
						 win = "";
						 hisScoreName="Score : 0";
						 yourScoreName ="Score : 0";
						 ball.newBall(new Vector2(randInt(num1,num2),randInt(num3,num4)),ballx.getRegionHeight(),ballx.getRegionWidth());
						 

					 }else{
						 ball.newBall(new Vector2(randInt(num1,num2),randInt(num3,num4)),ballx.getRegionHeight(),ballx.getRegionWidth());

						//ball.updateOtherPos();
					 }
					 
					 hisScoreName="Score: 0";
				 }
				 
				 
			}
	    });
	    stage.addActor(button);
	    //System.out.println("Click Button setup");
	    
		logic = atlasImages.findRegion("paddled2");
		logic.setRegionHeight((int)paddleheight);
		logic.setRegionWidth((int)paddlewidth);
		logic.flip(false,true);
		ballx = atlasImages.findRegion("paddleball");
		ballx.setRegionHeight((int)ballsize);
		ballx.setRegionWidth((int)ballsize);
		//System.out.println("Images setup");
		//img = new Texture(Gdx.files.internal("data/badlogic.jpg"));
		
		//System.out.println("Ball created");
		
		if(mode=="singleplayer"){
			System.out.println("singleplayer");
			ball = new Ball(new Vector2(randInt(num1,num2),randInt(num3,num4)),ballx.getRegionHeight(),ballx.getRegionWidth());
			System.out.println("ball created");
			
		
		if(Gdx.files.internal("player.dat").exists()){
			try{
				player = Player.realPlayer();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			System.out.println("exists");
		}
		else{
			player = new Player(new Vector2(playpos,height/2),logic.getRegionWidth(),logic.getRegionHeight());
			System.out.println("new player created");
			try {
				player.savePlayer(player);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("player not exist");
		}
		ai = new AI(new Vector2(width-logic.getRegionWidth()-playpos,ball.getPosition().y-(logic.getRegionHeight()/2)),logic.getRegionWidth(),logic.getRegionHeight());
		System.out.println("ai created");
		}
		else{
			System.out.println("mulitplayer");
			
				//System.out.println("multiplayer game");
				
	            if(flag){
	            	
	            	Thread t = new Thread(this);
	        		t.start();
	        		
	            }else{
	            	System.out.println("connecting...");
	            	SocketHints sh= new SocketHints();
					Socket clientSocket2 = Gdx.net.newClientSocket(Protocol.TCP,carriedIP,carriedPort, sh);
					Socket clientSocket3 = Gdx.net.newClientSocket(Protocol.TCP,carriedIP,carriedPort+1, sh);
					
					if(clientSocket2.isConnected()){
	            		System.out.println("connected");
	            		player = new Player(new Vector2(width-logic.getRegionWidth()-playpos,height/2),logic.getRegionWidth(),logic.getRegionHeight(),clientSocket2);
	            		//System.out.println("player1 cre");
	            		player2 = new Player2(new Vector2(playpos,height/2),logic.getRegionWidth(),logic.getRegionHeight(),clientSocket2);
	            		
	            		ball = new Ball(new Vector2(randInt(num1,num2),randInt(num3,num4)),ballx.getRegionHeight(),ballx.getRegionWidth(),clientSocket3,2);
	            		
	            		//System.out.println("player2 cre");
	            		//System.out.println("players created");
	            		wait=true;
	            		
	            	 }else{
	            		 System.out.println("failed");
	            	 }
					System.out.println("joined game");
					
				}
	        
			
		}
		
	}
	
	private void fontGenerator() {
		// TODO Auto-generated method stub
		generator  = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PTC55F.ttf"));
		
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		System.out.println("Freetype done");
		
		
		parameter.size = convertFromFloat(22,"x");
		
		
		aiBitmapFontName = generator.generateFont(parameter); // font size 18 pixels
		winBitmapFontName = generator.generateFont(parameter); // font size 18 pixels
		yourBitmapFontName = generator.generateFont(parameter); // font size 18 pixels
		winBitmapFontName.setScale(1, -1);
		aiBitmapFontName.setScale(1, -1);
		yourBitmapFontName.setScale(1, -1);
		generator.dispose();
		System.out.println("Font maps setup");
	}

	private void createSpriteBatch() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		//Create full screen for bounds
		srdr= new ShapeRenderer();
		srdr.setProjectionMatrix(camera.combined);
		System.out.println("Sprite Batch Setup");
	}

	private void ballPlacement() {
		// TODO Auto-generated method stub
		newBallP_x = BALL_PLACE*screenDiff_x;
		newBallP_y = BALL_PLACE*screenDiff_y;
		
		//range of ball placement
		num1=(width/2)-(int)newBallP_x;
		num2=(width/2)+(int)newBallP_x;
		num3=(height/2)-(int)newBallP_y;
		num4=(height/2)+(int)newBallP_y;
		System.out.println("Ball placement done");
	}

	private void setupScreen() {
		// TODO Auto-generated method stub
		double a,b;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		System.out.println(" ");
		a = (double)width;
		b = (double)MAIN_WIDTH;
		
		screenDiff_x = (float)round(a/b,2);
		
		System.out.println(screenDiff_x);
		System.out.println(" ");
		
		a = (double)height;
		b = (double)MAIN_HEIGHT;
		screenDiff_y =(float)round(a/b,2);
		
		System.out.println(screenDiff_y);
		System.out.println(" ");
		
		a = (double)height;
		b = (double)SCREEN_TEXT;
		screenPos =(float)round(a/b,2);
		
		
		
		playpos = screenDiff_x*PLAY_POS1;
		
		textpos = screenDiff_x*SCORE_TEXT;
		
		ballsize = BALL_VAL*screenDiff_x;
		System.out.println(ballsize);
		paddlewidth = screenDiff_x*PADDLE_WIDTH;
		paddleheight = screenDiff_y*PADDLE_HEIGHT;
	}

	private void setupCamera() {
		// TODO Auto-generated method stub
		camera = new OrthographicCamera(width, height);
		// Set it to an orthographic projection with "y down" (the first boolean parameter)
		camera.setToOrtho(true, width, height);
		camera.update();
		//System.out.println(width + " "+ height + "camera Setup");
	}

	public void setupSound(){
		//sound
				sound = Gdx.audio.newSound(Gdx.files.internal("data/mysound.mp3"));
				sound1 = Gdx.audio.newSound(Gdx.files.internal("data/point.mp3"));
				sound2 = Gdx.audio.newSound(Gdx.files.internal("data/win.mp3"));
				System.out.println("Sound setup");
	}
	
	@Override
	public void dispose(){
		System.out.println("disposed");
		if(serverSocket!=null){
			serverSocket.dispose();
			serverSocket2.dispose();
			clientSocket2.dispose();
			clientSocket.dispose();
		}
		
		try{
			player.savePlayer(player);
		}catch(IOException io){
			io.printStackTrace();
			
		}
	}
	
	
	@Override
	public void render () {
		//System.out.println("entered rendering");
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		if (Gdx.input.isKeyPressed(Keys.BACK)){
			  // Do something
			if(serverSocket!=null){
				serverSocket.dispose();
				serverSocket2.dispose();
				clientSocket2.dispose();
				clientSocket.dispose();
			}
			}
		if(mode!="singleplayer"){
			if(wait){
				if(player!=null){
					if(player2!=null){
						win="";
						//System.out.println("player1 and 2 not null");
						player.update();
						//System.out.println("player1 updated");
						ball.update();
						//System.out.println("ball updated");
						
						if(ball.getPosition().y<=newBallP_y){
							System.out.println("Top edge");
							ball.diry=1;
						}
						if(ball.getPosition().y>=height-newBallP_y){
							System.out.println("Bottom Edge");
							ball.diry=0;
						}
						
						
						
					}else{
						win = "Waiting on other player to join";
					}
					
				}else{
					win = "Waiting on players to join";
				}
			}
			
		}else{
			
			if(player!=null&& ball!=null){
				player.update();
				ball.update();
				if(ball.getPosition().y<newBallP_y){
					System.out.println("Top edge");
					ball.diry=1;
				}
				if(ball.getPosition().y>height-newBallP_y){
					System.out.println("Bottom Edge");
					ball.diry=0;
				}
				if(ball.getPosition().x>width){
					sound2.play(1.0f);
					ball=null;
					//ai.setVely(1);
					playerscore=playerscore+1;
					ai.setVely(4);
					ball = new Ball(new Vector2(randInt(num1,num2),randInt(num3,num4)),ballx.getRegionHeight(),ballx.getRegionWidth());
					yourScoreName = "Score : "+playerscore;
				}
			}
			
		}
		//System.out.println("playerupdated");
		
		
		//System.out.println("ballupdated");
		
		if(mode.equals("singleplayer")){
			//System.out.println("singleplayer");
			ai.update(ball.getPosition().y+(ball.texturewidth/2));
			if(ai.getBounds().overlaps(ball.getBounds())){
				sound.play(1.0f);
				newspeedx = ball.getVelx();
				ball.setVelx(newspeedx*2);
				newspeedy = ball.getVely();
				ball.setVely(newspeedy);
				ball.dirx=0;
				ai.setVely(newspeedy);
			}
			if(player.getBounds().overlaps(ball.getBounds())){
				sound.play(1.0f);
				newspeedx = ball.getVelx();
				ball.setVelx(newspeedx*2);
				newspeedy = ball.getVely();
				ball.setVely(newspeedy*2);
				ball.dirx=1;
				ai.setVely(newspeedy*2);
			}
			if(ball.getPosition().x<0){
				ball=null;
				ai.setVely(4);
				score = score-1;
				if(score ==-1){
				 sound2.play(1.0f);
				 yourScoreName = "Score: " + playerscore;
				 win = "Your final score was "+playerscore+"!";
				 Gdx.graphics.setContinuousRendering(false);
				 listener = new MyTextInputListener(lb);
				 
				 Gdx.input.getTextInput(new TextInputListener() {

					@Override
					public void input(String text) {
						// TODO Auto-generated method stub
						listener.input(text);
						if(connectionFlag){
							 System.out.println("internet connection");
							 Gdx.input.getTextInput(new TextInputListener() {

									@Override
									public void input(String text) {
										// TODO Auto-generated method stub
										System.out.println(text);
										today=dateFormat.format(date).toString();
										lb.addScore(text, today, playerscore);
						                // Use AsyncTask execute Method To Prevent ANR Problem
						                
									}

									@Override
									public void canceled() {
										// TODO Auto-generated method stub
										
									}}, "Submit Name Globally", "", "");
						 }
					}

					@Override
					public void canceled() {
						// TODO Auto-generated method stub
						if(connectionFlag){
							 System.out.println("internet connection");
							 Gdx.input.getTextInput(new TextInputListener() {

									@Override
									public void input(String text) {
										// TODO Auto-generated method stub
										System.out.println(text);
										today=dateFormat.format(date).toString();
										lb.addScore(text, today, playerscore);
						                // Use AsyncTask execute Method To Prevent ANR Problem
						                
									}

									@Override
									public void canceled() {
										// TODO Auto-generated method stub
										
									}}, "Submit Name Globally", "", "");
						 }
					}}, "Submit Name - Local", "", "");
				 
				 end=1; 
				
				 ball = new Ball(new Vector2(randInt(num1,num2),randInt(num3,num4)),ballx.getRegionHeight(),ballx.getRegionWidth());
				 ball.velx=0;
				 ball.vely=0;
				}
				else{
					sound1.play(1.0f);
					aiScoreName = "Lifes: " + score;
					ball = new Ball(new Vector2(randInt(num1,num2),randInt(num3,num4)),ballx.getRegionHeight(),ballx.getRegionWidth());
					
				}
			}
			
			
			}
		else{
			//System.out.println("entered multiplayer s2 ");
			//player2.update();
			if(wait){
				//System.out.println("proceed");
			if(player2.getBounds().overlaps(ball.getBounds())){
				sound.play(1.0f);
				newspeedx = ball.getVelx();
				ball.setVelx(newspeedx*2);
				newspeedy = ball.getVely();
				ball.setVely(newspeedy);
				ball.dirx=0;
			}
			if(player.getBounds().overlaps(ball.getBounds())){
				sound.play(1.0f);
				newspeedx = ball.getVelx();
				ball.setVelx(newspeedx*2);
				newspeedy = ball.getVely();
				ball.setVely(newspeedy*2);
				ball.dirx=1;
			}
			
			if(ball.getPosition().x<0){
				sound2.play(1.0f);
				System.out.println("player 2 scored"+ ball.getPosition().x);
				score2 = score2+1;
				System.out.println(score2);
				if(score2 ==10){
					hisScoreName = "Score : "+score2;
					win ="You lost!";
					ball.newBall(new Vector2(randInt(num1,num2),randInt(num3,num4)),ballx.getRegionHeight(),ballx.getRegionWidth());
					ball.velx=0;
					ball.vely=0;
					end =1;
				}else{
					ball.newBall(new Vector2(randInt(num1,num2),randInt(num3,num4)),ballx.getRegionHeight(),ballx.getRegionWidth());

					hisScoreName = "Score : "+score2;
				}
				
				
			}
			if(ball.getPosition().x>width){
				sound2.play(1.0f);
				score=score+1;
				System.out.println("player 1 scored " + score);
				if(score ==10){
					 sound2.play(1.0f);
					 yourScoreName = "Score: " + score;
					 
					 win = "You won, final score was "+score+" to"+score2+"!";
					 listener = new MyTextInputListener(lb);
					 Gdx.input.getTextInput(new TextInputListener() {

						@Override
						public void input(String text) {
							// TODO Auto-generated method stub
							listener.input(text);
						}

						@Override
						public void canceled() {
							// TODO Auto-generated method stub
							
						}}, "Submit Name", "", "");
					 
					 end=1; 
					
					 
						ball.newBall(new Vector2(randInt(num1,num2),randInt(num3,num4)),ballx.getRegionHeight(),ballx.getRegionWidth());
						ball.velx=0;
						ball.vely=0;
					}else{
						ball.newBall(new Vector2(randInt(num1,num2),randInt(num3,num4)),ballx.getRegionHeight(),ballx.getRegionWidth());
						yourScoreName = "Score : "+score;

					}
				
			}
			}
			
			
		}
		 
		srdr.begin(ShapeType.Line);
		srdr.setColor(0, 0, 0, 1);
		if(ball!=null){
			srdr.rect(ball.getPosition().x,ball.getPosition().y,ball.getBounds().height,ball.getBounds().width);

		}
		if(player!=null){
			srdr.rect(player.getPosition().x,player.getPosition().y,player.getBounds().width,player.getBounds().height);
			
		}
		if(mode=="singleplayer"){
			srdr.rect(ai.getPosition().x,ai.getPosition().y,ai.getBounds().width,ai.getBounds().height);
		}else{
			if(wait){
				srdr.rect(player2.getPosition().x,player2.getPosition().y,player2.getBounds().width,player2.getBounds().height);

			}
		}
		srdr.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		srdr.line(v1, v2);
		srdr.line(v3, v4);
		srdr.end();
		
		batch.begin();
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		yourBitmapFontName.draw(batch, yourScoreName, textpos, height-(int)screenPos); 
		aiBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		if(mode.equals("singleplayer")){
			aiBitmapFontName.draw(batch, aiScoreName, width-textpos, height-(int)screenPos); 

		}else{
			aiBitmapFontName.draw(batch, hisScoreName, width-textpos, height-(int)screenPos); 

		}
		winBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		if(mode.equals("singleplayer")){
			winBitmapFontName.draw(batch, win, width/2-(playpos/2), height-(int)screenPos); 

		}else{
			winBitmapFontName.draw(batch, win, width/2-playpos, height-(int)screenPos); 

		}
		if(player!=null){
			batch.draw(logic,player.getPosition().x,player.getPosition().y);
		}
		if(mode=="singleplayer"){
			batch.draw(logic,ai.getPosition().x,ai.getPosition().y);
		}else{
			if(wait){
				batch.draw(logic,player2.getPosition().x,player2.getPosition().y);
			}
		}
		if(ball!=null){
			batch.draw(ballx,ball.getPosition().x,ball.getPosition().y);
		}
		
		batch.end();
		if(end==1){
			stage.draw();
		}
		
	}
	
	public mainactivity(Leaderboard leaderboardServiceApi,boolean connectionFlag,String mode) {
	      this.lb = leaderboardServiceApi;
	      this.mode=mode;
	      System.out.println(mode);
	      this.score=2;
	      this.connectionFlag=connectionFlag;
	   }
	public mainactivity(Leaderboard leaderboardServiceApi,String mode,boolean flag) {
		this.lb = leaderboardServiceApi;
		this.mode=mode;
		System.out.println(mode);
		this.flag = flag; 
		this.score=0;
		this.score2=0;
	   }
	public mainactivity(Leaderboard leaderboardServiceApi,String mode,boolean flag,String ip,int port) {
		this.lb = leaderboardServiceApi;
		this.mode=mode;
		this.carriedIP=ip;
		this.carriedPort=port;
		System.out.println(mode);
		this.flag = flag; 
		this.score=0;
	   }
	public static int randInt(int min, int max) {

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	public void createplayers(Socket clientSocket,Socket clientSocket2){
		//System.out.println("creating new player");
		player = new Player(new Vector2(playpos,height/2),logic.getRegionWidth(),logic.getRegionHeight(),clientSocket);
		//System.out.println("player1 created");
		player2 = new Player2(new Vector2(width-logic.getRegionWidth()-playpos,height/2),logic.getRegionWidth(),logic.getRegionHeight(),clientSocket);
		//System.out.println("player2 created");
		ball = new Ball(new Vector2(randInt(num1,num2),randInt(num3,num4)),ballx.getRegionHeight(),ballx.getRegionWidth(),clientSocket2,1);
		System.out.println("ball created");
		ball.updateOtherPos();
		System.out.println("send new ball loc");
	}

	@Override
	public void run() {
		System.out.println("thread");
		// TODO Auto-generated method stub
		ServerSocketHints serverSocketHint = new ServerSocketHints();
        // 0 means no timeout.  Probably not the greatest idea in production!
        serverSocketHint.acceptTimeout = 0;
        System.out.println("s1");
        serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 63400, serverSocketHint);
        System.out.println("s2");
        serverSocket2 = Gdx.net.newServerSocket(Protocol.TCP, 63401, serverSocketHint);
        
        while(true){
        	 
        	 System.out.println("1st");
        	 clientSocket= serverSocket.accept(null);
        	 System.out.println("2nd");
        	 win="Player 2 setup";
        	 clientSocket2= serverSocket2.accept(null);
        	 win="Ball setup";
        	 System.out.println("listening socket");
        	 win="waiting on someone to join";
        	 if(clientSocket.isConnected()){
        		createplayers(clientSocket,clientSocket2);
        		System.out.println("players and ball created");
        		if(ball!=null){
        			System.out.println("true");
        			wait=true;
        		}else{
        			System.out.println("something wrong");
        		}
        		
        		break;
        	 }
        	 else{
        		System.out.println("not connected");
        	}
        	 
       }
	}
	
	public int convertFromFloat(float num,String str){
		float newnum;
		double round;
		double placeholder1,placeholder2;
		if(str.equals("x")){
			placeholder1 = Double.parseDouble(num+"");
			placeholder2 = Double.parseDouble(screenDiff_x+"");
			newnum = (float)(placeholder1*placeholder2);
			round = Math.round(newnum);
			System.out.println((int)round+" x converted");
			return (int)round;
		}else{
			placeholder1 = Double.parseDouble(num+"");
			placeholder2 = Double.parseDouble(screenDiff_y+"");
			newnum = (float)(placeholder1*placeholder2);
			round = Math.round(newnum);
			System.out.println((int)round+" y converted");
			return (int)round;
		}
		
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
}
