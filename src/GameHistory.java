import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GameHistory {

    private final Player firstPlayer;
    private final Player secondPlayer;
    private final ArrayList<Step> steps = new ArrayList<>();
    private Player winner;
    private final static DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy kk-mm-ss");

    public GameHistory(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public void createHistoryXML() {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document docGameHistory = builder.newDocument();

            Element root = docGameHistory.createElement("Gameplay");
            docGameHistory.appendChild(root);

            addPlayer(firstPlayer, docGameHistory, root);
            addPlayer(secondPlayer, docGameHistory, root);

            Element game = docGameHistory.createElement("Game");
            root.appendChild(game);
            addSteps(docGameHistory, game);

            Element gameResult = docGameHistory.createElement("GameResult");
            root.appendChild(gameResult);
            if (winner == null) {
                gameResult.appendChild(docGameHistory.createTextNode("Draw!"));
            } else {
                addPlayer(winner, docGameHistory, gameResult);
            }

            String currentDateStr = DATE_TIME_FORMAT.format(LocalDateTime.now());
            File file = new File("game " + currentDateStr + ".xml");

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(docGameHistory), new StreamResult(file));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addStep(Step step) {
        steps.add(step);
    }

    private void addSteps(Document doc, Element game) {
        int num = 1;
        for (Step step : steps) {
            Element stepElement = doc.createElement("Step");
            String playerId = "" + step.getPlayer().getplayerNumber();
            stepElement.setAttribute("num", "" + num++);
            stepElement.setAttribute("playerId", "" + playerId);
            String coordinates = step.getCoordinateX() + " " + step.getCoordinateY();
            game.appendChild(stepElement);
            stepElement.appendChild(doc.createTextNode(coordinates));
        }
    }

    private void addPlayer(Player player, Document doc, Element elem) {
        Element playerElement = doc.createElement("Player");
        playerElement.setAttribute("id", "" + player.getplayerNumber());
        playerElement.setAttribute("name", player.getPlayerName());
        playerElement.setAttribute("symbol", "" + player.getPlayerSign());
        elem.appendChild(playerElement);
    }

    public void setWinner(Player player) {
        winner = player;
    }

}
