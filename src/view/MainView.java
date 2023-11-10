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

        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);

        JTextArea conversation = new JTextArea();
        conversation.setEditable(false);
        conversation.setLineWrap(true);
        conversation.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(conversation);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(700, 700));

        JPanel panel = new JPanel();

        JTextField message = new JTextField();
        message.setPreferredSize(new Dimension(300, 30));

        JButton sendButton = new JButton("Enviar");

        panel.add(message, BorderLayout.NORTH);
        panel.add(sendButton, BorderLayout.CENTER);

        JLabel statusLabel = new JLabel("Status da Conexão: ");

        JPanel panel2 = new JPanel();
        panel2.add(statusLabel);

        frame.add(panel, BorderLayout.CENTER);


        frame.add(panel2, BorderLayout.SOUTH);
        frame.add(scrollPane, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
