// Game Class by Olivia Cheng
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static final int DRAW_NUM = 7;
    private static Scanner input = new Scanner(System.in);

    private Card topCard;
    private Deck deck;
    private Player[] players;
    private int playerIndex;

    private GameView window;

    private int state;

    public static final int STATE_BEGIN = 0;
    public static final int STATE_INSTRU = 1;
    public static final int STATE_MAIN = 2;
    public static final int STATE_END = 3;


    public Game() {
        window = new GameView(this);
        resetGame(true); // true = Show instructions the very first time
    }

    // Pass a boolean to determine if we should pause for the instructions
// Pass a boolean to determine if we should pause for the instructions
    public void resetGame(boolean isFirstTime) {
        setRestartRequested(false); // Reset the flag

        if (isFirstTime) {
            // Set up with the initial Uno title view
            this.state = STATE_BEGIN;
            window.repaint();
            System.out.println("\nHit \"ENTER\" to go to the Instructions and fill out the basic information.");
            input.nextLine();

            // Set up the instructions view
            this.state = STATE_INSTRU;
            printInstructions();
            window.repaint();
        } else {
            // Keep the current visual state exactly as it is (either MAIN or END).
            // Do NOT repaint or change to STATE_INSTRU here.
            System.out.println("\n--- RESTARTING: SETTING UP NEW ROUND ---");
        }

        // Ask and store user input on number of players
        int numPlayers = 0;
        while (numPlayers < 2 || numPlayers > 4) {
            System.out.println("How many players would you like? (From 2-4)");
            try {
                numPlayers = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                // Ignore and loop again if they don't type a valid number
            }
        }

        // Create an array for each Player's name
        String[] playerNames = new String[numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            System.out.println("What is your name Player " + (i+1) + "?");
            playerNames[i] = input.nextLine();
        }

        players = new Player[numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(playerNames[i]);
        }

        // Initialize a brand new deck
        String[] ranks = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Skip", "Draw Two", "Wild", "Wild Draw"};
        String[] suits = {"Red", "Yellow", "Green", "Blue"};
        int[] numCards = {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4};
        deck = new Deck(ranks, suits, numCards);

        // Deal 7 fresh cards to each player
        for (Player player: players) {
            for (int i = 0; i < DRAW_NUM; i++) {
                player.addCard(deck.deal());
            }
        }

        topCard = deck.deal();
        playerIndex = 0;

        // NOW we switch the state back to the main game and repaint.
        // The screen will instantly update from the old game to the newly dealt game!
        state = STATE_MAIN;
        window.repaint();
    }
    // Use 'volatile' to ensure the main thread instantly sees changes made by the GUI thread
    private volatile boolean restartRequested = false;

    public void setRestartRequested(boolean restartRequested) {
        this.restartRequested = restartRequested;
    }

    public boolean isRestartRequested() {
        return restartRequested;
    }

    // Cleanly destroys the old JFrame before opening a new one
    public void closeWindow() {
        window.dispose();
    }

    public int getState() {
        return state;
    }

    public int getCurrPlayerIndex() {
        return this.playerIndex;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Card getTopCard() {
        return topCard;
    }


    public static void printInstructions()
    {
        System.out.println("Welcome to UNO!");
        System.out.println("Instructions: ");
        System.out.println("Each player's hand begins with 7 cards. " +
                "The objective of the game is to be the first player with no cards left!");
        System.out.println("Player's can put down a card from their hand, " +
                "if the card matches the number or color of the top card, or if the card is a Wild or Wild Draw.");
        System.out.println();
        System.out.println("Different Types of Cards:");
        System.out.println("Draw Two: forces the next player to add two cards to their hand");
        System.out.println("Skip: skips the turn of the next player");
        System.out.println("Wild: changes the color of the top card");
        System.out.println("Wild Draw: changes the color of the top card, adds four cards to the next player's hand," +
                " and skips the next player's turn.");
        System.out.println();
        System.out.println("Reminder:");
        System.out.println("Have Fun! And, if the program rejects your input, double check your spelling and capitalization!");
        System.out.println();
    }

    // Ends when a Player wins, asks the user if they would want to draw or place, then calling methods to execute
    public Player playGame()
    {
        // Sets up the current player
        Player currPlayer = players[playerIndex];

        // Continues the game until a player has no more cards left
        while (!currPlayer.isWon() && !isRestartRequested())
        {
            // Print the current player's name, hand, and the current topCard
            System.out.println(currPlayer.toString());
            System.out.println("The top card is currently: " + topCard.toString());

             // Check if the player's hand has a card that is valid to put down
            if (isValidPlayer(currPlayer.getHand()))
            {
                // Ask user if they would like to put a Card
                String action;
                do {
                    System.out.println("Would you like to place a card or draw? (Yes for Card/No for Draw)");
                    action = input.nextLine().toUpperCase();
                    if (isRestartRequested()) return null; // Escape immediately
                } while (!action.equals("YES") && !action.equals("NO"));

                // Depending on the user's action, the Player can put down or draw a card
                if (action.equals("YES"))
                {
                    putCard(currPlayer);
                }
                else
                {
                    drawCard(currPlayer);
                }
            }
            // If player's hand does not have a valid card that matches top card, they must draw
            else
            {
                drawCard(currPlayer);
            }

            // Given that there was user action to their hand, update the view
            window.repaint();

            // Check if the current player won
            if (currPlayer.isWon())
            {
                window.repaint();
                break;
            }

            // Move on to next player
            playerIndex = (playerIndex + 1) % players.length;

            currPlayer = players[playerIndex];
        }

        // At the end of the game, change to the final ending state
        state = STATE_END;
        window.repaint();
        return players[playerIndex];
    }

    // Checks if the player is able to put any cards down
    public boolean isValidPlayer(ArrayList<Card> hand)
    {
        for (Card card: hand)
        {
            if (card.getRank().equals(topCard.getRank())
                    || card.getSuit().equals(topCard.getSuit())
                    || card.getRank().equals("Wild")
                    || card.getRank().equals("Wild Draw"))
            {
                return true;
            }
        }
        return false;
    }

    // Draws a card for the Player, and if playable, asks if they would like to put it down
    public void drawCard(Player player)
    {
        // Draw a card
        Card newCard = deck.deal();
        player.addCard(newCard);

        // Print the card suit and rank
        System.out.println("Draw a card:" + newCard.toString());

        // Check if the drawn card is playable
        if (isValidCard(newCard))
        {
            // Ask the user if they want to play their newly drawn card
            String action;
            do {
                System.out.println("Would you like to place your newly drawn card down? (Yes/No)");
                action = input.nextLine().toUpperCase();
                if (isRestartRequested()) return; // Escape immediately
            } while (!action.equals("YES") && !action.equals("NO"));
            // Player puts the new drawn card down; update the topCard and their hand
            if (action.equals("YES"))
            {
                cases(newCard, player);
            }
        }
    }

    // Asks the user for a valid Card to place down, and calls the next function to execute the rank of the card
    public void putCard(Player player) {
        Card chosenCard = null;
        int index = -1;

        do {
            System.out.println("What card would you like to place? (enter index)");
            String line = input.nextLine();
            if (isRestartRequested()) return; // Escape immediately

            try {
                index = Integer.parseInt(line) - 1;
                if (index >= 0 && index < player.getHand().size()){
                    chosenCard = player.getHand().get(index);
                }
            } catch (NumberFormatException e) {
                index = -1; // Invalid input
            }
        } while (chosenCard == null || !isValidCard(chosenCard) || index < 0 || index >= player.getHand().size());

        cases(chosenCard, player);
    }

    // Checks if the card fits the criteria to be put down
    public boolean isValidCard(Card card)
    {
        return card.getRank().equals(topCard.getRank())
                || card.getSuit().equals(topCard.getSuit())
                || card.getRank().equals("Wild")
                || card.getRank().equals("Wild Draw");
    }

    // Depending on the rank of the card, certain methods will be called
    public void cases(Card chosenCard, Player player)
    {
        switch (chosenCard.getRank()) {
            case "Wild" ->
            {
                wild();
            }
            case "Wild Draw" ->
            {
                wild();
                extraCards(4);
                skip(player);
            }
            case "Skip" ->
            {
                skip(player);
            }
            case "Draw Two" ->
            {
                extraCards(2);
            }
            default -> {
                topCard.setSuit(chosenCard.getSuit());
            }
        }
        topCard.setRank(chosenCard.getRank());
        player.removeCard(chosenCard);
    }

    // Resets the color of the top card based on user input
    public void wild() {
        String inputColor;
        do {
            System.out.println("What color do you want the top card to be? (Red/Yellow/Green/Blue)");
            inputColor = input.nextLine();
            if (isRestartRequested()) return; // Escape immediately
        } while (!inputColor.equals("Red") && !inputColor.equals("Yellow") && !inputColor.equals("Green") && !inputColor.equals("Blue"));

        topCard.setSuit(inputColor);
    }

    // Adds extra cards to the next player's hand
    public void extraCards(int numCards)
    {
        int indexNext = (playerIndex + 1) % players.length;

        System.out.println(players[indexNext].getName() + " must draw " + numCards + " cards!");
        System.out.println("The cards are: ");

        for (int i = 0; i < numCards; i++)
        {
            Card newCard = deck.deal();
            players[indexNext].addCard(newCard);
            System.out.println(newCard.toString() + ",");
        }
    }

    // Skips the turn of the next player
    public void skip(Player player)
    {
        int indexNext = (playerIndex + 1) % players.length;

        System.out.println(player.getName() + " is skipping " + players[indexNext].getName() + "'s turn!");
        playerIndex++;
    }

    public static void main(String[] args) {
        Game uno = new Game(); // This automatically calls resetGame(true) in the constructor

        while (true) {
            Player winner = uno.playGame();

            // If winner is null, it means the player clicked Restart mid-game
            if (uno.isRestartRequested()) {
                uno.resetGame(false); // Skip instructions!
                continue;
            }

            System.out.println(winner.getName() + " is the winner of UNO!");
            System.out.println("Click the 'RESTART' button in the game window to play again.");

            // Wait until the user clicks the Restart button on the end screen
            while (!uno.isRestartRequested()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            uno.resetGame(false);  // Skip instructions!
        }
    }
}