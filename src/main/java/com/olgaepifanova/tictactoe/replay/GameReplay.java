package com.olgaepifanova.tictactoe.replay;

import com.olgaepifanova.tictactoe.GameField;
import com.olgaepifanova.tictactoe.Step;

import java.io.File;
import java.util.List;

public abstract class GameReplay  {

    public abstract void parseFile(File file);

    protected void showSteps(List<Step> steps) {
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

}
