package view;

import connect.ChatClient;
import connect.ChatServer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainView {
    private ChatClient chatClient;
    private ChatServer server = new ChatServer();

    public ChatClient getChatClient() {
        return chatClient;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    private JFrame mainFrame;

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    private JTextArea conversationArea;
    private JMenuBar menuBar;

    public void show() {
        mainFrame = new JFrame("Chat para conversação - GRUPO 10 - POO II");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        createMenuBar(mainFrame);

        createConversationAreaWithScroll(mainFrame);

        createSendMessagePanel(mainFrame);

        createStatusPanel(mainFrame);

        mainFrame.setVisible(true);

// try {
//            server.start(1234);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        new Thread(() -> {
            try {
                server.start(1234);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

//       new Thread(() -> {
//            while (true) {
//                try {
//                    String message = server.receiveMessage();
//                    conversationArea.append(message + "\n");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    private void createStatusPanel(JFrame frame) {
        JLabel statusLabel = new JLabel("Status da Conexão: Não conectado   ");

        JPanel statusPanel = new JPanel();
        statusPanel.add(statusLabel);

        frame.add(statusPanel, BorderLayout.SOUTH);
    }

    private void createSendMessagePanel(JFrame frame) {
        JPanel sendMessagePanel = new JPanel();

        JTextField message = new JTextField();
        message.setPreferredSize(new Dimension(300, 30));

        JButton sendButton = new JButton("Enviar");
        sendButton.addActionListener(e -> {
            String messageText = message.getText();
            if (chatClient != null) {
                chatClient.sendMessage(messageText);
            }
        });

        sendMessagePanel.add(message, BorderLayout.NORTH);
        sendMessagePanel.add(sendButton, BorderLayout.CENTER);
        frame.add(sendMessagePanel, BorderLayout.CENTER);
    }

    private void createConversationAreaWithScroll(JFrame frame) {
        conversationArea = new JTextArea();
        conversationArea.setEditable(false);
        conversationArea.setLineWrap(true);
        conversationArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(conversationArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(700, 700));

        frame.add(scrollPane, BorderLayout.NORTH);
    }

    private void createMenuBar(JFrame frame) {
        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem connectItem = new JMenuItem("Conectar");

        JMenuItem exitMenu = new JMenuItem("Sair");
        exitMenu.addActionListener(e -> {
            if (chatClient != null) {
                try {
                    chatClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            System.exit(0);
        });

        JMenu helpMenu = new JMenu("Ajuda");
        JMenuItem helpItem = new JMenuItem("Ajuda");
        JMenuItem aboutItem = new JMenuItem("Sobre");

        aboutItem.addActionListener(e -> new AboutDialog(frame));
        connectItem.addActionListener(e -> new ConnectDialog(this));

        fileMenu.add(connectItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenu);
        menuBar.add(fileMenu);

        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);
    }
}
