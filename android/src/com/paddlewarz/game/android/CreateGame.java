package com.paddlewarz.game.android;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import com.paddlewarz.game.android.R;
import com.paddlewarz.game.android.R.id;
import com.paddlewarz.game.android.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class CreateGame extends Activity {

 TextView info, infoip, msg;
 String message = "";
 ServerSocket serverSocket;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.createview);
  info = (TextView) findViewById(R.id.info);
  infoip = (TextView) findViewById(R.id.infoip);
  msg = (TextView) findViewById(R.id.msg);
  
  infoip.setText(getIpAddress());

  Thread socketServerThread = new Thread(new SocketServerThread());
  socketServerThread.start();
 }

 @Override
 protected void onDestroy() {
  super.onDestroy();

  if (serverSocket != null) {
   try {
    serverSocket.close();
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
  }
 }

 private class SocketServerThread extends Thread {

  static final int SocketServerPORT = 63400;
  int count = 0;

  @Override
  public void run() {
   try {
    serverSocket = new ServerSocket(SocketServerPORT);
    CreateGame.this.runOnUiThread(new Runnable() {

     @Override
     public void run() {
      info.setText("I'm waiting here: "
        + serverSocket.getLocalPort());
     }
    });

    while (true) {
     Socket socket = serverSocket.accept();
     count++;
     message += "#" + count + " from " + socket.getInetAddress()
       + ":" + socket.getPort() + "\n";

     CreateGame.this.runOnUiThread(new Runnable() {

      @Override
      public void run() {
       msg.setText(message);
      }
     });

     SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
       socket, count);
     socketServerReplyThread.run();

    }
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
  }

 }

 private class SocketServerReplyThread extends Thread {

  private Socket hostThreadSocket;
  int cnt;

  SocketServerReplyThread(Socket socket, int c) {
   hostThreadSocket = socket;
   cnt = c;
  }

  @Override
  public void run() {
   OutputStream outputStream;
   String msgReply = "Hello from Android, you are #" + cnt;

   try {
    outputStream = hostThreadSocket.getOutputStream();
             PrintStream printStream = new PrintStream(outputStream);
             printStream.print(msgReply);
             printStream.close();

    message += "replayed: " + msgReply + "\n";

    CreateGame.this.runOnUiThread(new Runnable() {

     @Override
     public void run() {
      msg.setText(message);
     }
    });

   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    message += "Something wrong! " + e.toString() + "\n";
   }

   CreateGame.this.runOnUiThread(new Runnable() {

    @Override
    public void run() {
     msg.setText(message);
    }
   });
  }

 }

 private String getIpAddress() {
  String ip = "";
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
      ip += "SiteLocalAddress: " 
        + inetAddress.getHostAddress() + "\n";
     }
     
    }

   }

  } catch (SocketException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
   ip += "Something Wrong! " + e.toString() + "\n";
  }

  return ip;
 }
}
