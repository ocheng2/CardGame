import javax.swing.*;
import java.awt.*;

// Card Class by Olivia Cheng
public class Card {
    private String rank;
    private String suit;
    private int value;
    private int x;
    private int y;

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

    public Color getColor() {
        if (rank.equals("Wild") || rank.equals("Wild Draw")) {
            return Color.BLACK;
        } else if(suit.equals("Red")) {
            return Color.RED;
        } else if (suit.equals("Yellow")) {
            return Color.YELLOW;
        } else if (suit.equals("Green")) {
            return Color.GREEN;
        } else {
            return Color.BLUE;
        }
    }

    public void draw(Graphics g, int x, int y) {
        // Draw the card
        g.setColor(getColor());
        g.fillRoundRect(x, y, 60, 90, 12, 12);

        // Draw the outline
        g.setColor(Color.white);
        g.drawRoundRect(x+5,y+7, 50, 74, 12, 12);

        String s = rank;
        Boolean doubleDigit = false;
        if (rank.equals(("Wild"))) {
            s = "WC";
            doubleDigit = true;
        } else if (rank.equals("Wild Draw")) {
            s = "WD";
            doubleDigit = true;
        } else if (rank.equals("Skip")) {
            s = "S";
        } else if (rank.equals("Draw Two")) {
            s = "D2";
            doubleDigit = true;
        } else if (Integer.parseInt(rank) > 9) {
            doubleDigit = true;
        }

        g.setFont(new Font("Monospaced", Font.PLAIN, 40));
        if (doubleDigit) {
            // TODO: fix the symmetry
            g.drawString(s, x + 5, y + 60);
        } else {
            g.drawString(s, x + 18, y + 60);
        }
    }
}
