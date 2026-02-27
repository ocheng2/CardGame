import javax.swing.*;
import java.awt.*;

// Card Class by Olivia Cheng
public class Card {
    private String rank;
    private String suit;
    private int value;

    public static final int CARD_WIDTH = 60;
    public static final int CARD_HEIGHT = 90;
    public static final int OUTLINE_WIDTH = 50;
    public static final int OUTLINE_HEIGHT = 74;

    // Uno Colors
    public static final Color UNO_RED = new Color(204, 0, 0);
    public static final Color UNO_YELLOW = new Color(241, 194, 50);
    public static final Color UNO_GREEN = new Color(56, 118, 29);
    public static final Color UNO_BLUE = new Color(62, 133, 198);

    // Padding
    public static final int CORNER_RADIUS = 12;
    public static final int OUTLINE_MARGIN_X = 5;
    public static final int OUTLINE_MARGIN_Y = 7;
    public static final int FONT_MARGIN_Y = 60;
    public static final int FONT_MARGIN_SINGLEX = 8;
    public static final int FONT_MARGIN_DOUBLEX = 18;

    // Font Size
    public static final int FONT_SIZE = 40;

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

    public void setValue(int value) { this.value = value; }

    public String toString()
    {
        return this.rank + " of " + this.suit;
    }

    public Color getColor() {
        if (rank.equals("Wild") || rank.equals("Wild Draw")) {
            return Color.BLACK;
        } else if(suit.equals("Red")) {
            return UNO_RED;
        } else if (suit.equals("Yellow")) {
            return UNO_YELLOW;
        } else if (suit.equals("Green")) {
            return UNO_GREEN;
        } else {
            return UNO_BLUE;
        }
    }

    public void drawBack(Graphics g, int x, int y, boolean flip) {
        g.setColor(Color.BLACK);

        if (flip) {
            g.fillRoundRect(x, y, CARD_HEIGHT, CARD_WIDTH, CORNER_RADIUS, CORNER_RADIUS);

            // Draw the outline
            g.setColor(Color.white);
            g.drawRoundRect(x + OUTLINE_MARGIN_Y,y + OUTLINE_MARGIN_X, OUTLINE_HEIGHT, OUTLINE_WIDTH, CORNER_RADIUS, CORNER_RADIUS);
        } else {
            g.fillRoundRect(x, y, CARD_WIDTH, CARD_HEIGHT, CORNER_RADIUS, CORNER_RADIUS);

            // Draw the outline
            g.setColor(Color.white);
            g.drawRoundRect(x + OUTLINE_MARGIN_X,y+ OUTLINE_MARGIN_Y, OUTLINE_WIDTH, OUTLINE_HEIGHT, CORNER_RADIUS, CORNER_RADIUS);
        }
    }

    public void draw(Graphics g, int x, int y) {
        // Draw the card
        g.setColor(getColor());
        g.fillRoundRect(x, y, CARD_WIDTH, CARD_HEIGHT, CORNER_RADIUS, CORNER_RADIUS);

        // Draw the outline
        g.setColor(Color.white);
        g.drawRoundRect(x + OUTLINE_MARGIN_X,y + OUTLINE_MARGIN_Y, OUTLINE_WIDTH, OUTLINE_HEIGHT, CORNER_RADIUS, CORNER_RADIUS);


        // Used to determine the coordinates of the string
        boolean doubleDigit = false;

        // Selects the correct string inside the UNO Card and the placement
        String s = rank;
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

        // Set up the font for String
        g.setFont(new Font("Monospaced", Font.PLAIN, FONT_SIZE));

        if (doubleDigit) {
            g.drawString(s, x + FONT_MARGIN_SINGLEX, y + FONT_MARGIN_Y);
        } else {
            g.drawString(s, x + FONT_MARGIN_DOUBLEX, y + FONT_MARGIN_Y);
        }
    }
}
