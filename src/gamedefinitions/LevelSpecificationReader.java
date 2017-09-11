
/**
 * this class is reading the level information from text file.
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-10-11
 */

package gamedefinitions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import objects.Block;
import level.LevelInfoGenerator;
import level.LevelInformation;

public class LevelSpecificationReader {

    private List<LevelInformation> levels;

    /**
     * constructor.
     */
    public LevelSpecificationReader() {
        this.levels = new ArrayList<LevelInformation>();
    }

    /**
     * @param reader which we read from.
     * @return a list of level informations.
     */
    public List<LevelInformation> fromReader(Reader reader) {
        String line;

        try {
            BufferedReader input = new BufferedReader(reader);
            while ((line = input.readLine()) != null) {

                if (line.startsWith("#") || line.isEmpty() || line.startsWith("END_LEVEL")) {
                    continue;
                }

                if (line.contains("START_LEVEL")) {
                    this.levels.add(fromSubString(input));
                }
            }
        } catch (IOException e) {
            System.out.println("problem with reader");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("Failed closing file.");
            }
        }
        return this.levels;

    }

    /**
     * creates the different levels through a reader and given text files.
     * @param reader an reader object.
     * @return new level generator.
     */
    public LevelInformation fromSubString(BufferedReader reader) {

        // Level reader and definition :

        BufferedReader sLevel = reader;
        Map<String, String> levelProperties = new TreeMap<String, String>();

        // Blocks reader and definition :

        BlocksDefinitionReader readBlocks = null;
        BlocksFromSymbolsFactory blocksFactory = null;
        Reader blockDefFile = null;

        List<Block> blockList = new ArrayList<Block>();
        String line = null;
        String bdef = null;

        try {
            line = sLevel.readLine();
        } catch (IOException e1) {
            System.out.println("error reading a line in level def");
        }
        // FIRST - GETTING THE LEVEL INFO:

        while (!line.startsWith("START_BLOCKS")) {
            if (line.startsWith("#") || line.isEmpty()) {
                try {
                    line = sLevel.readLine();
                } catch (IOException e) {
                    System.out.println("error reading a line in level def");
                }
                continue;
            }
            String[] splited = line.split(":");
            levelProperties.put(splited[0], splited[1]);
            try {
                line = sLevel.readLine();
            } catch (IOException e) {
                System.out.println("error reading a line in level def");
            }
        } // END OF WHILE

        bdef = levelProperties.get("block_definitions");

        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(bdef);

            blockDefFile = new InputStreamReader(is);

        } catch (Exception e) {
            System.out.println("error reading the block def file");
        }

        // reading the block definitions file :

        readBlocks = new BlocksDefinitionReader(); // BlocksFromSymbolsFactory object

        // ****** READING THE BLOCKS FILE : ******
        blocksFactory = readBlocks.fromReader(blockDefFile); // // the BlockGenerator

        int ypos = Integer.parseInt(levelProperties.get("blocks_start_y"));;
        try {
            while ((line = sLevel.readLine()) != null) {
                if (line.startsWith("END_BLOCKS")) {
                    break;
                }
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }
                ypos += Integer.parseInt(levelProperties.get("row_height"));
                int xpos = Integer.parseInt(levelProperties.get("blocks_start_x"));
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    String current = Character.toString(c);

                    if (blocksFactory.isSpaceSymbol(current)) {

                        xpos += blocksFactory.getSpaceWidth(current);
                    }

                    if (blocksFactory.isBlockSymbol(current)) {
                        Block b = blocksFactory.getBlock(current, xpos, ypos);
                        blockList.add(b);
                        xpos += b.getCollisionRectangle().getWidth();

                    }
                }

            } // END OF WHILE !!!!!

            // easier map variable to read in LevelInformation creation:
            Map<String, String> pr = levelProperties;
            // WE CAN ALWAYS ADD ADITIONAL INFORMATION AND SIMPLY RETRIEVE IT.

            return new LevelInfoGenerator(pr.get("level_name"), pr.get("ball_velocities"), pr.get("background"),
                Integer.parseInt(pr.get("paddle_speed")), Integer.parseInt(pr.get("paddle_width")), Integer.parseInt(pr
                        .get("num_blocks")), blockList);


        } catch (NumberFormatException e) {
            System.out.println("format problem after block def file");
        } catch (IOException e) {

            System.out.println("error reading a line after block def file");
        }

        // default return value if failed for some reason
        return null;

    }
}
