import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameReplay {

    private static final Map<String, String> coordinatesMap = new HashMap<>();
    static {
        coordinatesMap.put("1", "1 1");
        coordinatesMap.put("2", "2 1");
        coordinatesMap.put("3", "3 1");
        coordinatesMap.put("4", "1 2");
        coordinatesMap.put("5", "2 2");
        coordinatesMap.put("6", "3 2");
        coordinatesMap.put("7", "1 3");
        coordinatesMap.put("8", "2 3");
        coordinatesMap.put("9", "3 3");
    }

    public static void parseXML(File xmlFile) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList playerList = document.getElementsByTagName("Player");

            List<Player> players = new ArrayList<>();
            if (playerList.getLength() > 1) {
                players.add(getPlayer(playerList.item(0)));
                players.add(getPlayer(playerList.item(1)));
            }
            for (Player player : players) {
                System.out.println(String.format("Игрок №%s %s, знак %s",
                        player.getplayerNumber(), player.getPlayerName(), player.getPlayerSign()));
            }

            NodeList stepList = document.getElementsByTagName("Step");
            List<Step> steps = new ArrayList<>();
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
                    System.out.println(String.format("Победитель: Игрок №%s %s, знак %s",
                            winnerPlayer.getplayerNumber(), winnerPlayer.getPlayerName(), winnerPlayer.getPlayerSign()));
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
        return new Player(id, name, symbolChar);
    }

    private static Step getStep(Node node, List<Player> players) {
        int playerId = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getNodeValue());
        Player playerStep = players.get(playerId - 1);
        String coordinates = adaptCoordinates(node.getFirstChild().getNodeValue());

        int x = Integer.parseInt(coordinates.substring(0, 1));
        int y = Integer.parseInt(coordinates.substring(coordinates.length() - 1));
        return new Step(playerStep, x, y);

    }

    private static void showSteps(List<Step> steps) {
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

    private static String adaptCoordinates(String coordinates) {
        return coordinatesMap.getOrDefault(coordinates, coordinates);
    }

}