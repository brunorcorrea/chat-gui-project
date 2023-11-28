package teste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.System.exit;

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
                System.out.println("Them: " + receivedMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String message) {
        // Send a message to the server when the client is updated
        out.println(message);
    }

    public void disconnect() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public static void main(String[] args) {
        try {
            ChatClient client = new ChatClient(new Socket("127.0.0.1", 12345));

            // Start a thread to handle incoming messages from the server
            new Thread(client).start();

            // Simulate sending a message to the server
            String sentMessage = "Mensagem muito importante do cliente!";
            System.out.println("You: " + sentMessage);
            client.update(sentMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
