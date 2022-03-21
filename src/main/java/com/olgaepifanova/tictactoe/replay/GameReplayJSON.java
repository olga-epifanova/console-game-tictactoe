package com.olgaepifanova.tictactoe.replay;

import com.olgaepifanova.tictactoe.Player;
import com.olgaepifanova.tictactoe.Step;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameReplayJSON extends GameReplay {

    @Override
    public void parseFile(File jsonFile) {

        try {
            String content = FileUtils.readFileToString(jsonFile, "utf-8");
            JSONObject fileObject = new JSONObject(content);

            JSONObject gameplayObj = fileObject.getJSONObject("Gameplay");
            JSONArray playersArr = gameplayObj.getJSONArray("Player");

            List<Player> players = new ArrayList<>();
            if (playersArr.length() > 1) {
                players.add(getPlayer(playersArr.getJSONObject(0)));
                players.add(getPlayer(playersArr.getJSONObject(1)));
            }

            for (Player player : players) {
                System.out.println(String.format("Игрок №%s %s, знак %s",
                        player.getplayerNumber(), player.getPlayerName(), player.getPlayerSign()));
            }

            JSONObject gameObj = gameplayObj.getJSONObject("Game");
            JSONArray stepArr = gameObj.getJSONArray("Step");

            List<Step> steps = new ArrayList<>();
            for (int i = 0; i < stepArr.length(); i++) {
                steps.add(getStep(stepArr.getJSONObject(i), players));
            }
            showSteps(steps);

            JSONObject gameResultObj = gameplayObj.getJSONObject("GameResult");
            if (gameResultObj.isEmpty()) {
                System.out.println("Ничья!");
            } else {
                JSONObject winnerObj = gameResultObj.getJSONObject("Player");
                Player winnerPlayer = getPlayer(winnerObj);
                System.out.println(String.format("Победитель: Игрок №%s %s, знак %s",
                        winnerPlayer.getplayerNumber(), winnerPlayer.getPlayerName(), winnerPlayer.getPlayerSign()));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Player getPlayer(JSONObject obj) {
        int id = obj.getInt("id");
        String name = obj.getString("name");
        String symbol = obj.getString("symbol");
        char symbolChar = symbol.charAt(0);
        return new Player(id, name, symbolChar);
    }

    private Step getStep(JSONObject obj, List<Player> players) {
        int playerId = obj.getInt("playerId");
        Player playerStep = players.get(playerId - 1);
        int x = obj.getInt("coordinateX");
        int y = obj.getInt("coordinateY");
        return new Step(playerStep, x, y);
    }

}
