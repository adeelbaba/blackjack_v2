import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * This class represents the BlackJack game. It performs all the game operations
 * and hold all the instances required to play the game i.e.
 * 1- Shuffled Deck
 * 2- Player
 * 3- Dealer
 * 4- Player Hand
 * 5- Dealer Hand
 * <p/>
 * This class creates a deck of cards, shuffles it, gives user the options to
 * place the bet and play (hit, stand, split) and takes appropriate actions.
 * Moreover, it evaluates the hand as well.
 * <p/>
 * Note that:
 * <p/>
 * 1- Only a single deck of cards is being used though it can easily be modified
 * to use more than one deck. If the available card count falls
 * below 10, we take the cards dealt in older hands, shuffle them and add to the deck.
 * <p/>
 * 2- Only following game features are implemented:
 * <p/>
 * - Single Player
 * - Hit
 * - Stand &
 * - Split (Single Level): The structure used here can easily be extended to allow
 * multiple level splitting though there would be some changes required.
 */

public class BlackJack {
    /**
     * Holds the shuffled cards and user deals the cards through this list.
     */
    private ArrayList<Card> deck;

    /**
     * Holds the card that were dealt to players in the previous (not current)
     * hands.
     */
    private ArrayList<Card> dealtDeck;

    /**
     * Instance of player
     */
    private Player player;

    /**
     * Instance of dealer
     */
    private Dealer dealer;

    /**
     * Player Hand which is the same as Player class's current hand
     * On a given all the operations will be performed on this object.
     * <p/>
     * It is used as a member here to avoid getting from Player again and again.
     */
    private PlayerHand playerHand;

    /**
     * Dealer Hand
     */
    private DealerHand dealerHand;

    /**
     * Scanner to scan user input
     */
    private Scanner scan = new Scanner(System.in);


    /**
     * Initialize all the members. A few things to keep in mind
     * - Player's chips count is to 100
     * - playerHand is player's current hand.
     * - dealerHand is dealer's current hand.
     */
    BlackJack() {
        deck = new ArrayList<Card>();
        dealtDeck = new ArrayList<Card>();
        player = new Player(100);
        dealer = new Dealer();
        playerHand = player.initializeCurrentHand();
        dealerHand = dealer.initializeCurrentHand();

    }

    /**
     * Welcome message for the player
     */
    void printWelcomeMessage() {
        System.out.println("================================================");
        System.out.println("| Welcome to BlackJack!                        |");
        System.out.println("================================================");
    }


    /**
     * This is the entry method and is called from the main class (Game)
     * This method does the following tasks:
     * <p/>
     * 1- Prints Welcome Message
     * 2- Creates and Gets shuffled deck
     * 3- Asks the user to place the bet & deal cards
     * 4- Plays player hand
     * 5- Plays Dealer Hand
     * 6- Evaluates the hand
     * 7- Asks the player if they want to continue
     */

    void startGame() {
        Boolean isGame = true;
        printWelcomeMessage();
        getShuffledDeck();

        // Check if the chip count is valid and if the player wants to keep on playing
        while (player.getChips() > 0 && isGame) {
            placeBet();
            distributeCards();

            // If the player hits BlackJack, no need to play Hit, Stand
            if (!playerHand.isBlackJack()) {
                playHand(playerHand);

                // Call dealer to play hits only if player stood current hand or player stood split hand
                // If the player busted or had blackjack, dealer doesn't need to take hits
                if (playerHand.isStand() || (playerHand.isSplit() && playerHand.getSplitHand().isStand())) {
                    performDealerHits();
                }
            }

            // Evaluate Hands
            // If there was a split, evaluate both curent and split hands
            if (playerHand.isSplit()) {
                System.out.println("\nEvaluating Split Hand # 1 \n");
                evaluateHand(playerHand);
                System.out.println("\nEvaluating Split Hand # 2 \n");
                evaluateHand(playerHand.getSplitHand());
            } else {
                // Only evaluate current hand since there was no split
                evaluateHand(playerHand);
            }

            // Dump the hand records to player's hand list
            // Dump dealt cards to dealt deck
            // Reinitialize the player and dealer hands
            resetHand();

            System.out.print("\nDo you like to play a new Hand? (Y / N)? ");
            isGame = scan.next().trim().equalsIgnoreCase("Y");

            // Check if deck has enough cards left
            // Add more cards if needed
            resetDeck();
        }

        if (player.getChips() <= 0 || !isGame) {
            exitGame();
        }
    }

    void exitGame() {
        if (player.getChips() <= 0) {
            System.out.println("Sorry! you are out of chips.");
        }
        System.out.println("Exiting the game!");
        player.printPlayer();
    }

