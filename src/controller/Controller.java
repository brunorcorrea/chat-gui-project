package controller;

import connect.ChatClient;
import connect.ChatServer;

import javax.swing.*;
import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.CountDownLatch;

public class Controller {
    private ChatClient chatClient;
    private final ChatServer chatServer = new ChatServer();
    private final CountDownLatch serverStartedLatch = new CountDownLatch(1);
    private JLabel statusLabel;

    public ChatServer getChatServer() {
        return chatServer;
    }

    public void startServer(JLabel statusLabel) {
        this.statusLabel = statusLabel;
        new Thread(() -> {
            int port = 5000;
            try {
                while (port < 6000) {
                    chatServer.start(port);
                    statusLabel.setText("Status da Conexão: Conectado");
                    serverStartedLatch.countDown();
                    break;
                }
            } catch (IOException e) {
                handleIOException(e);
            }
        }).start();
    }

    public void startClient(String ipAddress, int port, JLabel statusLabel) {
        this.statusLabel = statusLabel;
        new Thread(() -> {
            try {
                chatClient = new ChatClient();
                chatClient.connect(ipAddress, port);
                statusLabel.setText("Status da Conexão: Conectado");
            } catch (IOException e) {
                handleIOException(e);
            }
        }).start();
    }

    public void showReceivedMessage(JTextArea conversationArea) {
        new Thread(() -> {
            try {
                serverStartedLatch.await();
                while (true) {
                    try {
                        if (chatServer.getIn() != null) {
                            String message = chatServer.receiveMessage();
                            if (message != null) {
                                conversationArea.append("Outro: " + message + "\n");
                            }
                        } else if (chatClient != null && chatClient.getIn() != null) {
                            String message = chatClient.receiveMessage();
                            if (message != null) {
                                conversationArea.append("Outro: " + message + "\n");
                            }
                        }
                        Thread.sleep(1000);
                    } catch (SocketException se) {
                        handleSocketException(se);
                        handleDisconnection();
                        break;
                    } catch (IOException e) {
                        handleIOException(e);
                        handleDisconnection();
                    } catch (InterruptedException ie) {
                        handleInterruptedException(ie);
                        handleDisconnection();
                        break;
                    }
                }
            } catch (InterruptedException ie) {
                handleInterruptedException(ie);
            }
        }).start();
    }

    public void sendMessage(JTextField textField, JTextArea conversationArea) {
        String messageText = textField.getText().trim();
        if (messageText.isEmpty()) return;

        if (chatClient != null) {
            chatClient.sendMessage(messageText);
        } else {
            var clientSocket = chatServer.getClientSocket();
            if (clientSocket != null && !clientSocket.isClosed()) {
                chatServer.sendMessage(messageText);
            }
        }
        conversationArea.append("Voce: " + messageText + "\n");
        textField.setText("");
    }

    public void stopServer() {
        try {
            chatServer.stop();
            handleDisconnection();
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    public void stopClient() {
        if (chatClient != null) {
            try {
                chatClient.disconnect();
                handleDisconnection();
            } catch (IOException e) {
                handleIOException(e);
            }
        }
    }

    private void handleDisconnection() {
        resetConnectionState();
        SwingUtilities.invokeLater(() -> statusLabel.setText("Status da Conexão: Não conectado"));
    }

    private void resetConnectionState() {
        chatClient = null;
        serverStartedLatch.countDown();
    }

    private void handleIOException(IOException e) {
        e.printStackTrace();

        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(null, "Erro de E/S: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE));
    }

    private void handleSocketException(SocketException se) {
        se.printStackTrace();
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(null, "Erro de Socket: " + se.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE));
    }

    private void handleInterruptedException(InterruptedException ie) {
        ie.printStackTrace();

        Thread.currentThread().interrupt();
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(null, "Thread interrompida: " + ie.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE));
    }
}


