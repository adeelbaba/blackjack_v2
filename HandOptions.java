/**
 *
 * This Enum represents the simple three options that a player
 * can opt for in this version of BlackJack. The options are Hit, Stand and Split.
 *
 * It is used to compare User's inputs while giving them the options for the hand.
 *
 */


public enum HandOptions {
    HIT(1),
    STAND(2),
    SPLIT(3);

    private int value;

    private HandOptions(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }


}



