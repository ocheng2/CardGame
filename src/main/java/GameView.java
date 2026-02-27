// GameView by Olivia Cheng
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JFrame {
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 800;
    public final int TITLE_BAR_HEIGHT = 23;

    // Position of the Player cards
    public final int X_VER = 200;
    public final int Y_VER_1 = 50;
    public final int Y_VER_2 = 660;

    public final int Y_HOR = 600;
    public final int X_HOR_1 = 860;
    public final int X_HOR_2 = 50;

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

        if (state == Game.STATE_BEGIN) {
          drawUNO(g);
        }
        else if (state == Game.STATE_INSTRU) {
            g.drawImage(instructionsImage, 0, 0, this);
        } else if (state == Game.STATE_MAIN) {
            paintGame(g);
            paintPlayers(g, backend.getPlayers());

            actionButtons(g, "PLACE A CARD", 370, 360);
            actionButtons(g, "DRAW A CARD", 510, 360);

            paintTopCard(g, backend.getTopCard());
            paintPlayers(g, backend.getPlayers());
        } else if (state == Game.STATE_END) {
            g.setColor(Color.green);
        }
    }

    public void drawUNO(Graphics g) {
        g.setColor(new Color(1,86,75));
        g.fillRect(0, 0, 1000, 800);

        g.setFont(new Font("Serif", Font.ITALIC, 200));
        g.setColor(Color.WHITE);
        g.drawString("UNO", 110, 600);

        g.setFont(new Font("Serif", Font.ITALIC, 30));
        g.drawString("Press Enter to Begin", 130, 650 );
    }

    public void paintTopCard(Graphics g, Card c) {
        c.draw(g, 420,430);
        c.drawBack(g, 520, 430, false);
    }

    public void actionButtons(Graphics g, String text, int x, int y) {
        int width = 120;
        int height = 45;

        g.setColor(new Color(19,56,95));
        g.fillRoundRect(x, y, width, height, 12, 12);

        g.setFont(new Font("Monospaced", Font.PLAIN, 15));
        g.setColor(Color.WHITE);

        int textX = x + 10;
        int textY = y + 30;
        g.drawString(text, textX, textY);
    }

    public void paintPlayers(Graphics g, Player[] players) {
        int currX = 100;
        int currY = 100;
        int x, y;

        for (int i = 0; i < players.length; i++) {
            // Set up the player
            Player p = players[i];
            int positionIndex = (i - backend.getCurrPlayerIndex() + players.length) % players.length;;

            // Set up the font
            g.setFont(new Font("Serif", Font.ITALIC, 15));
            g.setColor(Color.WHITE);

            if (positionIndex == 0) {
                x = X_VER;
                y = Y_VER_2;
            } else if (positionIndex == 1) {
                x = X_HOR_2;
                y = Y_HOR;
            } else if (positionIndex == 2) {
                x = X_VER;
                y = Y_VER_1;
            } else {
                x = X_HOR_1;
                y = Y_HOR;
            }

            drawName(g, p, x, y, positionIndex);

            ArrayList<Card> hand = p.getHand();


            // Iterates through player's hand to draw each one
            for (int j = 0; j < hand.size(); j++) {
                Card c = hand.get(j);

                if (positionIndex == 0) {
                    c.draw(g, x, y);

                    // Ensures the cards do not overlap
                    x += 70;
                } else if (positionIndex % 2 == 1){
                    c.drawBack(g, x, y, true);

                    // Ensures the cards do not overlap
                    y -= 70;
                } else {
                    c.drawBack(g, x, y, false);

                    // Ensures the cards do not overlap
                    x += 70;
                }
            }
        }
    }

    public void drawName(Graphics g, Player p, int x, int y, int positionIndex) {
        if (positionIndex % 2 == 0) {
            y += 110;
        } else {
            y += 80;
        }

        g.drawString("Player: " + p.getName(), x, y);
    }

    public void paintGame(Graphics g) {
        g.setColor(new Color(1,86,75));
        g.fillRect(0, 0, 1000, 800);
    }
}
