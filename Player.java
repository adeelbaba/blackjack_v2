import java.util.ArrayList;

/**
 * This represents a Player. It is composed of
 * 1- No. of chips the player holds.
 * 2- Current player hand.
 * 3- If there is a split hand on the current hand.
 * 4- List of all the hands played by the player.
 * <p/>
 * The class initializes the current hand with each new hand. Creates
 * a split hand in case the users opts for a split and keeps track
 * of the chips and hands played
 */

public class Player {

    /**
     * Holds the total number of chips available.
     * Assuming that a single chip holds a unit value.
     */
    private int chips;

    /**
     * Holds the history of hands played
     */
    private ArrayList<PlayerHand> hands;

    /**
     * Holds the current hand played by the Player
     */
    private PlayerHand currentHand;


    Player(int chips) {
        this.chips = chips;
        hands = new ArrayList<PlayerHand>();
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    /**
     * @return a new PlayerHand as current hand
     */
    public PlayerHand initializeCurrentHand() {
        currentHand = new PlayerHand();
        return currentHand;
    }

    /**
     * Once hand is finished playing, add to the list of hands
     *
     * @param hand PlayerHand that just finished playing or was evaluated
     */
    public void addHand(PlayerHand hand) {
        hands.add(hand);
    }

    /**
     * This method prints the details / summary of the hands
     * played by the player displaying total wins, losses and ties.
     * <p/>
     * Though complete hands can also be printed by calling printHand
     * in the for each loop but is not implemented here to keep things
     * simple
     */
    public void printPlayer() {
        System.out.println(" ");
        System.out.println("Summary of the Player Hands");
        System.out.println("==========================================================");
        System.out.println("Player remaining chips: " + chips);
        System.out.println("Total Hands played: " + hands.size());
        int countWins = 0;
        int countTies = 0;
        int countLosses = 0;

        for (Hand hand : hands) {
            if (hand.getResult() == 'W') {
                countWins++;
            } else if (hand.getResult() == 'L') {
                countLosses++;
            }
            if (hand.getResult() == 'T') {
                countTies++;
            }

        }
        System.out.println("Wins: " + countWins + " Losses: " + countLosses + " Ties: " + countTies);
        System.out.println("==========================================================");

    }

}
