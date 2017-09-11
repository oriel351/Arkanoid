

/**
 * this class generates a single block from the information extracted from text file.
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-10-15
 */



package gamedefinitions;

import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;




import objects.Block;
import physics.Point;

public class BlockGenerator implements BlockCreator {

    private Map<String, String> blockDefinition;

    /**
     * Constructor.
     * @param blockDefinition the map that helps us building the different blocks.
     */
    public BlockGenerator(Map<String, String> blockDefinition) {
        this.blockDefinition = blockDefinition;
    }

    @Override
    public Block create(int xpos, int ypos) {

        Point upperLeft = new Point(xpos, ypos);
        double width = Double.parseDouble(this.blockDefinition.get("width"));
        double height = Double.parseDouble(this.blockDefinition.get("height"));
        int numOfHits = Integer.parseInt(this.blockDefinition.get("hit_points"));
        Color strokeColor = null;
        Map<Integer, Fill> fills = getFills(numOfHits);

        String stroke = this.blockDefinition.get("stroke");
        if (stroke != null) {
            strokeColor = new ColorsParser(stroke).getColor();
        }
        return new Block(upperLeft, width, height, numOfHits, fills, strokeColor);

    }

    /**
     * generates the background/color of the block.
     * @param hits is the number of hits of the block.
     * @return the background/color of the block
     */
    public Map<Integer, Fill> getFills(int hits) {
        Map<Integer, Fill> fills = new TreeMap<Integer, Fill>();
        Fill defFill;

        // DEFAULT FILL :

        String defVal = this.blockDefinition.get("fill"); // we support either fill or fill-1 pattern for default.
        if (defVal == null) {
            defVal = this.blockDefinition.get("fill-1");
        }
        if (defVal.contains("image")) {
            defFill = new ImgParser(defVal);
        } else {
            defFill = new ColorsParser(defVal);
        }

        // IN CASE WE NEED TO HIT A BLOCK MORE THAN ONCE:
        if (hits > 1) {
            for (int i = hits; i > 0; i--) {
                String bg = this.blockDefinition.get("fill-" + i);
                if (bg != null) {
                    if (bg.contains("image")) {
                        fills.put(i, new ImgParser(bg));
                    } else {
                        fills.put(i, new ColorsParser(bg));
                    }
                } else { // we dont have the fill=k definition , we use the default.
                   fills.put(i, defFill);
                }

            }
                         // END CASE > 1.
        } else {
            fills.put(1, defFill);


        }


        return fills;

    }
}
