
/**
 * DealerHand represents the Hand of a dealer and inherits from
 * the Hand class. The class is constructed to differentiate
 * displaying a dealer's hand and his / her face up card.
 */

public class DealerHand extends Hand {


    public DealerHand() {
        super();
    }

    /**
     * Print dealer's face up card while the player continues to play.
     */
    public void printFaceUpCard() {
        System.out.println("=====================");
        System.out.println("| Dealer Hand       |");
        System.out.println("=====================");

        System.out.println("Dealer Face Up Card - " + cards.get(0));
    }


    /**
     * Print dealer's complete hand including cards in a hand and
     * the value of the hand.
     * <p/>
     * It also prints result whether dealer won or lost if the hand
     * has been evaluated.
     */
    @Override
    public void printHand() {
        System.out.println("====================================");
        System.out.println("| Dealer Hand                      |");
        System.out.println("====================================");

        StringBuilder buff = new StringBuilder();
        for (Card c : cards) {
            buff.append(c.toString());
            buff.append(": ");
        }
        buff.append(" Hand Value: ");
        buff.append(handValue);

        if (result != 'N') {
            buff.append(" : Result: ");
            buff.append(result == 1 ? "W" : "L");
        }

        System.out.println(buff.toString());
    }
}
