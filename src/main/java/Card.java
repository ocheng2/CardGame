import javax.swing.*;
import java.awt.*;

// Card Class by Olivia Cheng
public class Card {
    private String rank;
    private String suit;
    private int value;
    private int x;
    private int y;

    public static final int CARD_WIDTH = 60;
    public static final int CARD_HEIGHT = 90;

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
            return new Color(204,0,0);
        } else if (suit.equals("Yellow")) {
            return new Color (241,194,50);
        } else if (suit.equals("Green")) {
            return new Color(56,118,29);
        } else {
            return new Color(62, 133, 198);
        }
    }

    public void drawBack(Graphics g, int x, int y, boolean flip) {
        g.setColor(Color.BLACK);

        if (flip) {
            g.fillRoundRect(x, y, CARD_HEIGHT, CARD_WIDTH, 12, 12);

            // Draw the outline
            g.setColor(Color.white);
            g.drawRoundRect(x+7,y+5, CARD_HEIGHT- 16, CARD_WIDTH - 10,12, 12);
        } else {
            g.fillRoundRect(x, y, CARD_WIDTH, CARD_HEIGHT, 12, 12);

            // Draw the outline
            g.setColor(Color.white);
            g.drawRoundRect(x+5,y+7, CARD_WIDTH - 10,CARD_HEIGHT - 16, 12, 12);
        }
    }

    public void draw(Graphics g, int x, int y) {
        // Draw the card
        g.setColor(getColor());
        g.fillRoundRect(x, y, CARD_WIDTH, CARD_HEIGHT, 12, 12);

        // Draw the outline
        g.setColor(Color.white);
        g.drawRoundRect(x+5,y+7, CARD_WIDTH - 10, CARD_HEIGHT - 16, 12, 12);

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
            g.drawString(s, x + 8, y + 60);
        } else {
            g.drawString(s, x + 18, y + 60);
        }
    }
}
