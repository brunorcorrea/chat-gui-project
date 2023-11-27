package view;

import connect.ChatClient;
import connect.PortScanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class MainView {
    private static ChatClient chatClient;

    public static void show() {
        JFrame frame = new JFrame("Chat para conversação - GRUPO 10 - POO II");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        createMenuBar(frame);

        createConversationAreaWithScroll(frame);

        createSendMessagePanel(frame);

        createStatusPanel(frame);

        frame.setVisible(true);
    }

    private static void createStatusPanel(JFrame frame) {
        JLabel statusLabel = new JLabel("Status da Conexão: Não conectado   ");

        JPanel statusPanel = new JPanel();
        statusPanel.add(statusLabel);

        frame.add(statusPanel, BorderLayout.SOUTH);
    }

    private static void createSendMessagePanel(JFrame frame) {
        JPanel sendMessagePanel = new JPanel();

        JTextField message = new JTextField();
        message.setPreferredSize(new Dimension(300, 30));

        JButton sendButton = new JButton("Enviar");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String messageText = message.getText();
                if (chatClient != null) {
                    chatClient.sendMessage(messageText);

                }
            }
        });

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
        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chatClient != null) {
                    try {
                        chatClient.disconnect();
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                }
                System.exit(0);
            }
        });

        JMenuItem scanPortsItem = new JMenuItem("Verificar Portas");
        scanPortsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPortScannerDialog(frame);
            }
        });

        JMenu helpMenu = new JMenu("Ajuda");
        JMenuItem helpItem = new JMenuItem("Ajuda");
        JMenuItem aboutItem = new JMenuItem("Sobre");

        aboutItem.addActionListener(e -> new AboutDialog(frame));
        connectItem.addActionListener(e -> new ConnectDialog(frame));

        fileMenu.add(connectItem);
        fileMenu.add(scanPortsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenu);
        menuBar.add(fileMenu);

        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);

        connectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showConnectDialog(frame);
            }
        });
    }

    private static void showConnectDialog(JFrame frame) {
        JDialog connectDialog = new JDialog(frame, "Conectar", true);
        connectDialog.setSize(300, 150);
        connectDialog.setLayout(new BorderLayout());

        JTextField ipAddressField = new JTextField();
        JTextField portField = new JTextField();
        JButton connectButton = new JButton("Conectar");

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipAddress = ipAddressField.getText();
                String portText = portField.getText();

                if (ipAddress.isEmpty() || portText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos.");
                    return;
                }

                try {
                    int port = Integer.parseInt(portText);
                    chatClient = new ChatClient();
                    chatClient.connect(ipAddress, port);

                    connectDialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "A porta deve ser um número válido.");
                } catch (IOException ex) {

                    JOptionPane.showMessageDialog(frame, "Erro ao conectar ao servidor.");
                }
            }
        });

        JPanel connectPanel = new JPanel();
        connectPanel.setLayout(new GridLayout(3, 2));
        connectPanel.add(new JLabel("IP Address:"));
        connectPanel.add(ipAddressField);
        connectPanel.add(new JLabel("Port:"));
        connectPanel.add(portField);
        connectPanel.add(new JLabel());
        connectPanel.add(connectButton);

        connectDialog.add(connectPanel, BorderLayout.CENTER);
        connectDialog.setVisible(true);
    }

    private static void showPortScannerDialog(JFrame frame) {
        JDialog portScannerDialog = new JDialog(frame, "Verificar Portas Disponíveis", true);
        portScannerDialog.setSize(300, 150);
        portScannerDialog.setLayout(new BorderLayout());

        JTextField ipAddressField = new JTextField();
        JTextField startPortField = new JTextField();
        JTextField endPortField = new JTextField();
        JButton scanButton = new JButton("Verificar Portas Disponíveis");

        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipAddress = ipAddressField.getText();
                String startPortText = startPortField.getText();
                String endPortText = endPortField.getText();

                if (ipAddress.isEmpty() || startPortText.isEmpty() || endPortText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos.");
                    return;
                }

                try {
                    int startPort = Integer.parseInt(startPortText);
                    int endPort = Integer.parseInt(endPortText);

                    PortScanner.scanPorts(ipAddress, startPort, endPort);
                    portScannerDialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "As portas devem ser números válidos.");
                }
            }
        });

        JPanel portScannerPanel = new JPanel();
        portScannerPanel.setLayout(new GridLayout(3, 2));
        portScannerPanel.add(new JLabel("IP Address:"));
        portScannerPanel.add(ipAddressField);
        portScannerPanel.add(new JLabel("Start Port:"));
        portScannerPanel.add(startPortField);
        portScannerPanel.add(new JLabel("End Port:"));
        portScannerPanel.add(endPortField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(scanButton);

        portScannerDialog.add(portScannerPanel, BorderLayout.CENTER);
        portScannerDialog.add(buttonPanel, BorderLayout.SOUTH);
        portScannerDialog.setVisible(true);
    }
}