    /**
     * Ask the player to place the bet and check if the input is valid.
     */
    void placeBet() {
        System.out.println("=========================================================================================");

        System.out.print(" You have " + player.getChips() + " chips available \n");
        System.out.print(" Please place your bet (No. of Chips): ");

        while (!scan.hasNextInt()) {
            System.out.print(" Invalid Bet. Please place your bet: ");
            scan.next();
        }

        int bet = scan.nextInt();


        while (bet <= 0 || bet > player.getChips()) {
            System.out.print("\n Invalid Bet Amount! Please place more than 0 and less than " + player.getChips() + " chips: ");
            while (!scan.hasNextInt()) {
                System.out.print("\n Invalid Bet Amount! Please place more than 0 and less than " + player.getChips() + " chips: ");
                scan.next();
            }
            bet = scan.nextInt();
        }

        playerHand.setBet(bet);
        player.setChips(player.getChips() - bet);
        System.out.println(" Your Bet is: " + bet + " Chips & you have " + player.getChips() + " Chips remaining ");
        System.out.println("=========================================================================================");
    }


    /**
     * Deal cards one by one. First to user then to dealer himself
     * <p/>
     * Print Dealer's face up card and player's complete hand
     */
    void distributeCards() {

        playerHand.addCard(deck.remove(0));
        dealerHand.addCard(deck.remove(0));
        playerHand.addCard(deck.remove(0));
        dealerHand.addCard(deck.remove(0));

        dealerHand.printFaceUpCard();
        playerHand.printHand();
    }


    /**
     * Give player the options to play the hand i.e. either
     * Hit, Stand or Split
     *
     * @param hand Player hand that is effective, could be split hand or the first hand
     * @return Player's selection
     */
    int giveHandOptions(PlayerHand hand) {
        System.out.println("=====================");
        System.out.println("| Hand Options      |");
        System.out.println("=====================");
        System.out.println("| Press 1 to Hit    |");
        System.out.println("| Press 2 to Stand  |");
        System.out.println("| Press 3 to Split  |");
        System.out.println("=====================");
        System.out.print("Your Option: ");


        while (!scan.hasNextInt()) {
            System.out.print("\nInvalid option selection. Please input (1) to Hit, (2) to Stand and (3) to Split: ");
            scan.next();
        }

        int input = scan.nextInt();

        while (input != HandOptions.HIT.getValue() && input != HandOptions.STAND.getValue() && input != HandOptions.SPLIT.getValue()) {
            System.out.print("\nInvalid option selection. Please input (1) to Hit, (2) to Stand and (3) to Split: ");
            while (!scan.hasNextInt()) {
                scan.next();
            }
            input = scan.nextInt();
        }

        while (input == HandOptions.SPLIT.getValue() && !hand.canSplit()
                || (input == HandOptions.SPLIT.getValue() && hand.canSplit() && player.getChips() < hand.getBet())) {
            if (player.getChips() < hand.getBet()) {
                System.out.println("You don't have enough chips to match the bet.");
            }
            System.out.print("You can't split! Please input (1) to Hit, (2) to Stand: ");
            while (!scan.hasNextInt()) {
                scan.next();
            }
            input = scan.nextInt();
            System.out.println(" ");

        }

        return input;

    }

    /**
     * Based on the player's input, take appropriate actions.
     *
     * @param hand whichever hand is playing
     */
    void playHand(PlayerHand hand) {

        // Give player options
        int input = giveHandOptions(hand);

        // If player wanted to hit
        // Remove card from deck and call playerHand.hit
        while (input == HandOptions.HIT.getValue()) {
            Card newCard = deck.remove(0);
            hand.hit(newCard);

            // Player will be given options to hit or stand even if
            // their handValue is sitting at 21
            if (!hand.isBusted()) {
                input = giveHandOptions(hand);
            } else {
                break;
            }

        }

        // Call player's stand
        if (input == HandOptions.STAND.getValue()) {
            hand.stand();
        }

        // Call split method, show both the hands and initiate
        // play for both hands
        if (input == HandOptions.SPLIT.getValue()) {
            if (hand.canSplit()) {
                split();

                System.out.println("==== Hand 1 ====");
                hand.printHand();
                System.out.println("==== Hand 2 ====");
                hand.getSplitHand().printHand();

                if (!hand.isBlackJack()) {
                    System.out.println("==== Playing Split Hand # 1 ====");
                    hand.printHand();
                    playHand(hand);
                }

                if (!hand.getSplitHand().isBlackJack()) {
                    System.out.println("==== Playing Split Hand # 2 ====");
                    hand.getSplitHand().printHand();
                    playHand(hand.getSplitHand());
                }
            }
        }

    }

