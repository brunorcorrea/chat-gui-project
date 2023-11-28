package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientImpl extends Client {
    private Client client;

    public Client getOtherUser() {
        return client;
    }

    public void setOtherUser(Client otherClient) {
        this.client = otherClient;
    }

    public void connect(String ipAddress, int port) throws IOException {
        client.socket = new Socket(ipAddress, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() throws IOException {
        return in.readLine();
    }

    public void disconnect() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}
