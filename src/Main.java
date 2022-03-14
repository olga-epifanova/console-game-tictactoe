import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите действие");
            System.out.println("1 - Начать новую игру");
            System.out.println("2 - Воспроизвести игру по xml файлу");
            int answer;
            if (scanner.hasNextInt()) {
                answer = scanner.nextInt();
            } else {
                System.out.println("Введено некорректное значение!");
                scanner.nextLine();
                continue;
            }
            if (answer == 1) {
                startGame();
                break;
            } else if (answer == 2) {
                replayGame();
                break;
            } else {
                System.out.println("Введено некорректное значение!");
            }
        }
    }

    public static void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя первого игрока");
        String firstName = scanner.nextLine();
        Player firstPlayer = new Player(1, firstName, 'X');
        System.out.println("Введите имя второго игрока");
        String secondName = scanner.nextLine();
        Player secondPlayer = new Player(2, secondName, 'O');

        Game game = new Game(firstPlayer, secondPlayer);
        game.startGame();
    }

    public static void replayGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите наименование xml файла в формате имяфайла.xml");
        String fileName = scanner.nextLine();
        File xmlFile = new File(fileName);
        if (xmlFile.exists()) {
            GameReplay.parseXML(xmlFile);
        } else {
            System.out.println("Файл не найден");
        }
    }

}
