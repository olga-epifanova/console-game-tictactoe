import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя первого игрока");
        String firstName = scanner.nextLine();
        Player firstPlayer = new Player(firstName, 'X');
        System.out.println("Введите имя второго игрока");
        String secondName = scanner.nextLine();
        Player secondPlayer = new Player(secondName, 'O');

        Game game = new Game(firstPlayer, secondPlayer);
        game.startGame();

    }
}
