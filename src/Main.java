/**
 * The GoogleSearch Program implements an application that
 *  - Search for a keyword using webcrawler
 *  - Display the top 30 websites with randomly score generation.
 *  - Implement Red Black Tree to manipulate the data.
 *
 * @author  Trinh Nguyen
 * @class   CS146-07
 * @Programming_Assignment_3
 * @version 1.0
 * @date    2018-30-28
 */


/**
 * This is the main method which call function Searching, which
 * Searching() method perform all the tasks of this assignment.
 * @param args - Unused
 * @time_complexity O(lgn)
 */

public class Main {
    public static void main(String[] args) throws Exception {
        GoogleSearch google = new GoogleSearch();
        google.Searching(); //O(n^2)
    }
}