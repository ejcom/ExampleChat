package ej.training.chat.server;

import ej.training.chat.network.TCPConnection;
import ej.training.chat.network.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer implements TCPConnectionListener {
    public static void main(String[] args) {
        new ChatServer();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    private ChatServer() {
        System.out.println("Server running. . .");
        try (ServerSocket serverSocket = new ServerSocket(8100)) {
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                } catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnentions("Client connected: " + tcpConnection);
    }

    @Override
    public synchronized void onRecievString(TCPConnection tcpConnection, String value) {
        sendToAllConnentions(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnentions("Client disconnected: " + tcpConnection);
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }

    private void sendToAllConnentions(String value) {
        System.out.println(value);
        final int cnt = connections.size();
        for (int i = 0; i < cnt; i++) connections.get(i).sendString(value);
    }
}
