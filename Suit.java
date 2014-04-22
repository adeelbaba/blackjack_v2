/**
 *
 * This enum represents the four different suits namely
 * Spades, Clubs, Hearts and Diamonds.
 *
 * The symbol represents the Unicode for the Suit. It is
 * not used anywhere since most terminals and command line
 * don't support Unicode. However, the name of the Suit can
 * very easily be replaced with Unicode using the symbol where
 * we are printing a Card's value, display name and suit.*
 *
 */

public enum Suit {

    SPADES("\u2665"),
    CLUBS("\u2666"),
    HEARTS("\u2660"),
    DIAMONDS("\u2663");

    private String symbol;

    private Suit(String symbol){
        this.symbol = symbol;
    }
    public String getSymbol(){
        return this.symbol;
    }
}
