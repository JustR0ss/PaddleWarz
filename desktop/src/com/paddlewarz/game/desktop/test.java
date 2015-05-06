package com.paddlewarz.game.desktop;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import com.paddlewarz.game.Player;
	public class test
	{
	    private static ServerSocket serverSocket;
	    private static Socket clientSocket;
	    private static BufferedReader bufferedReader,br;
	    private static String inputLine;
	    OutputStream outputStream;
	    PrintStream ps;
	    String message;
	    public void connect(){
	        // Wait for client to connect on 63400
	        try
	        {

				URL whatismyip = new URL("http://checkip.amazonaws.com");
				BufferedReader in = new BufferedReader(new InputStreamReader(
				                whatismyip.openStream()));
				
				String ip = in.readLine(); //you get the IP as a String
				String ipval = "127.0.0.1";
	            int port=8888;
	        	clientSocket = new Socket(ipval,port);
	            
	        	outputStream = clientSocket.getOutputStream();
	        	br = new BufferedReader(new InputStreamReader(System.in));
	        	ps = new PrintStream(outputStream);
	        	ps.print(br);
	        	ps.flush();
	            // Create a reader
	            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	            // Get the client message
	            while((inputLine = bufferedReader.readLine()) != null)
	            System.out.println(inputLine);
	        }
	        catch(IOException e)
	        {
	            System.out.println(e);
	        }
	    }
	}