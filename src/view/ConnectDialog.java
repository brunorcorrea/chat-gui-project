package view;

import connect.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ConnectDialog extends JDialog {
    private static ChatClient chatClient;

    public ConnectDialog(JFrame parent) {
        super(parent, "Conectar", true);

        JTextField ipAddressField = new JTextField();
        JTextField portField = new JTextField();
        JButton connectButton = new JButton("Conectar");

        connectButton.addActionListener(e -> {
            String ipAddress = ipAddressField.getText();
            String portText = portField.getText();

            if (ipAddress.isEmpty() || portText.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Por favor, preencha todos os campos.");
                return;
            }

            try {
                int port = Integer.parseInt(portText);
                chatClient = new ChatClient();
                chatClient.connect(ipAddress, port);

                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent, "A porta deve ser um número válido.");
            } catch (IOException ex) {

                JOptionPane.showMessageDialog(parent, "Erro ao conectar ao servidor.");
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

        this.add(connectPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
