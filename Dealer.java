import java.util.ArrayList;

/**
 * The main purpose of this class is to return a new DealerHand
 * whenever a new hand is initialized or played.
 * <p/>
 * The class consists of a current dealer hand and also holds
 * the list of all hands played by the dealer.
 * <p/>
 * Though list of hands is not being used anywhere for the time
 * being but can provide information as to how the dealer performed.
 */

public class Dealer {

    /**
     * List of hands played by the dealer. Updated once
     * a hand is evaluated.
     */
    private ArrayList<DealerHand> hands;

    /**
     * Current Hand of the Dealer
     */
    private DealerHand currentHand;

    Dealer() {
        hands = new ArrayList<DealerHand>();
    }

    /**
     * This method always sets or initializes the currentHand
     * and returns.
     *
     * @return currentHand
     */
    public DealerHand initializeCurrentHand() {
        currentHand = new DealerHand();
        return currentHand;
    }

    /**
     * Once a hand in the game is evaluated. Add dealer's
     * to the list.
     *
     * @param hand
     */
    public void addHand(DealerHand hand) {
        hands.add(hand);
    }
}

