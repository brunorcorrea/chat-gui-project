package view;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class ConnectDialog extends JDialog {

    public ConnectDialog(MainView mainView) {
        super(mainView.getMainFrame(), "Conectar", true);

        JTextField ipAddressField = new JTextField();
        JTextField portField = new JTextField();
        JButton connectButton = createConnectButton(mainView, ipAddressField, portField);

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

    private JButton createConnectButton(MainView mainView, JTextField ipAddressField, JTextField portField) {
        JButton connectButton = new JButton("Conectar");

        connectButton.addActionListener(e -> {
            String ipAddress = ipAddressField.getText();
            String portText = portField.getText();

            if (ipAddress.isEmpty() || portText.isEmpty()) {
                JOptionPane.showMessageDialog(mainView.getMainFrame(), "Por favor, preencha todos os campos.");
                return;
            }

            try {
                int port = Integer.parseInt(portText);

                if (isLocalMachineIpAddress(ipAddress) && port == mainView.getController().getChatServer().getServerSocket().getLocalPort()) {
                    JOptionPane.showMessageDialog(mainView.getMainFrame(), "Você não pode se conectar ao seu próprio servidor.");
                    return;
                }

                mainView.getController().startClient(ipAddress, port, mainView.getStatusLabel());

                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainView.getMainFrame(), "A porta deve ser um número válido.");
            }
        });
        return connectButton;
    }

    private boolean isLocalMachineIpAddress(String ipAddress) {
        try {
            InetAddress[] localAddresses = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
            InetAddress inetAddress = InetAddress.getByName(ipAddress);

            return Arrays.asList(localAddresses).contains(inetAddress);
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
