

/**
 * this class is reading the level information from text file.
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-10-11
 */

package gamedefinitions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;

public class BlocksDefinitionReader {

    private Map<String, String> spacer;
    private Map<String, BlockCreator> blockCreators;
    private Map<String, String> defaultValues;
 //   private Map<String, String> values;

    /**
     * constructor.
     */
    public BlocksDefinitionReader() {
        this.spacer = new TreeMap<String, String>();
        this.blockCreators = new TreeMap<String, BlockCreator>();
        this.defaultValues = new TreeMap<String, String>();
        // this.values = new TreeMap<String, String>();
    }

    /**
     * @param reader a reader that helps us to read the file.
     * @return the factory information to create blocks.
     */
    public BlocksFromSymbolsFactory fromReader(Reader reader) {

        String line = null;
        try {
            BufferedReader input = new BufferedReader(reader);

            while ((line = input.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }
                // **********DEFAULTS**********
                if (line.startsWith("default")) {
                    String[] defs = splitLine(line);

                    for (int i = 0; i < defs.length; i++) {
                        String[] defVal = defs[i].split(":");
                        this.defaultValues.put(defVal[0], defVal[1]);
                    }
                }
                // ********* BLOCKS *************
                if (line.startsWith("bdef")) {
                    addValues(splitLine(line));
                }

                // *********SPACERS ***********
                if (line.startsWith("sdef")) {

                    String[] sdef = splitLine(line);
                    String[] widthAndVal = new String[2];;
                    String widthVal;
                    for (int i = 0; i < sdef.length; i++) {
                        String[] svals = sdef[i].split(":");
                        widthAndVal[i] = svals[1];
                    }
                    this.spacer.put(widthAndVal[0], widthAndVal[1]);
                }

            } // END OF WHILE

            return new BlocksFromSymbolsFactory(this.spacer, this.blockCreators);

        } catch (IOException e) {
            System.out.println("some IO error");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("Failed closing file.");
            }
        }
        // if there was some problem we return null as default.
        return null;

    } // END OF fromREADER

    /**
     * @param line the line we read from the file
     * @return the value depending on the key
     */
    public String[] splitLine(String line) {

        int begin;
        begin = line.indexOf(" ");
        // end = line.lastIndexOf(" ");

        // Splitting into (key:value) :
        String sub = line.substring(begin + 1);
        String[] vals = sub.split("\\s");
        return vals;
    }

    /**
     * using the parts of the line we read from the file to add values to the map.
     * @param splitedLine the parts of the line we read from the file.
     */
    public void addValues(String[] splitedLine) {

        Map<String, String> values = new TreeMap<String, String>();
        String symbol = splitedLine[0].split(":")[1]; // getting the letter of the symbol.

        for (int i = 1; i < splitedLine.length; i++) { // starting from the first definition.
            String[] vals = splitedLine[i].split(":");
            values.put(vals[0], vals[1]);
        }
        for (Map.Entry<String, String> entry : this.defaultValues.entrySet()) {
            if (!values.containsKey(entry.getKey())) {
                values.put(entry.getKey(), entry.getValue());
            }
        }
        // this.values = new TreeMap<String, String>(); // reseting the valuse for next symbol.
        BlockCreator creator = new BlockGenerator(values);
        this.blockCreators.put(symbol, creator);

    }
}
