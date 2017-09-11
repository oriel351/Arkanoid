
/**
 * this class generates a background for the game. using Fill interface..
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-10-15
 */

package gamedefinitions;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import biuoop.DrawSurface;

public class ImgParser implements Fill {

    private String path;
    private Image img;
    /**
     * constructor.
     * @param bg the given path.
     */
    public ImgParser(String bg) {
        int begin = bg.indexOf("(");
        int end = bg.lastIndexOf(")");
        this.path = bg.substring(begin + 1, end);

        Image nonBuffered = null;
        try {
            nonBuffered = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(this.path));
        } catch (IllegalArgumentException e) {
            System.out.println("there was an error finding the file ImgParser");
        } catch (IOException e) {
            System.out.println("the was some IO exception");
        }
        this.img =  nonBuffered;
    }

    /**
     * Buffering the image from the given path.
     * @return the image.
     */
    public Image getImg() {

        Image nonBuffered = null;
        try {
            nonBuffered = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(this.path));
        } catch (IllegalArgumentException e) {
            System.out.println("there was an error finding the file ImgParser");
        } catch (IOException e) {
            System.out.println("the was some IO exception");
        }
        return nonBuffered;

    }

    @Override
    public void drawInside(DrawSurface surface, int x, int y, int width, int height) {

       //surface.drawImage((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(), getImg());
        surface.drawImage(x, (int) y, this.img);



    }

}
