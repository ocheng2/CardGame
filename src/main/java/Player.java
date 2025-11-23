import java.util.ArrayList;

public class Player {
    private final String name;
    private ArrayList<Card> hand;

    public Player(String name)
    {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public Player (String name, ArrayList<Card> hand)
    {
        this.name = name;
        this.hand = new ArrayList<>(hand);
    }

    // Getter Methods for all Instance variables
    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    // DON'T NEED
//    public void addPoints (int newPoint)
//    {
//        points += newPoint;
//    }

    // Add card to Player's hand
    public void addCard (Card newCard)
    {
        hand.add(newCard);
    }

    public void removeCard(Card newCard)
    {
        hand.remove(newCard);
    }

    // Player wins if there are no more cards left in their hand
    public boolean isWon()
    {
        return hand.isEmpty();
    }

    public String toString()
    {
        String statement = "It is " + this.name + "'s turn. Here are "
                + this.name + "'s cards: ";

        for (Card card: hand)
        {
            statement += card.toString() + ", ";
        }
        return statement;
    }
}


