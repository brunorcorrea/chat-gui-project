package teste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient implements Observer, Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ChatClient(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            // Waiting for messages from the server
            while (true) {
                // Simulate receiving a message from the server
                String receivedMessage = in.readLine();
                System.out.println("Received message from server: " + receivedMessage);

                // Sleep for a while to simulate some processing
                Thread.sleep(2000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String message) {
        // Send a message to the server when the client is updated
        out.println("Client updated with message: " + message);
    }

    public void disconnect() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 12345)) {
            ChatClient client = new ChatClient(socket);

            // Start a thread to handle incoming messages from the server
            new Thread(client).start();

            // Simulate sending a message to the server
            client.update("Hello from client!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
