import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class GameReplay {

    public static void parseXML(File xmlFile) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList playerList = document.getElementsByTagName("Player");

            ArrayList<Player> players = new ArrayList<>();
            if (playerList.getLength() > 0) {
                players.add(getPlayer(playerList.item(0)));
                players.add(getPlayer(playerList.item(1)));
            }
            for (Player player : players) {
                System.out.println("Игрок №" + player.getplayerNumber() + " " + player.getPlayerName()
                        + ", знак " + player.getPlayerSign());
            }

            NodeList stepList = document.getElementsByTagName("Step");
            ArrayList<Step> steps = new ArrayList<>();
            for (int i = 0; i < stepList.getLength(); i++) {
                steps.add(getStep(stepList.item(i), players));
            }

            showSteps(steps);

            NodeList gameResult = document.getElementsByTagName("GameResult").item(0).getChildNodes();
            if (gameResult.item(0) != null) {
                if (gameResult.item(0).getNodeValue().equals("Draw!")) {
                    System.out.println("Ничья!");
                } else {
                    Node winner = gameResult.item(0).getNextSibling();
                    Player winnerPlayer = getPlayer(winner);
                    System.out.println("Победитель: Игрок №" + winnerPlayer.getplayerNumber() + " "
                            + winnerPlayer.getPlayerName() + ", знак " + winnerPlayer.getPlayerSign());
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }

    private static Player getPlayer(Node node) {
        int id = Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue());
        String name = node.getAttributes().getNamedItem("name").getNodeValue();
        String symbol = node.getAttributes().getNamedItem("symbol").getNodeValue();
        char symbolChar = symbol.charAt(0);
        Player player = new Player(id, name, symbolChar);
        return player;
    }

    private static Step getStep(Node node, ArrayList<Player> players) {
        int playerId = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getNodeValue());
        Player playerStep = players.get(playerId - 1);
        String coordinates = adaptСoordinates(node.getFirstChild().getNodeValue());

        int x = Integer.parseInt(coordinates.substring(0, 1));
        int y = Integer.parseInt(coordinates.substring(coordinates.length() - 1));
        Step step = new Step(playerStep, x, y);
        return step;
    }

    private static void showSteps(ArrayList<Step> steps) {
        GameField gameField = new GameField();
        for (Step step : steps) {
            int y = step.getCoordinateY();
            int x = step.getCoordinateX();
            char sign = step.getPlayer().getPlayerSign();
            gameField.setCell(y - 1, x - 1, sign);
            gameField.showGameField();
            System.out.println();
        }
    }

    private static String adaptСoordinates(String coordinates) {
        String result;
        switch (coordinates) {
            case "1":
                result = "1 1";
                break;
            case "2":
                result = "2 1";
                break;
            case "3":
                result = "3 1";
                break;
            case "4":
                result = "1 2";
                break;
            case "5":
                result = "2 2";
                break;
            case "6":
                result = "3 2";
                break;
            case "7":
                result = "1 3";
                break;
            case "8":
                result = "2 3";
                break;
            case "9":
                result = "3 3";
                break;
            default:
                result = coordinates;
        }
        return result;
    }


}