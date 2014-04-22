import java.util.ArrayList;

/**
 * This is a generic Hand class which represents a single hand in a BlackJack game.
 * It is meant to be sub-classed. See PlayerHand and DealerHand.
 * <p/>
 * This class provides basic data members and provides generic implementation for
 * hitting, adjusting aces value based on the hand and if there is a blackjack
 * at a hand.
 */

public abstract class Hand {

    /**
     * Hold the cards that are dealt in a hand
     */
    protected ArrayList<Card> cards;

    /**
     * Evaluate and store the value of the hand
     */
    int handValue;

    /**
     * Store the result if the hand was evaluated.
     */
    char result;

    /**
     * Hold aces count that are being counted as 11. This count will
     * be used to adjust the value of aces as required i.e. if an ace
     * should be evaluated as 1 instead of 11.
     */
    int aces;

    Boolean isBlackJack = false;

    public Hand() {
        cards = new ArrayList<Card>();
        handValue = 0;
        result = 'N';
        aces = 0;
    }

    public void stand() {
    }

    /**
     * Add the card from deck to the hand. Compute the new handValue
     * and aces if needed
     *
     * @param newCard taken from the deck
     */
    public int hit(Card newCard) {
        System.out.println("\nNew Card " + newCard);
        addCard(newCard);
        printHand();
        if (handValue > 21) {
            return -1;
        }
        return 1;
    }

    public void addCard(Card newCard) {
        cards.add(newCard);
        setHandValue();
    }

    /**
     * This method is called in case of a split where a card is moved from main hand
     * to a split hand.
     *
     * @param index location from which to remove the card
     * @return fetched Card
     */
    public Card removeCard(int index) {
        Card card = cards.remove(index);
        setHandValue();
        return card;
    }

    /**
     * Compute the new handValue. For simplicity initiate handValue and aces to 0.
     * Also calls validateBlackJack which checks if we have a BlackJack
     * and adjustAce to decide which value of an ace to use i.e. 11 or 1
     */
    public void setHandValue() {
        handValue = 0;
        aces = 0;

        for (Card c : cards) {
            handValue += c.getCardType().getValue();
            if (c.getCardType() == CardType.ACE) {
                aces++;
            }
        }
        validateBlackJack();
        adjustAce();

    }

    /**
     * Updates the handValue and count of aces.
     * <p/>
     * If the sum of the handValue is < 21 then an ace will always be
     * counted as 11. If the count exceeds 21 and there is an ace in the hand
     * then we need to update the handValue and the count of aces.
     */
    void adjustAce() {
        if (handValue > 21 && aces > 0) {
            for (int i = 0; i < aces; i++) {
                handValue -= 10;
                if (handValue < 21) break;

            }
        }
    }

    /**
     * Check if there is a BlackJack i.e. only two cards are dealt and handValue is 21.
     * This is relevant in the case of a player. For instance, if a player doesn't have a
     * BlackJack he / she will continue to hit or hand even if dealer has a BlackJack.
     * <p/>
     * But if a player has a BlackJack then dealer BlackJack is also validated.
     */
    void validateBlackJack() {
        isBlackJack = cards.size() == 2 && handValue == 21;
    }

    Boolean isBlackJack() {
        return isBlackJack;
    }

    public Boolean isBusted() {
        return handValue > 21;
    }

    public void printHand() {
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getHandValue() {
        return handValue;
    }

    public void setHandValue(int handValue) {
        this.handValue = handValue;
    }

    public char getResult() {
        return result;
    }

    public void setResult(char result) {
        this.result = result;
    }
}




