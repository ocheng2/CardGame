// Card Class by Olivia Cheng
public class Card {
    private String rank;
    private String suit;
    private int value;

    public Card (String rank, String suit)
    {
        this.rank = rank;
        this.suit = suit;
        this.value = 0;
    }

    // Getter and setter methods of rank and suit
    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getValue() { return value; }

    public void setValue(int value) { this.suit = suit; }

    public String toString()
    {
        return this.rank + " of " + this.suit;
    }
}
