// GameView by Olivia Cheng
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JFrame {
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 800;

    public final int BACKGROUND_COOR = 0;

    // Position of the Player cards
    public final int X_VER = 200;
    public final int Y_VER_1 = 50;
    public final int Y_VER_2 = 660;

    public final int Y_HOR = 600;
    public final int X_HOR_1 = 860;
    public final int X_HOR_2 = 50;

    // Color Constant
    public final Color COLOR_BACKGROUND = new Color(1,86,75);
    public final Color COLOR_BUTTON = new Color(19,56,95);

    // Default coordinates, lengths, and arcs for the action buttons
    public final int BUTTON_WIDTH = 120;
    public final int BUTTON_HEIGHT = 45;
    public final int BUTTON_ARC = 12;
    public final int BUTTON_MARGIN_X = 10;
    public final int BUTTON_MARGIN_Y = 28;

    // Default font sizes used throughout the views
    public final int FONTSIZE_TITLE = 200;
    public final int FONTSIZE_SUBTITLE = 30;
    public final int FONTSIZE_TEXT = 15;

    // Default margins for the distance between the name to the player's hand
    public final int NAME_MARGIN_HOR = 110;
    public final int NAME_MARGIN_VER = 80;

    // Default coordinates for our central console cards
    public final int TOPCARD_X = 420;
    public final int CARDSTACK_Y = 430;
    public final int DRAWCARD_X = 520;

    public final int CARD_SPACING = 70;

    // Default coordinates for the different titles and subtitles
    public final int TITLE_X = 110;
    public final int TITLE_Y = 600;

    public final int SUBTITLE_X = 130;
    public final int SUBTITLE_Y = 650;

    private Game backend;
    private Image instructionsImage;

    // Constructor of GameView
    public GameView(Game backend) {
        this.backend = backend;
        this.instructionsImage = new ImageIcon("src/main/resources/Instructions.png").getImage();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("UNO Game");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    // Manages the different view states
    public void paint(Graphics g) {
        int state = backend.getState();

        // Initial view with the UNO logo
        if (state == Game.STATE_BEGIN) {
            paintInitialUNO(g);
        }
        // Instructions view
        else if (state == Game.STATE_INSTRU) {
            g.drawImage(instructionsImage, BACKGROUND_COOR, BACKGROUND_COOR, this);
        }
        // Game view
        else if (state == Game.STATE_MAIN) {
            paintBackground(g);

            // Paints the hand of currPlayer and the amount of cards for the other Players
            paintPlayers(g, backend.getPlayers());

            // Draws the central console buttons and cards
            paintActionButtons(g, "PLACE A CARD", 370, 360);
            paintActionButtons(g, "DRAW A CARD", 510, 360);
            paintCenterCards(g, backend.getTopCard());
        }
        // End view
        else if (state == Game.STATE_END) {
            paintEndUNO(g);
        }
    }

    public void paintInitialUNO(Graphics g) {
        // Paint the green background
        paintBackground(g);

        // Set up font and write title name
        g.setFont(new Font("Serif", Font.ITALIC, FONTSIZE_TITLE));
        g.setColor(Color.WHITE);
        g.drawString("UNO", TITLE_X, TITLE_Y);

        // Set up font and write subtitle name
        g.setFont(new Font("Serif", Font.ITALIC, FONTSIZE_SUBTITLE));
        g.drawString("Press Enter to Begin", SUBTITLE_X, SUBTITLE_Y);
    }

    public void paintEndUNO(Graphics g) {
        paintBackground(g);

        // Set up font and write title
        g.setFont(new Font("Serif", Font.ITALIC, FONTSIZE_TITLE));
        g.setColor(Color.WHITE);
        g.drawString("THE END", TITLE_X, TITLE_Y);

        // Set up font and write subtitle name
        g.setFont(new Font("Serif", Font.ITALIC, FONTSIZE_SUBTITLE));
        g.drawString("Hope you had some fun.", SUBTITLE_X, SUBTITLE_Y);
    }

    // Paints the center console of the Game, including topCard and the default draw stack
    public void paintCenterCards(Graphics g, Card c) {
        // Make sure topCard exists
        if (c != null) {
            c.draw(g, TOPCARD_X,CARDSTACK_Y);
        }
        // Draw the face-down card next to it
        new Card("0", "Blue").drawBack(g, DRAWCARD_X, CARDSTACK_Y, false);
    }

    // Draws the action buttons for Draw or Place Card
    public void paintActionButtons(Graphics g, String text, int x, int y) {
        // Draw the outer button
        g.setColor(COLOR_BUTTON);
        g.fillRoundRect(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_ARC, BUTTON_ARC);

        // Set up font
        g.setFont(new Font("Monospaced", Font.PLAIN, FONTSIZE_TEXT));
        g.setColor(Color.WHITE);

        // Center the text in buttons
        g.drawString(text, x + BUTTON_MARGIN_X, y + BUTTON_MARGIN_Y);
    }

    // Goes through the player's hand to draw either their hand or the amount of cards
    public void paintPlayerHand(Graphics g, ArrayList<Card> hand, int positionIndex, int x, int y) {
        // Iterates through player's hand to draw each one
        for (int i = 0; i < hand.size(); i++) {
            // Draw their hand at the bottom if currPlayer
            if (positionIndex == 0) {
                hand.get(i).draw(g, x, y);
                // Draw the index of the card below the card
                String index = Integer.toString(i);
                g.drawString(index, x + (hand.get(i).CARD_WIDTH/2 - 10), y - 20);
                // Ensures the cards do not overlap
                x += CARD_SPACING;
            }
            // If they are 1st or 3rd player, draw cards horizontal
            else if (positionIndex % 2 == 1) {
                hand.get(i).drawBack(g, x, y, true);
                // Ensures the cards do not overlap
                y -= CARD_SPACING;
            }
            // If they are 2nd player, draw cards vertical
            else {
                hand.get(i).drawBack(g, x, y, false);
                // Ensures the cards do not overlap
                x += CARD_SPACING;
            }
        }
    }

    // Returns the starting x value of the player's hand depending on their placement from currPlayer
    public int getCardX(int positionIndex) {
        if (positionIndex == 0 || positionIndex == 2) {
            return X_VER;
        } else if (positionIndex == 1) {
            return X_HOR_2;
        } else {
            return X_HOR_1;
        }
    }

    // Returns the starting y value of the player's hand depending on their placement from currPlayer
    public int getCardY(int positionIndex) {
        if (positionIndex == 1 || positionIndex == 3) {
            return Y_HOR;
        } else if (positionIndex == 0) {
            return Y_VER_2;
        } else {
            return Y_VER_1;
        }
    }

    // Paints the name and hand of each Player, on different sides of the window
    public void paintPlayers(Graphics g, Player[] players) {
        for (int i = 0; i < players.length; i++) {
            // Set up the player
            Player p = players[i];

            // Find their position in order of currPlayer as 0
            int positionIndex = (i - backend.getCurrPlayerIndex() + players.length) % players.length;;

            // Find the starting x, y coor for each player's hand
            int x = getCardX(positionIndex);
            int y = getCardY(positionIndex);

            // Set up the font
            g.setFont(new Font("Serif", Font.ITALIC, FONTSIZE_TEXT));
            g.setColor(Color.WHITE);
            drawName(g, p, x, y, positionIndex);

            // Draw their Hand
            paintPlayerHand(g, p.getHand(), positionIndex, x, y);
        }
    }

    // Depending on their position from currPlayer, their name will appear in different places
    public void drawName(Graphics g, Player p, int x, int y, int positionIndex) {
        if (positionIndex % 2 == 0) {
            y += NAME_MARGIN_HOR;
        } else {
            y += NAME_MARGIN_VER;
        }

        g.drawString("Player: " + p.getName(), x, y);
    }

    // Paints the default green background
    public void paintBackground(Graphics g) {
        g.setColor(COLOR_BACKGROUND);
        g.fillRect(BACKGROUND_COOR, BACKGROUND_COOR, WINDOW_WIDTH, WINDOW_HEIGHT);
    }
}
