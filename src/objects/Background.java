package objects;

/**
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-09-27
 */
import gamedefinitions.ColorsParser;
import gamedefinitions.Fill;
import gamedefinitions.ImgParser;
import biuoop.DrawSurface;
import level.GameLevel;

/**
 * this class shows a beautiful background.
 */
public class Background implements Sprite {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Fill bg;

    /**
     * constructor.
     * @param address the photo's directory
     */
    public Background(String address) {

        if (address.contains("image")) {
            this.bg = new ImgParser(address);
        } else {
            this.bg = new ColorsParser(address);
        }

        /* int begin = address.lastIndexOf("(") + 1;
         * int end = address.indexOf(")"); // as substring function works, it gets to end - 1 index
         * this.bgAddress = address.substring(begin, end); */

    }

    @Override
    public void drawOn(DrawSurface d) {

        /* BufferedImage img = null;
         * try {
         * img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(this.bgAddress));
         * } catch (IOException e) {
         * // TODO Auto-generated catch block
         * System.out.println("failed drawing the background");
         * }
         * d.drawImage(0, 0, img); */
        this.bg.drawInside(d, 0, 0, WIDTH, HEIGHT);

    }

    @Override
    public void timePassed(double dt) {

    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);

    }

}
