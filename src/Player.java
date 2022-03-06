public class Player {

    private final String playerName;
    private final char playerSign;

    public String getPlayerName() {
        return playerName;
    }

    public char getPlayerSign() {
        return playerSign;
    }

    public Player(String name, char sign) {
        this.playerName = name;
        this.playerSign = sign;
    }

}