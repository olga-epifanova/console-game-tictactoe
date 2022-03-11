public class GameField {

    private final char[][] cells = {
            {'-', '-', '-'},
            {'-', '-', '-'},
            {'-', '-', '-'}
    };
    private static final char defaultCellSign = '-';

    public static char getDefaultCellSign() {
        return defaultCellSign;
    }

    public void setCell(int y, int x, char sign) {
        cells[y][x] = sign;
    }

    public char getCell(int y, int x) {
        return cells[y][x];
    }

    public void showGameField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" |" + cells[i][j] + "| ");
            }
            System.out.println();
        }
    }

    public void cleanGameField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = defaultCellSign;
            }
        }
    }

    public boolean isFullGameField() {
        boolean isFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[i][j] == defaultCellSign) {
                    isFull = false;
                    break;
                };
            }
        }
        return isFull;
    }

}