import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class TicTacToeGame {
    private JFrame frame;
    private JButton[][] buttons = new JButton[3][3];
    private JLabel statusLabel, xWinsLabel, oWinsLabel;
    private String currentPlayer = "X";
    private int xWins = 0;
    private int oWins = 0;
    private int roundsPlayed = 0;

    public TicTacToeGame() {
        frame = new JFrame("Tic Tac Toe");
        frame.setSize(420, 550); // povećano za bolji layout
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.BLUE);

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(0, 0, 420, 170); // povećano
        topPanel.setBackground(Color.GREEN);

        JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 5, 420, 40);
        topPanel.add(title);

        statusLabel = new JLabel("Player X Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBounds(0, 45, 420, 25); // ispod naslova
        topPanel.add(statusLabel);

        // Score Panel
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        scorePanel.setBounds(0, 75, 420, 30);
        scorePanel.setBackground(Color.GREEN);

        xWinsLabel = new JLabel("X Wins: 0");
        xWinsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        xWinsLabel.setForeground(Color.WHITE);

        oWinsLabel = new JLabel("O Wins: 0");
        oWinsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        oWinsLabel.setForeground(Color.WHITE);

        scorePanel.add(xWinsLabel);
        scorePanel.add(oWinsLabel);
        topPanel.add(scorePanel);

        // Reset Button
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(250, 120, 80, 30);
        resetButton.addActionListener(e -> resetGame());
        topPanel.add(resetButton);

        // Back to Menu Button (manji i crven)
        JButton backButton = new JButton("Back");
        backButton.setBounds(140, 120, 80, 30);
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            frame.dispose(); // Close current game window
            new MainMenu(); // Open main menu
        });
        topPanel.add(backButton);

        frame.add(topPanel);

        // Game board
        JPanel boardPanel = new JPanel();
        boardPanel.setBounds(60, 190, 300, 300);
        boardPanel.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("");
                btn.setFont(new Font("Arial", Font.BOLD, 48));
                int row = i, col = j;
                btn.addActionListener(e -> handleMove(btn, row, col));
                buttons[i][j] = btn;
                boardPanel.add(btn);
            }
        }

        frame.add(boardPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void handleMove(JButton btn, int row, int col) {
        if (!btn.getText().equals("")) return;

        btn.setText(currentPlayer);
        btn.setForeground(currentPlayer.equals("X") ? Color.GREEN : Color.RED);

        if (checkWinner()) {
            if (currentPlayer.equals("X")) {
                xWins++;
                xWinsLabel.setText("X Wins: " + xWins);
            } else {
                oWins++;
                oWinsLabel.setText("O Wins: " + oWins);
            }

            JOptionPane.showMessageDialog(frame, "Player " + currentPlayer + " wins!");
            roundsPlayed++;
            resetBoard();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(frame, "It's a tie!");
            roundsPlayed++;
            resetBoard();
        } else {
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            statusLabel.setText("Player " + currentPlayer + " Turn");
        }

        if (roundsPlayed == 3) {
            declareFinalWinner();
        }
    }

    private boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][0].getText().equals("") &&
                buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][0].getText().equals(buttons[i][2].getText())) return true;

            if (!buttons[0][i].getText().equals("") &&
                buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                buttons[0][i].getText().equals(buttons[2][i].getText())) return true;
        }

        if (!buttons[0][0].getText().equals("") &&
            buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[0][0].getText().equals(buttons[2][2].getText())) return true;

        if (!buttons[0][2].getText().equals("") &&
            buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[0][2].getText().equals(buttons[2][0].getText())) return true;

        return false;
    }

    private boolean isBoardFull() {
        for (JButton[] row : buttons) {
            for (JButton btn : row) {
                if (btn.getText().equals("")) return false;
            }
        }
        return true;
    }

    private void resetBoard() {
        for (JButton[] row : buttons) {
            for (JButton btn : row) {
                btn.setText("");
                btn.setForeground(Color.BLACK);
            }
        }
        currentPlayer = "X";
        statusLabel.setText("Player X Turn");
    }

    private void resetGame() {
        xWins = 0;
        oWins = 0;
        roundsPlayed = 0;
        xWinsLabel.setText("X Wins: 0");
        oWinsLabel.setText("O Wins: 0");
        resetBoard();
    }

    private void declareFinalWinner() {
        String winner;
        if (xWins > oWins) {
            winner = "Player X";
        } else if (oWins > xWins) {
            winner = "Player O";
        } else {
            winner = "No one (Tie)";
        }

        try (FileWriter writer = new FileWriter("pobjeda.txt", true)) {
            writer.write("Winner after 3 rounds: " + winner + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(frame, "After 3 rounds, the winner is: " + winner);
        resetGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeGame::new);
    }
}
