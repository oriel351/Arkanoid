
/**
 * this class has the info to make all blocks for the level.
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-10-14
 */


package gamedefinitions;

import java.util.Map;

import objects.Block;

public class BlocksFromSymbolsFactory {

    private Map<String, String> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Constructor.
     * @param spacerWidths the map that connects between symbols and spacer widths.
     * @param blockCreators the map that connects between symbols and blocks that need to be created.
     */
    public BlocksFromSymbolsFactory(Map<String, String> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;

        /**
         * this.spacerWidths = new TreeMap<String, Integer>() ;
         * this.blockCreators = new TreeMap<String, BlockCreator>();
         */
    }

    /**
     * returns true if 's' is a valid space symbol.
     * @param s is a symbol in the map.
     * @return if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * returns true if 's' is a valid block symbol.
     * @param s is a symbol in the map.
     * @return if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     * Return a block according to the definitions associated
     * with symbol s. The block will be located at position (xpos, ypos).
     * @param s is a symbol in the map.
     * @param xpos the x position of upperLeft corner of block.
     * @param ypos the y position of upperLeft corner of block.
     * @return a new block to be shown in the game.
     */

    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * creating spaces.
     * @param s is a symbol in the map.
     * @return the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return Integer.parseInt(this.spacerWidths.get(s));
    }

}
