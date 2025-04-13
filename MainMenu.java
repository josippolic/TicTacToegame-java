import javax.swing.*;
import java.awt.*;

public class MainMenu {
    JFrame mainFrame;

    public MainMenu() {
        mainFrame = new JFrame("Tic Tac Toe Menu");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 200);
        mainFrame.setLayout(new GridLayout(3, 1));

        JButton playBtn = new JButton("Play Tic Tac Toe");
        JButton rulesBtn = new JButton("Rules");
        JButton exitBtn = new JButton("Exit");

        playBtn.addActionListener(e -> {
            mainFrame.dispose(); // zatvara meni
            new TicTacToeGame(); // pokreće igru
        });

        rulesBtn.addActionListener(e -> showRules());
        exitBtn.addActionListener(e -> System.exit(0));

        mainFrame.add(playBtn);
        mainFrame.add(rulesBtn);
        mainFrame.add(exitBtn);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    void showRules() {
        JFrame rulesFrame = new JFrame("Rules");
        rulesFrame.setSize(400, 200);
        rulesFrame.setLayout(new BorderLayout());

        JTextArea rulesText = new JTextArea(
            "Tic Tac Toe Rules:\n\n" +
            "1. Players take turns placing X or O.\n" +
            "2. First to line up 3 wins.\n" +
            "3. If the board fills with no winner, it's a tie."
        );
        rulesText.setWrapStyleWord(true);
        rulesText.setLineWrap(true);
        rulesText.setEditable(false);
        rulesText.setMargin(new Insets(10, 10, 10, 10));

        JButton backBtn = new JButton("Back to Menu");
        backBtn.addActionListener(e -> rulesFrame.dispose());

        rulesFrame.add(rulesText, BorderLayout.CENTER);
        rulesFrame.add(backBtn, BorderLayout.SOUTH);
        rulesFrame.setLocationRelativeTo(null);
        rulesFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
