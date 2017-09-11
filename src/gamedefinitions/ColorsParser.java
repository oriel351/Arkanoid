
/**
 * this class generates a color by info from some source.
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-10-15
 */

package gamedefinitions;

import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;

import biuoop.DrawSurface;

public class ColorsParser implements Fill {

    private String colorString;
    private Map<String, Color> variants;
    private Color color;

    /**
     * constructor.
     * @param color the given color.
     */
    public ColorsParser(String color) {
        this.colorString = color;
        this.variants = null;
        this.color = null;
        generateColorFromString();

    }

    /**
     * this function is generating a color from string.
     */
    public void generateColorFromString() {
        int begin = this.colorString.lastIndexOf("(") + 1;
        int end = this.colorString.indexOf(")"); // as substring function works, it gets to end - 1 index
        String values = this.colorString.substring(begin, end);
        // if BASED ON RGB:d
        if (this.colorString.contains("RGB")) {
            String[] nums = values.split(",");
            this.color = new Color(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), Integer.parseInt(nums[2]));

        } else { // ELSE MANUALLY FIND THE COLOR FROM A MAP COLOR.
            generateMap();
            this.color = this.variants.get(values);
            if (this.color == null) {
                this.color = this.variants.get("default");
            }
        }

    }

    /**
     * this function generates a map of colors.
     */
    public void generateMap() {
        this.variants = new TreeMap<String, Color>();
        this.variants.put("black", Color.black);
        this.variants.put("gray", Color.gray);
        this.variants.put("lightGray", Color.lightGray);
        this.variants.put("red", Color.red);
        this.variants.put("yellow", Color.yellow);
        this.variants.put("cyan", Color.cyan);
        this.variants.put("blue", Color.cyan);
        this.variants.put("magenta", Color.magenta);
        this.variants.put("pink", Color.pink);
        this.variants.put("orange", Color.orange);
        this.variants.put("white", Color.white);
        this.variants.put("darkGray", Color.darkGray);
        this.variants.put("green", Color.green);
        this.variants.put("default", Color.PINK);

    }

    /**
     * @return the chosen color.
     */
    public Color getColor() {
        return this.color;
    }

    @Override
    public void drawInside(DrawSurface surface, int x, int y, int width, int height) {

        if (this.color == null) { // some default color value. should never happen.
            this.color = Color.RED;
        }
        /* surface.setColor(this.color);
         * surface.fillRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
         * (int) shape.getWidth(), (int) shape.getHeight()); */

        surface.setColor(this.color);
        surface.fillRectangle(x, y, width, height);

    }
}
