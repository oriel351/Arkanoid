
/**
 * this class is reading the level sets from text file.
 * @author Oriel Gaash 
 * @version 1.7
 * @since 2015-10-24
 */

package gamedefinitions;

import gameplay.Option;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class LevelSetsReader {

    /**
     * constructor.
     */
    public LevelSetsReader() {

    }

    /**
     * @param reader which we read from.
     * @return a list of level informations.
     */
    public static List<Option<String>> fromReader(Reader reader) {
        List<Option<String>> options = new ArrayList<Option<String>>();

        String line = null;
        String[] levelDef = null;

        Option<String> sub = null;

        LineNumberReader input = null;



        try {
            input = new LineNumberReader(reader);
            while ((line = input.readLine()) != null) {

                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                if (input.getLineNumber() % 2 != 0) { // odd line - (key:level name) format
                    levelDef = line.split(":");

                } else { // even line - we have path and we create the task.
                    String levelSetPath = line;
                    sub = new Option<String>(levelDef[0], levelDef[1], levelSetPath);
                    options.add(sub);
                }
            }
        } catch (IOException e) {
            System.out.println("problem with level sets reader");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("Failed closing file(level sets.");
            }
        }

        return options;

    }
}
