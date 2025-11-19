import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] values)
    {
        for (int i = 0; i < ranks.length; i++)
        {
            cards.add(new Card(ranks[i], suits[i], values[i]));
        }

        cardsLeft = cards.size();
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
