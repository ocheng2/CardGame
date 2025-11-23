public class Card {
    private String rank;
    private String suit;

    public Card (String rank, String suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    // i.e. 1, 2, Draw
    public String getRank() {
        return rank;
    }

    // Color
    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String toString()
    {
        return this.rank + " of " + this.suit;
    }
}
