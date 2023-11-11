package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView {
    public static void show() {
        JFrame frame = new JFrame("Chat para conversação - GRUPO 10 - POO II");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); ///TODO use gridlayout

        createMenuBar(frame);

        createConversationAreaWithScroll(frame);

        createSendMessagePanel(frame);

        createStatusPanel(frame);

        frame.setVisible(true);
    }

    private static void createStatusPanel(JFrame frame) {
        JLabel statusLabel = new JLabel("Status da Conexão: ");

        JPanel statusPanel = new JPanel();
        statusPanel.add(statusLabel);

        frame.add(statusPanel, BorderLayout.SOUTH);
    }

    private static void createSendMessagePanel(JFrame frame) {
        JPanel sendMessagePanel = new JPanel();

        JTextField message = new JTextField();
        message.setPreferredSize(new Dimension(300, 30));

        JButton sendButton = new JButton("Enviar");

        sendMessagePanel.add(message, BorderLayout.NORTH);
        sendMessagePanel.add(sendButton, BorderLayout.CENTER);
        frame.add(sendMessagePanel, BorderLayout.CENTER);
    }

    private static void createConversationAreaWithScroll(JFrame frame) {
        JTextArea conversationArea = new JTextArea();
        conversationArea.setEditable(false);
        conversationArea.setLineWrap(true);
        conversationArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(conversationArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(700, 700));
        frame.add(scrollPane, BorderLayout.NORTH);
    }

    private static void createMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem connectItem = new JMenuItem("Conectar");

        JMenuItem exitMenu = new JMenuItem("Sair");

        JMenu helpMenu = new JMenu("Ajuda");
        JMenuItem helpItem = new JMenuItem("Ajuda");
        JMenuItem aboutItem = new JMenuItem("Sobre");

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
