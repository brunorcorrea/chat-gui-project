package model;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Client {
    protected Socket socket;
    protected PrintWriter out;
    protected BufferedReader in;

    public abstract void connect(String ipAddress, int port) throws Exception;

    public abstract void sendMessage(String message);

    public abstract String receiveMessage() throws Exception;

    public abstract void disconnect() throws Exception;
}
