// Deck Class by Olivia Cheng
import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] numCards)
    {
        cards = new ArrayList<Card>();

        for (int i = 0; i < suits.length; i++)
        {
            for (String rank: ranks)
            {
                cards.add(new Card(rank, suits[i]));

                // Make extra copies of a certain card depending on the number needed
                // Subtract 1 to include the card we just made
                int extra = numCards[i] - 1;

                for (int k = 0; k < extra; k++)
                {
                    cards.add(new Card(rank, suits[i]));
                }
            }
        }

        cardsLeft = cards.size();
        shuffle();
    }

    public boolean isEmpty()
    {
        return cardsLeft == 0;
    }

    public int getCardsLeft()
    {
        return cardsLeft;
    }

    // Returns the card at the end of our deck array
    public Card deal()
    {
        if (isEmpty())
        {
            return null;
        }

        // Update the number of cards in deck
        cardsLeft--;

        return cards.get(cardsLeft);
    }

    public void shuffle()
    {
        // Iterates through the deck array from the end to the beginning
        for (int i = cards.size() - 1; i >= 0; i--)
        {
            // Generates a random integer from range: 0-i
            int random = (int)(Math.random() * i);

            // Switches the current card at index i with the card at the random index
            Card placeholder = cards.get(i);
            cards.set(i, cards.get(random));
            cards.set(random, placeholder);
        }

        // Resets the values of cardsLeft to the size of deck
        cardsLeft = cards.size();
    }
}
