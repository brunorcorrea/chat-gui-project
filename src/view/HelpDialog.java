package view;

import javax.swing.*;
import java.awt.*;

public class HelpDialog extends JDialog {

    private static final String message =
            "Bem-vindo ao Chat para conversação - GRUPO 10 - POO II\n\n" +
                    "Automaticamente, ao abrir o programa, um servidor é iniciado na porta 5000, caso ela já esteja ocupada, ele tentará usar alguma outra porta até a 6000.\n\n" +
                    "Para se conectar a um servidor, basta clicar em Arquivo, depois clicar em Conectar no menu superior e preencher os campos com o IP e a porta do servidor.\n" +
                    "Com isso, você e o outro usuário estarão conectados e poderão conversar.\n\n" +
                    "Caso deseje que outra pessoa se conecte no seu servidor, basta passar o seu IP e a porta que você está usando.\n" +
                    "Com isso, a pessoa poderá se conectar ao seu servidor e vocês poderão conversar.\n\n" +
                    "Para enviar uma mensagem, basta digitar a mensagem no campo de texto e clicar em Enviar.\n\n" +
                    "Para encerrar a aplicação, basta clicar no X no canto superior direito da janela ou clicar no menu Arquivo e clicar em Sair.";

    public HelpDialog(JFrame parent) {
        super(parent, "Ajuda", true);

        JPanel helpPanel = new JPanel();
        helpPanel.setLayout(new BorderLayout());

        JTextArea messageArea = new JTextArea(message);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);

        helpPanel.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(closeEvent -> this.dispose());
        helpPanel.add(closeButton, BorderLayout.SOUTH);

        this.getContentPane().add(helpPanel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }
}
