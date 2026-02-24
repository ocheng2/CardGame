// GameView by Olivia Cheng
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JFrame {
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 800;
    public final int TITLE_BAR_HEIGHT = 23;
    private Game backend;

    private Image gameImage;
    private Image instructionsImage;


    public GameView(Game backend) {
        this.backend = backend;
        this.instructionsImage = new ImageIcon("src/main/resources/Instructions.png").getImage();
        this.gameImage = new ImageIcon("src/main/resources/GameCard.png").getImage();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("UNO Game");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);

        int state = backend.getState();

        if (state == Game.STATE_INSTRU) {
            g.drawImage(instructionsImage, 0, 0, this);
        } else if (state == Game.STATE_MAIN) {
            paintGame(g);
            paintTopCard(g, backend.getTopCard());
            paintDeck(g, backend.getCurrPlayer());
        } else if (state == Game.STATE_END) {
            g.setColor(Color.green);
        }
    }

    public void paintTopCard(Graphics g, Card c) {
        c.draw(g, 500,400);
    }

    public void actionButtons(Graphics g) {

    }

    // Draw the player's hand
    public void paintDeck (Graphics g, Player p) {
        ArrayList<Card> hand = p.getHand();
        String name = p.getName();

        // Coordinates of the card
        int x = 100;
        int y = 700;

        // Iterates through player's hand to draw each one
        for (Card c: hand) {
            c.draw(g, x, y);

            // Ensures the cards do not overlap
            x += 70;
        }
    }

    public void paintGame(Graphics g) {
        g.drawImage(gameImage, 0, 0, this);
    }
}
