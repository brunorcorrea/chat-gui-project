package teste;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;

public class ChatServer implements Subject {
    private ServerSocket serverSocket;
    private List<Observer> observers = new ArrayList<>();

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server waiting for connections on port " + port);

        // Wait for a client connection
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

        // Create an observer for the client
        ChatClient chatClient = new ChatClient(clientSocket);
        addObserver(chatClient);

        // Start a thread to handle incoming messages from the client
        new Thread(chatClient).start();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void stop() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        try {
            server.start(12345);

            // Waiting for messages from the client
            while (true) {
                // Simulate receiving a message from the client
                String receivedMessage = "Mensagem muito importante do servidor!";
                System.out.println("You: " + receivedMessage);
                server.notifyObservers(receivedMessage);
                sleep(2000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                server.stop();
            } catch (IOException e) {
                e.printStackTrace();
                exit(1);
            }
        }
    }
}
