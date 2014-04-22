/**
 * This class represents a single complete Card which has a
 * Title, Value and Suit, for example Jack of Spades having value 10.
 * <p/>
 * Thus, a Card is composed of a CardType(value, displayName) and Suit.
 */

public class Card {
    Suit suit;
    CardType cardType;

    public Card(Suit suit, CardType cardType) {
        this.suit = suit;
        this.cardType = cardType;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    /**
     * This method is used at numerous locations where we are printing / displaying
     * a particular card while hitting or while displaying player / dealer hands.
     */
    public String toString() {
        return cardType.getDisplayName() + " of " + suit.name();
    }

    /**
     * equals is overriden to validate a possible split.
     * The condition here is to check if both cards have same Title or DisplayName i.e.
     * you can only split if you have Jack and Jack or Seven and Seven.
     * Split on cards having same values i.e. Jack and Queen are not allowed.
     * However, it can be very easily implemented by comparing the values of cards rather than
     * the displayName.
     *
     * @param obj another Card.
     *
     */

    public boolean equals(Object obj) {
        boolean equals = false;

        if (obj == this) {
            equals = true;
        } else if ((obj instanceof Card)) {
            Card other = (Card) obj;
            equals = (this.cardType.getDisplayName().equals(other.getCardType().getDisplayName()));
        }

        return equals;
    }
}
