import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int points;

    public Player(String name)
    {
        this.name = name;
        points = 0;
    }

    public Player (String name, ArrayList<Card> hand)
    {
        this.name = name;
        this.hand = hand;
        points = 0;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints (int newPoint)
    {
        points += newPoint;
    }

    public void addCard (Card newCard)
    {
        hand.add(newCard);
    }

    public String toString()
    {
        String statement = this.name + " has " + this.points + " points\n"
                + this.name + "'s cards: ";

        for (Card card: hand)
        {
            statement += card.toString() + ", ";
        }
        return statement;
    }
}