    /**
     * If the player opted for split then initialize the split hand,
     * distribute one of first hand's card, set the bet value, update
     * player chips and deal two cards one to each hand from the deck
     */
    public void split() {
        if (!playerHand.canSplit()) {
            return;
        }

        player.setChips(player.getChips() - playerHand.getBet());

        // Initialize splitHand with bet equal to parent hand and
        // split boolean set to true to prevent further splitting
        PlayerHand splitHand = new PlayerHand(playerHand.getBet(), true);
        splitHand.addCard(playerHand.removeCard(1));

        // draw cards from the deck for each hand 
        playerHand.addCard(deck.remove(0));
        splitHand.addCard(deck.remove(0));

        playerHand.setSplitHand(splitHand);
        playerHand.setSplit(true);
    }

    /**
     * Dealer should take the hit as long as they don't 17
     */
    private void performDealerHits() {
        dealerHand.printHand();
        while (dealerHand.getHandValue() < 17) {
            System.out.println("Dealer Hits!");
            dealerHand.hit(deck.remove(0));
        }
    }

    /**
     * Evaluate who won the hand
     * whether it was a blackjack, tie, bust or comparison of hand value
     *
     * @param hand the player hand that needs to be evaluated
     */
    void evaluateHand(PlayerHand hand) {
        char handResult = 'N';

        if (hand.isBlackJack() && dealerHand.isBlackJack()) {
            System.out.println("Player & Dealer BlackJack. It is a Push!");
            handResult = 'T';
        } else if (hand.isBlackJack()) {
            System.out.println("Player BlackJack. Player wins hand!");
            handResult = 'W';
        } else if (hand.getHandValue() > 21) {
            System.out.println("Player Busted, Dealer wins hand!");
            handResult = 'L';
        } else if (dealerHand.getHandValue() > 21) {
            System.out.println("Dealer Busted, Player wins hand!");
            handResult = 'W';
        } else if (hand.getHandValue() > dealerHand.getHandValue() && hand.getHandValue() <= 21) {
            System.out.println("Player wins hand!");
            handResult = 'W';
        } else if (dealerHand.getHandValue() > hand.getHandValue() && dealerHand.getHandValue() <= 21) {
            System.out.println("Dealer wins hand!");
            handResult = 'L';
        } else if (hand.getHandValue() == dealerHand.getHandValue()) {
            System.out.println("We have reached a PUSH. Nobody wins!");
            handResult = 'T';
        }

        if (handResult == 'W') {
            player.setChips(player.getChips() + hand.getBet() + hand.getBet());
            hand.setResult(handResult);
            dealerHand.setResult('L');
        } else if (handResult == 'T') {
            player.setChips(player.getChips() + hand.getBet());
            hand.setResult(handResult);
            dealerHand.setResult(handResult);
        } else if (handResult == 'L') {
            hand.setResult(handResult);
            dealerHand.setResult('W');
        }

    }

    /**
     * Create a new deck and shuffle the cards
     */
    public void getShuffledDeck() {
        deck.addAll(getNewDeck());
        Collections.shuffle(deck);
    }

    /**
     * Create a new deck
     *
     * @return list of cards
     */
    ArrayList<Card> getNewDeck() {
        ArrayList<Card> d = new ArrayList<Card>();

        // Initialize Spade cards and add to deck
        for (Suit suit : Suit.values()) {
            for (CardType cardType : CardType.values()) {
                Card card = new Card(suit, cardType);
                d.add(card);
            }
        }
        return d;
    }

    /**
     * Move the cards in hand to history lists.
     * Reinitialize player hand, player split hand and
     * dealer hand
     */
    void resetHand() {
        player.addHand(playerHand);
        if (playerHand.getSplitHand() != null) {
            player.addHand(playerHand.getSplitHand());
        }

        dealer.addHand(dealerHand);
        // Move the cards to Dealt Deck Shoe
        dealtDeck.addAll(playerHand.getCards());
        if (playerHand.isSplit())
            dealtDeck.addAll(playerHand.getSplitHand().getCards());
        dealtDeck.addAll(dealerHand.getCards());
        // Reset the player and dealer hands
        playerHand = player.initializeCurrentHand();
        dealerHand = dealer.initializeCurrentHand();
    }

    /**
     * Move cards from dealtDeck to deck since deck
     * has 10 or less cards left
     * <p/>
     * The value 10 is arbitrary and can be changed with any other value
     */
    void resetDeck() {
        if (deck.size() <= 10) {
            System.out.println("============================================================");
            System.out.println(" Deck is almost empty. Adding more cards to the dealer shoe.");
            System.out.println("============================================================");
            Collections.shuffle(dealtDeck);
            deck.addAll(dealtDeck);
            dealtDeck = new ArrayList<Card>();
        }
    }

}

