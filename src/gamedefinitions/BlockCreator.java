package gamedefinitions;

import objects.Block;

/**
 * an interface of a factory-object that is used for creating blocks.
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-10-11
 */
public interface BlockCreator {
   /**
    *  Create a block at the specified location.
    * @param xpos x coordinate of upperLeft point.
    * @param ypos y coordinate of upperLeft point.
    * @return a new Block.
    */
    Block create(int xpos, int ypos);
 }
