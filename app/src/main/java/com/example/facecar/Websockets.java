package com.example.facecar;


import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;

public class Websockets {

    WebSocket ws = null;

    public Websockets() {

        // Create a WebSocket factory and set 5000 milliseconds as a timeout
        // value for socket connection.
        WebSocketFactory factory = new WebSocketFactory().setConnectionTimeout(5000);

        // Create a WebSocket. The timeout value set above is used.
        try {
            ws = factory.createSocket("ws://192.168.4.1:8888/ws/");

            ws.addListener(new WebSocketAdapter() {
                @Override
                public void onTextMessage(WebSocket websocket, String message) throws Exception {
                    Log.d("TAG", "onTextMessage: " + message);
                }
            });

            ws.connectAsynchronously();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void onDestroy() {

        if (ws != null) {
            ws.disconnect();
            ws = null;
        }
    }

    public void sendMessageSuccess() {
        if (ws.isOpen()) {
            ws.sendText("1");
        }
    }

    public void sendMessageFail() {
        if (ws.isOpen()) {
            ws.sendText("0");
        }


    }
}
