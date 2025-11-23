import java.util.ArrayList;

public class Deck {
    private final ArrayList<Card> cards;
    private int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] numCards)
    {
        cards = new ArrayList<Card>();

        for (int i = 0; i < suits.length; i++)
        {
            for (int j = 0; j < ranks.length; j++)
            {
                cards.add(new Card(ranks[j], suits[i]));

                // Add extra copies depending on number of Cards needed
                // Subtract 1 to count the card we just made
                int extra = numCards[i] - 1;

                for (int k = 0; k < extra; k++)
                {
                    cards.add(new Card(ranks[j], suits[i]));
                }
            }
        }

        cardsLeft = cards.size();
        shuffle();
    }

    public boolean isEmpty()
    {
        if (cardsLeft == 0)
        {
            return true;
        }
        return false;
    }

    public int getCardsLeft()
    {
        return cardsLeft;
    }

    public Card deal()
    {
        if (isEmpty())
        {
            return null;
        }

        cardsLeft--;
        return cards.get(cardsLeft);
    }

    public void shuffle()
    {
        for (int i = cards.size() - 1; i >= 0; i--)
        {
            int random = (int)(Math.random() * i);
            Card placeholder = cards.get(i);

            cards.set(i, cards.get(random));
            cards.set(random, placeholder);
        }
        cardsLeft = cards.size();
    }
}
