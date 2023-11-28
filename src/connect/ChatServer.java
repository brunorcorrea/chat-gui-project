package connect;

import view.MainView;

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

    public void start(MainView mainView, int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server waiting for connections on port " + port);

        // Wait for a client connection
        clientSocket = serverSocket.accept();
        System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
        mainView.getStatusLabel().setText("Status da Conexão: Conectado");

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String receiveMessage() throws IOException {
        if(in != null) return in.readLine();
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
}
