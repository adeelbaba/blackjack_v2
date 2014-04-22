/**
 *
 * This Enum represents the 13 cards in any deck.
 * It also assigns each CardType a value based on BlackJack
 * which is used while calculating the value of any BlackJack
 * hand. The value and displayName are also used when a
 * player or a dealer hand is printed out.
 * and a displayName which are used while calculating the
 * value of a particular hand and display
 *
 */

public enum CardType {

    ACE(11, "ACE"),
    KING(10, "KING"),
    QUEEN(10, "QUEEN"),
    JACK(10, "JACK"),
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10");

    private int value;
    private String displayName;

    private CardType(int value, String displayName){
        this.value = value;
        this.displayName = displayName;
    }

    public int getValue(){
        return this.value;
    }

    public String getDisplayName(){
        return this.displayName;
    }


}
