package com.olgaepifanova.tictactoe;

import com.olgaepifanova.tictactoe.replay.GameReplay;
import com.olgaepifanova.tictactoe.replay.GameReplayJSON;
import com.olgaepifanova.tictactoe.replay.GameReplayXML;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите действие");
            System.out.println("1 - Начать новую игру");
            System.out.println("2 - Воспроизвести игру по файлу (xml или json)");
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
        System.out.println("Введите наименование файла в формате имяфайла.xml или имяфайла.json");
        String fileName = scanner.nextLine();
        File file = new File(fileName);
        GameReplay gameReplay;
        if (file.exists()) {
            if (fileName.endsWith(".xml")) {
                gameReplay = new GameReplayXML();
                gameReplay.parseFile(file);
            } else if (fileName.endsWith(".json")) {
                gameReplay = new GameReplayJSON();
                gameReplay.parseFile(file);
            } else {
                System.out.println("Некорректный формат файла");
            }
        } else {
            System.out.println("Файл не найден");
        }
    }

}
