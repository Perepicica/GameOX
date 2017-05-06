import java.util.HashMap;
import java.util.Map;

public final class XXOO {

    public enum Move {
        X, O;

        public Move opposite() {
            return this == X ? O : X;
        }
    }

    private final int width;
    private final int height;
    private String winner = null;

    public Map<Cell, Move> getMoves() {
        return moves;
    }

    public String getWinner() {
        return winner;
    }

    private final Map<Cell, Move> moves = new HashMap<>();
    private Move turn = Move.X;
    private String player1;
    private String player2;


    public XXOO(int width, int height, String player1, String player2) {
        this.width = width;
        this.height = height;
        this.player1 = player1;
        this.player2 = player2;
    }

    private final static class Cell {
        private final int x;
        private final int y;

        private Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof Cell) {
                Cell cell = (Cell) obj;
                return x == cell.x && y == cell.y;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int result1 = x;
            result1 = 31 * result1 + y;
            return result1;
        }
    }

    public Move get(int x, int y) {
        return get(new Cell(x, y));
    }

    private Move get(Cell cell) {
        return moves.get(cell);
    }

    public Move getMove() {
        return turn;
    }

    public void makeTurn(int x, int y) {
        Cell cell = new Cell(x, y);
        if (get(x, y) != null)
            throw new IllegalArgumentException("Эта ячейка уже занята, попробуйте другую!");
        if (x < 0 || x >= width) throw new IllegalArgumentException("x должен быть не меньше 0 и меньше " + width);
        if (y < 0 || y >= height) throw new IllegalArgumentException("у должен быть не меньше 0 и меньше " + height);
        moves.put(cell, turn);
        gettWinner();
        turn = turn.opposite();
    }

    public void clearCell(int x, int y) {
        if (x < 0 || x >= width) throw new IllegalArgumentException("x должен быть не меньше 0 и не больше " + width);
        if (y < 0 || y >= height) throw new IllegalArgumentException("у должен быть не меньше 0 и не больше " + height);
        Cell cell = new Cell(x, y);
        moves.put(cell, null);
    }

    Move move = Move.O;

    public void gettWinner() {
        move = move.opposite();
        int win = 0;
        //ищу победителя в строчках
        for (int x = 0; x < width; x++) {
            int count = 0;
            for (int y = 0; y < height; y++) {
                Cell cell = new Cell(x, y);
                if (move.equals(get(x, y))) count++;
            }
            if (count == width) win++;
        }
        // ищу победителя в столбцах
        for (int y = 0; y < height; y++) {
            int count = 0;
            for (int x = 0; x < width; x++) {
                Cell cell = new Cell(x, y);
                if (move.equals(get(x, y))) count++;
            }
            if (count == width) win++;
        }
        //ищу победителя в главной диагонали
        int count = 0;
        for (int x = 0; x < width; x++) {
            int y = x;
            Cell cell = new Cell(x, y);
            if (move.equals(get(x, y))) count++;
        }
        if (count == width) win++;
        //ищу победителя в побочнй диагонали
        count = 0;
        for (int x = 0; x < width; x++) {
            int y = width - 1 - x;
            Cell cell = new Cell(x, y);
            if (move.equals(get(x, y))) count++;
        }
        if (count == width) win++;
        if (win > 0 && move.equals(Move.X)) winner = player1;
        if (win > 0 && move.equals(Move.O)) winner = player2;

    }

    @Override
    public String toString() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                return get(x, y) + " ";
            }
            return "/n";
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof XXOO) {
            XXOO xxoo = (XXOO) obj;
            if (width == xxoo.width && height == xxoo.height) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        return get(x, y) == xxoo.get(x, y);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 17 * result + height;
        return result;
    }
}
