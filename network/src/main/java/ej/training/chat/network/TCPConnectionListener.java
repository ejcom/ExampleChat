package ej.training.chat.network;

public interface TCPConnectionListener {

    void onConnectionReady(TCPConnection tcpConnection);
    void onRecievString(TCPConnection tcpConnection, String value);
    void onDisconnect(TCPConnection tcpConnection);
    void onException(TCPConnection tcpConnection, Exception e);

}
