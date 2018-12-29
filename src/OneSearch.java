import java.util.ArrayList;
/**
 * OneSearch class
 *  - stores the data of one search (search keyword, list of websites
 * counter (number of occurrence of keyword), and RBT tree (BinarySearchTree))
 *  - has constructor, setters and getters to access the private variables,
 * and print the value of the object.
 *
 */

public class OneSearch {
    private String keyword;
    private ArrayList<URL> list = new ArrayList<URL>();
    private int counter; //Counting number of search for the same keyword
    private RBT tree = new RBT();

    public OneSearch(String keyword, ArrayList<URL> list, RBT tree, int counter) {
        this.keyword = keyword;
        this.list = list;
        this.counter = counter;
        this.tree = tree;
    }

    public RBT getTree() { return tree; }

    public void setTree(RBT tree) { this.tree = tree; }

    /** Default Constructor */
    public OneSearch(){ }

    /** Setters */
    public void setCounter(int counter) { this.counter = counter; }

    public void setKeyword(String keyword) { this.keyword = keyword; }

    public void setList(ArrayList<URL> list) { this.list = list; }

    /** Getters */
    public String getKeyword() { return keyword; }

    public ArrayList<URL> getList() { return list; }

    public int getCounter() { return counter; }

    public void incrementCounter(){ counter++; }


}