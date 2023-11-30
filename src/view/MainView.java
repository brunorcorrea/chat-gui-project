package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainView {
    private final Controller controller = new Controller();

    private JFrame mainFrame;
    private JTextArea conversationArea;
    private JMenuBar menuBar;
    private JLabel statusLabel;

    public void show() {
        mainFrame = new JFrame("Chat para conversação - GRUPO 10 - POO II");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        createMenuBar(mainFrame);

        createConversationAreaWithScroll(mainFrame);

        createSendMessagePanel(mainFrame);

        createStatusPanel(mainFrame);

        mainFrame.setVisible(true);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeApplication();
            }
        });

        controller.startServer(statusLabel);
        controller.showReceivedMessage(conversationArea);
    }

    private void createStatusPanel(JFrame frame) {
        statusLabel = new JLabel("Status da Conexão: Não conectado");

        JPanel statusPanel = new JPanel();
        statusPanel.add(statusLabel);

        frame.add(statusPanel, BorderLayout.SOUTH);
    }

    private void createSendMessagePanel(JFrame frame) {
        JPanel sendMessagePanel = new JPanel();

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 30));

        JButton sendButton = new JButton("Enviar");
        sendButton.addActionListener(e -> controller.sendMessage(textField, conversationArea));

        sendMessagePanel.add(textField, BorderLayout.NORTH);
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
        exitMenu.addActionListener(e -> closeApplication());

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

    private void closeApplication() {
        controller.stopServer();
        controller.stopClient();
        System.exit(0);
    }

    public Controller getController() {
        return controller;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }
}
