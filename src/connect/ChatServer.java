package connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server waiting for connections on port " + port);

        clientSocket = serverSocket.accept();
        System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String receiveMessage() throws IOException {
        if (in != null) return in.readLine();
        return null;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void stop() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
        if (clientSocket != null && !clientSocket.isClosed()) {
            clientSocket.close();
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public BufferedReader getIn() {
        return in;
    }
}
