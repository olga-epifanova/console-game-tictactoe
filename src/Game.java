import java.util.Scanner;

public class Game {

    private final Player firstPlayer;
    private final Player secondPlayer;
    private GameField gameField;

    public Game(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public void startGame() {

        gameField = new GameField();
        gameField.showGameField();

        playGame();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Хотите начать новую игру? Введите 1 или 2");
            System.out.println("1 - Да");
            System.out.println("2 - Нет");
            int answer;
            if (scanner.hasNextInt()) {
                answer = scanner.nextInt();
            } else {
                System.out.println("Введено некорректное значение!");
                scanner.nextLine();
                continue;
            }
            if (answer == 1) {
                gameField.cleanGameField();
                gameField.showGameField();
                playGame();
            } else if (answer == 2) {
                System.out.println("Игра окончена.");
                break;
            } else {
                System.out.println("Введено некорректное значение!");
            }

        }
    }

    private void playGame() {
        boolean endGame;
        while (true) {
            endGame = playerMove(firstPlayer);
            if (endGame) break;
            endGame = playerMove(secondPlayer);
            if (endGame) break;
        }
    }

    private boolean playerMove(Player player) {
        makeMove(player);
        gameField.showGameField();
        boolean winner = isWinner(player);
        if (winner) {
            String winnerText = player.getPlayerName() + " победил(а)!";
            System.out.println(winnerText);
            FileResultWriter.writeFile(winnerText);
        }
        return winner;
    }

    private void makeMove(Player player) {
        while (true) {
            System.out.println(player.getPlayerName() + ", введите номер клетки по горизонтали (от 1 до 3)");
            int x = getNumber();
            System.out.println(player.getPlayerName() + ", введите номер клетки по вертикали (от 1 до 3)");
            int y = getNumber();
            if (!isFreeCell(y, x)) {
                System.out.println("Эта ячейка уже занята!");
            } else {
                gameField.setCell(y - 1, x - 1, player.getPlayerSign());
                break;
            }
        }
    }

    private static int getNumber() {
        Scanner scanner = new Scanner(System.in);
        int result = -1;
        do {
            if (scanner.hasNextInt()) {
                int number = scanner.nextInt();
                if (number < 1 || number > 3) {
                    System.out.println("Некорректное значение, введите число от 1 до 3!");
                } else {
                    result = number;
                }
            } else {
                System.out.println("Введено некорректное значение!");
                scanner.nextLine();
            }
        }
        while (result == -1);
        return result;
    }

    private boolean isWinner(Player player) {
        char playerSign = player.getPlayerSign();
        for (int i = 0; i < 3; i++) {
            if ((gameField.getCell(i, 0) == playerSign && gameField.getCell(i, 1) == playerSign && gameField.getCell(i, 2) == playerSign) ||
                    (gameField.getCell(0, i) == playerSign && gameField.getCell(1, i) == playerSign && gameField.getCell(2, i) == playerSign))
                return true;
        }

        return (gameField.getCell(0, 0) == playerSign && gameField.getCell(1, 1) == playerSign && gameField.getCell(2, 2) == playerSign) ||
                (gameField.getCell(2, 0) == playerSign && gameField.getCell(1, 1) == playerSign && gameField.getCell(0, 2) == playerSign);
    }

    private boolean isFreeCell(int y, int x) {
        char defaultCellSign = GameField.getDefaultCellSign();
        return gameField.getCell(y - 1, x - 1) == defaultCellSign;
    }

}