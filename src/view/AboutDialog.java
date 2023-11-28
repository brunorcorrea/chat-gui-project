package view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AboutDialog extends JDialog {
    private static final String CURRENT_PATH = System.getProperty("user.dir");
    private static final String IMAGES_FOLDER = CURRENT_PATH + "\\src\\images\\";

    private final String projectTitle = "Chat para conversação - GRUPO 10 - POO II";
    private final String authorsTitle = "Autores:";
    private final List<String> authors = List.of("Bruno Ricardo Corrêa - 260759",
            "Felipe Gabriel Viana Alves - 246258",
            "Igor Paulo dos Santos Santana - 198643",
            "João Gabriel Gomes Mariano - 247487",
            "Julia Gonzalez Leal - 260824");

    private final Map<String, String> authorImages = new HashMap<>();

    public AboutDialog(JFrame parent) {
        super(parent, "Sobre", true);

        initializeAuthorImages();

        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS));

        JLabel projectTitleLabel = new JLabel(projectTitle);
        JLabel authorsTitleLabel = new JLabel(authorsTitle);

        aboutPanel.add(projectTitleLabel);
        aboutPanel.add(authorsTitleLabel);

        addAuthorsToPanel(aboutPanel);

        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(closeEvent -> this.dispose());
        aboutPanel.add(closeButton, BorderLayout.SOUTH);

        this.getContentPane().add(aboutPanel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void initializeAuthorImages() {
        authorImages.put("Bruno Ricardo Corrêa - 260759", "bruno.png");
        authorImages.put("Felipe Gabriel Viana Alves - 246258", "felipe.png");
        authorImages.put("Igor Paulo dos Santos Santana - 198643", "igor.png");
        authorImages.put("João Gabriel Gomes Mariano - 247487", "joao.png");
        authorImages.put("Julia Gonzalez Leal - 260824", "julia.png");
    }

    private void addAuthorsToPanel(JPanel panel) {
        authors.forEach(author -> {
            String imageName = authorImages.get(author);
            ImageIcon imageIcon = new ImageIcon(IMAGES_FOLDER + imageName);
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(100, 100, 100));
            JLabel authorLabel = new JLabel("• " + author, imageIcon, JLabel.LEFT);
            panel.add(authorLabel);
        });
    }
}
