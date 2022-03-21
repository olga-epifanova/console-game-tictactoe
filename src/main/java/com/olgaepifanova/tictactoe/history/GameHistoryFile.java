package com.olgaepifanova.tictactoe.history;

import com.olgaepifanova.tictactoe.Player;
import com.olgaepifanova.tictactoe.Step;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class GameHistoryFile {

    protected final Player firstPlayer;
    protected final Player secondPlayer;
    protected final ArrayList<Step> steps = new ArrayList<>();
    protected Player winner;
    protected final static DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy kk-mm-ss");

    public GameHistoryFile(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public abstract void createHistoryFile();
    public abstract void addStep(Step step);
    public abstract void setWinner(Player player);

}
