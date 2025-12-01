import java.util.ArrayList;

public class Player {
    private final String name;
    private ArrayList<Card> hand;
    private int points;

    public Player(String name)
    {
        this.name = name;
        this.hand = new ArrayList<>();
        this.points = 0;
    }

    public Player (String name, ArrayList<Card> hand)
    {
        this.name = name;
        this.hand = new ArrayList<>(hand);
        this.points = 0;
    }

    // Getter Methods for all Instance variables
    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getPoints()
    {
        return points;
    }

    public void addPoints (int newPoint)
    {
        points += newPoint;
    }

    // Add card to Player's hand
    public void addCard (Card newCard)
    {
        hand.add(newCard);
    }

    // Remove card from Player's hand
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

        for (int i = 0; i < hand.size(); i++)
        {
            statement += hand.get(i).toString();

            // Adds a comma to the end of each card's toString
            if (i != hand.size() - 1)
            {
                statement += ",";
            }
            // Adds a period to the end of the last card in the player's hand
            else
            {
                statement += ".";
            }
        }

        return statement;
    }
}


