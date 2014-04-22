
/**
 * This is a subclass of abstract Hand class. This will represent
 * a player's hand. It will also hold the amount of bet that a player places
 * and implements methods to handle a player stand or split decisions.
 * <p/>
 * Moreover, it overrides the printHand method to print / show a player's
 * cards at a given instance.
 * <p/>
 * In this version of BlackJack, PlayerHand contains the splitHand
 * which would make it easier to implement or extend further splitting
 * (more than once) though
 * it is not allowed.
 */

public class PlayerHand extends Hand {
    /**
     * Represents player's bet in a hand
     */
    private int bet;

    /**
     * A Boolean value when a player has already made a split. This variable will be
     * true to prevent further splits i.e. the implementation only allows for
     * single level split
     */
    private Boolean split = false;

    /**
     * A Boolean to check if a player has made a split. This boolean is used to check if
     * it is time for the Dealer to start playing his hand
     */
    private Boolean stand = false;

    /**
     * In case a split is played, this member holds the split
     * hand
     */

    private PlayerHand splitHand;


    public PlayerHand() {
        super();
        bet = 0;
    }

    /**
     * This constructor is called by the split player hand to
     * initialize itself with the amount bet and boolean split
     * as true to prevent further splits.
     *
     * @param bet
     * @param split
     */
    public PlayerHand(int bet, boolean split) {
        super();
        this.bet = bet;
        this.split = split;
    }

    /**
     * Player will have the option to select hit, stand
     * if his handValue is 21
     */
    @Override
    public void stand() {
        stand = true;
        System.out.println("Player decided to stand.");
    }

    public Boolean isStand() {
        return stand;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public PlayerHand getSplitHand() {
        if (splitHand != null) {
            return splitHand;
        }
        return splitHand;
    }

    /**
     * @param splitHand
     */
    public void setSplitHand(PlayerHand splitHand) {
        this.splitHand = splitHand;
    }

    public void setSplit(Boolean split) {
        this.split = split;
    }

    /**
     * This method checks if the conditions are met to make a split i.e.
     * 1- A split has not already been made
     * 2- The player is dealt only two cards and both cards are equal (having
     * same card type i.e. Jack and a Jack etc.
     *
     * @return true if a split is a legal action, false otherwise.
     */
    public Boolean canSplit() {
        if (!split && cards.size() == 2 && cards.get(0).equals(cards.get(1)))
            return true;
        return false;
    }

    public Boolean isSplit() {
        return split;
    }

    /**
     * Print a player's hand. Added headings so that it is clear that a player hand is
     * being printed.
     * <p/>
     * Prints player's cards, total hand value and the bet placed on this hand.
     * <p/>
     * Also prints result of the hand once the hand has been evaluated.
     */
    @Override
    public void printHand() {
        System.out.println("=====================");
        System.out.println("| Player Hand       |");
        System.out.println("=====================");

        StringBuilder buff = new StringBuilder();
        buff.append("BET: ").append(bet).append(" \nCards ");
        for (Card c : cards) {
            buff.append(": ");
            buff.append(c.toString());
        }
        buff.append("\nHand Value: ");
        buff.append(handValue);

        if (result != 'N') {
            buff.append(" : Result: ");
            buff.append(result);
        }

        System.out.println(buff.toString());
    }
}
