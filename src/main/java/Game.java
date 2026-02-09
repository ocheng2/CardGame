// Game Class by Olivia Cheng
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static final int DRAW_NUM = 7;
    private static Scanner input = new Scanner(System.in);

    private Card topCard;
    private Deck deck;
    private Player[] players;
    private int playerIndex;

    public Game() {
        // Ask and store user input on number of players
        System.out.println("How many players would you like?");
        int numPlayers = input.nextInt();
        input.nextLine();

        // Create an array for each Player's name
        String[] playerNames = new String[numPlayers];

        // Asks for each Player's name and store in array names
        for (int i = 0; i < numPlayers; i++)
        {
            System.out.println("What is your name " + "Player " + (i+1) + "?");
            playerNames[i] = input.nextLine();
        }

        players = new Player[numPlayers];

        // Creates instances of Player Class and adds it into the players array
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(playerNames[i]);
        }

        // Calls to the Deck class to initialize an instance of a deck
        String[] ranks = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "Skip", "Draw Two", "Wild", "Wild Draw"};
        String[] suits = {"Red", "Yellow", "Green", "Blue"};
        int[] numCards = {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4};

        deck = new Deck(ranks, suits, numCards);

        // Gives each player 7 cards for their hand
        for (Player player: players)
        {
            for (int i = 0; i < DRAW_NUM; i++)
            {
                player.addCard(deck.deal());
            }
        }

        topCard = deck.deal();
        playerIndex = 0;
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
        while (!currPlayer.isWon())
        {
            // Print the current player's name, hand, and the current topCard
             System.out.println(currPlayer.toString());
             System.out.println("The top card is currently: " + topCard.toString());

             // Check if the player's hand has a card that is valid to put down
            if (isValidPlayer(currPlayer.getHand()))
            {
                // Ask user if they would like to put a Card
                String action;
                do
                {
                    System.out.println("Would you like to place a card or draw? (Yes for Card/No for Draw)");
                    action = input.nextLine().toUpperCase();
                }
                while (!action.equals("YES") && !action.equals("NO"));

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

            // Check if the current player won
            if (currPlayer.isWon())
            {
                break;
            }

            // Move on to next player
            playerIndex = (playerIndex + 1) % players.length;

            currPlayer = players[playerIndex];
        }
        // Returns the Player that won
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
            do
            {
                System.out.println("Would you like to place your newly drawn card down? (Yes/No)");
                action = input.nextLine().toUpperCase();
            }
            while (!action.equals("YES") && !action.equals("NO"));

            // Player puts the new drawn card down; update the topCard and their hand
            if (action.equals("YES"))
            {
                cases(newCard, player);
            }
        }
    }

    // Asks the user for a valid Card to place down, and calls the next function to execute the rank of the card
    public void putCard(Player player)
    {
        // Asks the player the card they would like to place
        Card chosenCard = null;

        do
        {
            System.out.println("What card would you like to place? (Rank of Suit)");
            String inputCard = input.nextLine();
            chosenCard = getCardFromHand(player, inputCard);
        }
        while (chosenCard == null || !isValidCard(chosenCard));

        cases(chosenCard, player);
    }

    // Checks if the card Player choose to put down is apart of their hand and returns the Card
    public Card getCardFromHand(Player player, String selectedCard)
    {
        for (Card card: player.getHand())
        {
            if (card.toString().equals(selectedCard))
            {
                return card;
            }
        }
        return null;
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
    public void wild()
    {
        String inputColor;
        do
        {
            System.out.println("What color do you want the top card to be? (Red/Yellow/Green/Blue)");
            inputColor = input.nextLine();
        }
        while (!inputColor.equals("Red") && !inputColor.equals("Yellow") && !inputColor.equals("Green") && !inputColor.equals("Blue"));

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
        printInstructions();

        // Creates an instance of the Game class
        Game uno = new Game();

        Player winner = uno.playGame();

        System.out.println(winner.getName() + " is the winner of UNO!");
    }
}