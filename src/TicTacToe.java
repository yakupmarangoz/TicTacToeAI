import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe {

    private JFrame frame;
    private JButton[][] buttons;
    private int[][] board;
    private boolean gameActive;

    public TicTacToe() {
        frame = new JFrame("Tic Tac Toe");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        board = new int[3][3];
        gameActive = true;

        initializeBoard();
        frame.setVisible(true);
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                final int x = i;
                final int y = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (gameActive && board[x][y] == 0) {
                            playerMove(x, y);
                        }
                    }
                });
                frame.add(buttons[i][j]);
            }
        }
    }

    private void playerMove(int x, int y) {
        board[x][y] = AI.HUMAN;
        buttons[x][y].setText("X");
        buttons[x][y].setEnabled(false);

        if (AI.Won(board, AI.HUMAN)) {
            endGame("You Win!");
            return;
        }

        if (AI.emptyCells(board).isEmpty()) {
            endGame("It's a Draw!");
            return;
        }

        aiMove();
    }

    private void aiMove() {
        int[] aiMove = AI.AITurn(board);
        if (aiMove != null) {
            board[aiMove[0]][aiMove[1]] = AI.COMP;
            buttons[aiMove[0]][aiMove[1]].setText("O");
            buttons[aiMove[0]][aiMove[1]].setEnabled(false);
            
            if (AI.Won(board, AI.COMP)) {
                endGame("Computer Wins!");
                return;
            }
            
            if (AI.emptyCells(board).isEmpty()) {
                endGame("It's a Draw!");
            }
        }
    }

    private void endGame(String message) {
        JOptionPane.showMessageDialog(frame, message);
        gameActive = false;

        int option = JOptionPane.showConfirmDialog(frame, "Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            resetBoard();
        } else {
            frame.dispose();
        }
    }

    private void resetBoard() {
        gameActive = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }

}
