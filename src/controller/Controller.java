package controller;

import connect.ChatClient;
import connect.ChatServer;

import javax.swing.*;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Controller {
    private ChatClient chatClient;
    private final ChatServer chatServer = new ChatServer();

    public ChatServer getChatServer() {
        return chatServer;
    }

    public void startServer(JLabel statusLabel) {
        new Thread(() -> {
            int port = 5000;
            while (port < 6000) {
                try {
                    chatServer.start(port);
                    statusLabel.setText("Status da Conexão: Conectado");
                    break;
                } catch (IOException e) {
                    port++;
                }
            }
        }).start();
    }

    public void startClient(String ipAddress, int port, JLabel statusLabel) {
        new Thread(() -> {
            try {
                chatClient = new ChatClient();
                chatClient.connect(ipAddress, port);
                statusLabel.setText("Status da Conexão: Conectado");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void showReceivedMessage(JTextArea conversationArea) {
        new Thread(() -> {
            while (true) {
                try {
                    if(chatServer.getIn() != null) {
                        String message = chatServer.receiveMessage();
                        if(message != null) {
                            conversationArea.append("Them: " + message + "\n");
                        }
                    } else if(chatClient != null && chatClient.getIn() != null) {
                        String message = chatClient.receiveMessage();
                        if(message != null) {
                            conversationArea.append("Them: " + message + "\n");
                        }
                    }
                    sleep(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void sendMessage(JTextField textField, JTextArea conversationArea) {
        String messageText = textField.getText();
        if (chatClient != null) {
            chatClient.sendMessage(messageText);
            conversationArea.append("You: " + messageText + "\n");
            textField.setText("");
        }

        var clientSocket = chatServer.getClientSocket();
        if (clientSocket!= null && !clientSocket.isClosed()) {
            chatServer.sendMessage(messageText);
            conversationArea.append("You: " + messageText + "\n");
            textField.setText("");
        }
    }

    public void stopServer() {
        try {
            chatServer.stop();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void stopClient(JLabel statusLabel) {
        if (chatClient != null) {
            try {
                chatClient.disconnect();
                statusLabel.setText("Status da Conexão: Não conectado");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
