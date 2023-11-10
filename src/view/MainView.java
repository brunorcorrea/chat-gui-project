package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView {
    public static void show() {
        JFrame frame = new JFrame("Menu Padrão");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JMenuBar menuBar = new JMenuBar();

        JMenu arquivoMenu = new JMenu("Arquivo");

        JMenuItem abrirItem = new JMenuItem("Abrir");
        JMenuItem salvarItem = new JMenuItem("Salvar");
        JMenuItem sairItem = new JMenuItem("Sair");

        abrirItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para abrir um arquivo
            }
        });

        salvarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para salvar o arquivo
            }
        });

        sairItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Encerrar a aplicação
            }
        });

        arquivoMenu.add(abrirItem);
        arquivoMenu.add(salvarItem);
        arquivoMenu.addSeparator(); // Adicionar um separador
        arquivoMenu.add(sairItem);

        menuBar.add(arquivoMenu);
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }
}
