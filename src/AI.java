import java.util.ArrayList;
import java.util.List;

public class AI {
    public static int HUMAN = -1;
    public static int COMP = 1;

    public static boolean Won(int[][] state, int player) {
        int[][] winstates = new int[][] {
            {state[0][0], state[0][1], state[0][2]},
            {state[1][0], state[1][1], state[1][2]},
            {state[2][0], state[2][1], state[2][2]},
            {state[0][0], state[1][0], state[2][0]},
            {state[0][1], state[1][1], state[2][1]},
            {state[0][2], state[1][2], state[2][2]},
            {state[0][0], state[1][1], state[2][2]},
            {state[2][0], state[1][1], state[0][2]},
        };
        for (int[] row : winstates) {
            if (player == row[0] && player == row[1] && player == row[2]) {
                return true;
            }
        }
        return false;
    }

    public static int evaluate(int[][] state) {
        if (Won(state, HUMAN)) {
            return -1;
        } else if (Won(state, COMP)) {
            return 1;
        } else {
            return 0;
        }
    }

    public static List<int[]> emptyCells(int[][] state) {
        List<int[]> moveable = new ArrayList<>();
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                if (state[i][j] == 0) {
                    moveable.add(new int[] {i, j});
                }
            }
        }
        return moveable;
    }

    private static boolean game_over(int[][] state) {
        return Won(state, HUMAN) || Won(state, COMP) || emptyCells(state).isEmpty();
    }

    public static int minimax(int[][] state, int depth, int player, int alpha, int beta) {
        if (depth == 0 || game_over(state)) {
            return evaluate(state);
        }
        if (player == COMP) {
            int best = Integer.MIN_VALUE;
            for (int[] cell : emptyCells(state)) {
                int x = cell[0];
                int y = cell[1];
                state[x][y] = COMP;
                best = Integer.max(best, minimax(state, depth - 1, HUMAN, alpha, beta));
                state[x][y] = 0;
                alpha = Integer.max(alpha, best);
                if(alpha>= beta)
                {
                    break;
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int[] cell : emptyCells(state)) {
                int x = cell[0];
                int y = cell[1];
                state[x][y] = HUMAN;
                best = Integer.min(best, minimax(state, depth - 1, COMP, alpha, beta));
                state[x][y] = 0;
                beta = Integer.min(beta, best);
                if (beta <= alpha) {
                    break;
                }
            }
            return best;
        }
    }

    public static int[] AITurn(int[][] state) {
        int depth = emptyCells(state).size();
        if (depth == 0 || game_over(state)) {
            return null;
        }

        int[] bestMove = null;
        int bestValue = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (int[] cell : emptyCells(state)) {
            int x = cell[0];
            int y = cell[1];
            state[x][y] = COMP;
            int moveValue = minimax(state, depth - 1, HUMAN, alpha,beta);
            state[x][y] = 0;

            if (moveValue > bestValue) {
                bestValue = moveValue;
                bestMove = new int[] {x, y};
            }
            alpha = Integer.max(alpha, bestValue);
            if (beta <= alpha) 
            {
                break; 
            }
        }

        return bestMove;
    }
}
