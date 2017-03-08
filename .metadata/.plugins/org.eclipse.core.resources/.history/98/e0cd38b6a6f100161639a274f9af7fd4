package fishtank;
import java.awt.event.*;

/**
 * A fish tank simulation.
 */
public class FishTank {

    /** The width of a character. */
    public final static int charWidth = 6;
    /** The height of a character. */
    public final static int charHeight = 10;
    /** (int)(640/6) columns, (int)(480/10) rows. */
    public static Object[][] myLittleFishies = new Object[(int)(480/10)][(int)(640/6)];

    /**
     * Start the fishies!
     * @param pars ignored.  Why is this normally called args when the thing is called
     * a parameter?
     */
    public static void main(String[] pars) {

      // The window in which the fish swim.
        FishFrame f = new FishFrame();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        myLittleFishies[23][18]=new Fish();
        ((Fish)myLittleFishies[23][18]).setLocation(23,18);
        myLittleFishies[6][12]=new Fish();
        ((Fish)myLittleFishies[6][12]).setLocation(6,12);
        myLittleFishies[17][4]=new Fish();
        ((Fish)myLittleFishies[17][4]).setLocation(17,4);
        myLittleFishies[15][28]=new Fish();
        ((Fish)myLittleFishies[15][28]).setLocation(15,28);
        myLittleFishies[23][18]=new Fish();
        ((Fish)myLittleFishies[23][18]).setLocation(15,36);
        myLittleFishies[16][35]=new Fish();
        ((Fish)myLittleFishies[16][35]).setLocation(16,35);
        myLittleFishies[16][35]=new Fish();
        ((Fish)myLittleFishies[16][35]).setLocation(16,35);
        myLittleFishies[16][22]=new Fish();
        ((Fish)myLittleFishies[16][22]).setLocation(16,18);
        myLittleFishies[23][18]=new Fish();
        ((Fish)myLittleFishies[23][18]).setLocation(23,18);
        myLittleFishies[6][12]=new Fish();
        ((Fish)myLittleFishies[6][12]).setLocation(6,12);
        myLittleFishies[10][20]=new HungryFish();
        ((HungryFish)myLittleFishies[10][20]).setLocation(10,33);
        myLittleFishies[24][33]=new Seaweed(6);
        ((Seaweed)myLittleFishies[24][33]).setLocation(24,33);
        myLittleFishies[32][25]=new Seaweed(7);
        ((Seaweed)myLittleFishies[32][25]).setLocation(32,25);
        myLittleFishies[13][25]=new Seaweed(5);
        ((Seaweed)myLittleFishies[13][25]).setLocation(13,25);

        // Show it all!
        f.setSize(640, 480);
        f.setLocation(10,10);
        f.setVisible(true);

            // Every .3 seconds, tell each item in the fishtank to take
            // a turn.
            while (true) {

                for(int a=0;a!=(int)(480/10);a++){
                for(int b=0;b!=(int)(640/6);b++){
                    if (myLittleFishies[a][b] != null)
                        if (myLittleFishies[a][b] instanceof Fish) {
                            ((Fish) myLittleFishies[a][b]).move();
                        }else if (myLittleFishies[a][b] instanceof Seaweed) {
                          ((Seaweed) myLittleFishies[a][b]).wave();
                        }else if (myLittleFishies[a][b] instanceof HungryFish) {
                          ((HungryFish) myLittleFishies[a][b]).move();
                        }if (myLittleFishies[a][b] instanceof Bubble) {
                          // Figure out whether to float left or right, if at all.
                          Bubble heybub= (Bubble) myLittleFishies[a][b];
                          heybub.d = Math.random();
                          if (heybub.d < 0.33) heybub.floatStraightUp();
                          else if (heybub.d < 0.66) heybub.floatRightUp();
                          else /* heybub.d >= 0.66 */ heybub.floatLeftUp();
                        }
                }
                }

                // Tell the fishtank to redraw itself.
                f.repaint();

                // Wait .3 seconds before redoing the queue.
                try { Thread.sleep(300); } catch(Exception e) {}
            }

    }
}
