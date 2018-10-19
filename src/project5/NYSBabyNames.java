package project5;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Scanner;


/**
 * This program:
 * opens a .csv of baby names (given as a command line argument),
 * creates Name objects from each line,
 * sorts them by year into YearNames objects,
 * and allows the user to check the popularity of baby names over years until they quit
 * @author Joshua Donelly-Higgins
 */
public class NYSBabyNames {

    public static void main(String[] args) {

        //verify that the command line argument exists
        if (args.length == 0) {
            System.err.println("Usage Error: the program expects file name as an argument.\n");
            System.exit(1);
        }

        //verify that command line argument contains a name of an existing file
        File dataFile = new File(args[0]);
        if (!dataFile.exists()) {
            System.err.println("Error: the file " + dataFile.getAbsolutePath() + " does not exist.\n");
            System.exit(1);
        }
        if (!dataFile.canRead()) {
            System.err.println("Error: the file " + dataFile.getAbsolutePath() +
                    " cannot be opened for reading.\n");
            System.exit(1);
        }

        //open the file for reading
        Scanner inData = null;


        try {
            inData = new Scanner(dataFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error: the file " + dataFile.getAbsolutePath() +
                    " cannot be opened for reading.\n");
            System.exit(1);
        }

        //read the content of the file and save the data
        ArrayList<YearNames> list = new ArrayList<YearNames>(10);
        String line = null;
        while (inData.hasNextLine()) {
            try {
                line = inData.nextLine();

                //parse line and add to dataset
                ArrayList<String> current = splitCSVLine(line);

                //check line has correct number of elements
                if (current.size() != 5) throw new IllegalArgumentException("Incomplete line.");

                //add a year entry if necessary
                boolean entryPresent = false;
                for (YearNames element : list) {
                    if (element.getYear() == Integer.parseInt(current.get(0))) {
                        entryPresent = true;
                        element.add(new Name(current.get(1), current.get(3), Integer.parseInt(current.get(4)), current.get(2)));
                    }
                }

                if (entryPresent == false) {
                    YearNames element = new YearNames(Integer.parseInt(current.get(0)));
                    element.add(new Name(current.get(1), current.get(3), Integer.parseInt(current.get(4)), current.get(2)));
                    list.add(element);


                }

            } catch (IllegalArgumentException ex) {
                //caused by an incomplete or miss-formatted line in the input file
                continue;
            }

        }

        //sort data by year

        Collections.sort(list);

        //Interactive Mode

        Scanner userInput  = new Scanner (System.in );
        String userValueName;
        String userValueCounty;


        do {
            System.out.print("Enter a name or \"q\" to stop:\t" );
            //get user input for name
            userValueName = userInput.nextLine().toLowerCase();
            System.out.println("");

            if (!userValueName.equals("q")) {
                System.out.print("Enter a county (enter \"ALL\" to see all counties):\t");
                //get user input for county
                userValueCounty = userInput.nextLine().toLowerCase();
                System.out.println("");

                //check if the name or county occurs
                boolean zeroOccurences = true;
                String printOutput = "";

                for (YearNames element : list) {
                    double val = element.getFractionByNameCounty(userValueName, userValueCounty);
                    if (val > 0) zeroOccurences = false;
                    printOutput += String.format("%d (%.4f) : ", element.getYear(), val * 100);
                    for (int i = 0; i < Math.ceil(val * 10000); i++) printOutput += ("|");
                    printOutput += "\n";
                }

                //print if name occurs
                if (zeroOccurences) System.out.println("No such name/county combination in dataset.");
                else System.out.print(printOutput);
            }

        } while (!userValueName.equalsIgnoreCase("q"));

        userInput.close();

    }

    /**
     * Splits the given line of a CSV file according to commas and double quotes
     * (double quotes are used to surround multi-word entries so that they may contain commas)
     * @author Joanna Klukowska
     * @param textLine	a line of text to be passed
     * @return an Arraylist object containing all individual entries found on that line
     */
    public static ArrayList<String> splitCSVLine(String textLine){

        ArrayList<String> entries = new ArrayList<String>();
        int lineLength = textLine.length();
        StringBuffer nextWord = new StringBuffer();
        char nextChar;
        boolean insideQuotes = false;
        boolean insideEntry= false;

        // iterate over all characters in the textLine
        for (int i = 0; i < lineLength; i++) {
            nextChar = textLine.charAt(i);

            // handle smart quotes as well as regular quotes
            if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') {

                // change insideQuotes flag when nextChar is a quote
                if (insideQuotes) {
                    insideQuotes = false;
                    insideEntry = false;
                }else {
                    insideQuotes = true;
                    insideEntry = true;
                }
            } else if (Character.isWhitespace(nextChar)) {
                if ( insideQuotes || insideEntry ) {
                    // add it to the current entry
                    nextWord.append( nextChar );
                }else { // skip all spaces between entries
                    continue;
                }
            } else if ( nextChar == ',') {
                if (insideQuotes){ // comma inside an entry
                    nextWord.append(nextChar);
                } else { // end of entry found
                    insideEntry = false;
                    entries.add(nextWord.toString());
                    nextWord = new StringBuffer();
                }
            } else {
                // add all other characters to the nextWord
                nextWord.append(nextChar);
                insideEntry = true;
            }

        }
        // add the last word ( assuming not empty )
        // trim the white space before adding to the list
        if (!nextWord.toString().equals("")) {
            entries.add(nextWord.toString().trim());
        }

        return entries;
    }


}
