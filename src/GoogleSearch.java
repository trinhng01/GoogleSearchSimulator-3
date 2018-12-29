/**
 * OneSearch class
 *  - stores the data of all searches
 *  - constructor, setters and getters to access the private variables,
 * and print the value of the object.
 *  - Search for a keyword using webcrawler and display the top 30 websites with randomly score generation.
 *  - Implement Red Black Tree to manipulate the data.
 */

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.URI;

public class GoogleSearch {
    public static final String PAGERANK   = "P";
    public static final String TOTAL_SCORE = "S";
    private ArrayList<OneSearch> SearchList = new ArrayList<>();

    /** Default Constructor */
    public GoogleSearch() { }

    /**
     * Searching() method perform search with keyword input and display top
     * 30 websites, allow user to search, insert or delete a website, and
     * display a sorted list of domain name for the top search
     * @exception URISyntaxException, IOException
     * @time_complexity O(lgn)
     */
    public void Searching() throws IOException, URISyntaxException {
        Scanner scanner = new Scanner(System.in);
        WebCrawler jsoup = new WebCrawler();
        OneSearch oneSearch = jsoup.WebCrawler(scanner);    //Webcrawler
        Menu(scanner,oneSearch);                            //Perform data manipulation
    }

    /**
     * Menu method performs search, insert, delete
     * @param oneSearch - where the data is stored
     * @param scanner - get user input
     * @exception URISyntaxException for insertWebsite
     * @time_complexity O(lgn)
     */
    private void Menu(Scanner scanner, OneSearch oneSearch) throws URISyntaxException {
        String mess =  "\n------------------------------ MENU ------------------------------" +
                "\nOption 1: Display top 30 Websites" +
                "\nOption 2: Search a Website based on PageRank" +
                "\nOption 3: Search a Website based on Total Score" +
                "\nOption 4: Add a new Website" +
                "\nOption 5: Delete a Website based on PageRank" +
                "\nOption 6: Delete a Website based on Total Score" +
                "\nOption 7: Exit" +
                "\nEnter the option (1-4): ";
        int option;
        do{
            option = MenuOptionInputValidation(mess, scanner); //O(n)
            switch (option) {
                case 1: oneSearch.getTree().inorder(); break;
                case 2: searchTree(scanner, oneSearch, PAGERANK); break;            //PageRank
                case 3: searchTree(scanner, oneSearch, TOTAL_SCORE); break;         //Total Score
                case 4: insertWebsite(scanner, oneSearch); break;
                case 5: deleteWebsite(scanner, oneSearch, PAGERANK); break;         //PageRank
                case 6: deleteWebsite(scanner, oneSearch, TOTAL_SCORE); break;      //Total Score
                case 7: break;
                default:
                    System.out.println("Error: Input must be a positive integer (1-3).");
            }
        }while (option != 4);
    }
    /**
     * searchTree method find a website given its pageRank
     * @param oneSearch - contains the website to be searched
     * @param scanner - get user input
     * @time_complexity O(lgn)
     */
    private URL searchTree(Scanner scanner, OneSearch oneSearch, String action){
        System.out.println("\n------------------------------ SEARCH ------------------------------\n");
        URL findNode = null;
        int key;
        if (action == PAGERANK){
            key = PageRankInputValidation("Enter PageRank: ", scanner, oneSearch);
            findNode = oneSearch.getTree().RBSearch(key, action);
        }
        else if (action == TOTAL_SCORE){
            key = TotalScoreInputValidation("Enter Total Score: ", scanner, oneSearch);
            findNode = oneSearch.getTree().RBSearch(key, action);
        }
        else System.out.println("Error!");

        if (findNode != null) findNode.printOne();
        else System.out.println("Website is NOT found!");
        return findNode;
    }

    /**
     * insertWebsite method insert a new website to database
     * @param oneSearch - where to insert a new website
     * @param scanner - get user input
     * @time_complexity O(lgn)
     */
    private void insertWebsite(Scanner scanner, OneSearch oneSearch)throws URISyntaxException {
        System.out.println("\n------------------------------- ADD -------------------------------\n");
        System.out.print("Enter Title of website: ");
        String title = scanner.nextLine();
        System.out.print("Enter URL of website [https://www....]: ");
        String url = scanner.nextLine();
        String domainName = getDomainName(url);
        URL newNode = new URL(title, url, domainName, oneSearch.getList().size());
        System.out.println("WARNING: The score of your new website will be evaluated and auto-generated." +
                "\nConfirm your new Website information:");
        newNode.print_New();
        oneSearch.getTree().RBInsert(newNode);
        oneSearch.getList().add(newNode);
        System.out.println("\n\n----------------------- Updated List of URLs -----------------------");
        oneSearch.getTree().inorderTreeWalk(oneSearch.getTree().getRoot());
    }
    /**
     * getDomainName method extract the domain name from url
     * @param url
     * @return domain name
     * @exception URISyntaxException
     * @time_complexity O(1)
     */
    private static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    /**
     * deleteWebsite method delete a a website given its PageRank
     * @param oneSearch - contains the website to be deleted
     * @param scanner - get user input
     * @time_complexity O(lgn)
     */
    private void deleteWebsite(Scanner scanner, OneSearch oneSearch, String action){
        System.out.println("\n------------------------------ DELETE ------------------------------\n");
        URL findNode;
        do{
            findNode = searchTree(scanner, oneSearch, action);
        }while (findNode == null);
        oneSearch.getTree().RBDelete(findNode);
        oneSearch.getList().remove(findNode);
        oneSearch.getTree().inorder();
    }
    /**---------------- UserInputValidation Functions ----------------*/

    /**
     * PageRankInputValidation() method makes sure user enter valid integer for pageRank
     * @param message - instruction for user
     * @param scanner - get user input
     * @time_complexity O(1)
     */
    private int PageRankInputValidation(String message, Scanner scanner, OneSearch oneSearch) {
        int num;
        String errorMessage = "Error: Input must be a positive integer (1-"+oneSearch.getTree().getSize()+").";
        System.out.print(message);
        while (!scanner.hasNextInt() || (num = scanner.nextInt()) <= 0 || num > oneSearch.getTree().getSize()) {
            System.out.print(errorMessage + "\n" + message);
            scanner.nextLine();
        }
        scanner.nextLine();
        return num;
    }

    /**
     * TotalScoreInputValidation() method makes sure user enter valid integer for total score
     * @param message - instruction for user
     * @param scanner - get user input
     * @time_complexity O(1)
     */
    private int TotalScoreInputValidation(String message, Scanner scanner, OneSearch oneSearch) {
        int num;
        String errorMessage = "Error: Input must be a positive integer (0-100).";
        System.out.print(message);
        while (!scanner.hasNextInt() || (num = scanner.nextInt()) < 0 || num >100) {
            System.out.print(errorMessage + "\n" + message);
            scanner.nextLine();
        }
        scanner.nextLine();
        return num;
    }

    /**
     * UserOptionInputValidation() method makes sure user enter valid integer for menu option
     * @param message - instruction for user
     * @param scanner - get user input
     * @time_complexity O(1)
     */
    private int MenuOptionInputValidation(String message, Scanner scanner) {
        int num;
        String errorMessage = "Error: Input must be a positive integer (1-7).";
        System.out.print(message);
        while (!scanner.hasNextInt() || (num = scanner.nextInt()) <= 0 || num > 7) {
            System.out.print(errorMessage + "\n" + message);
            scanner.nextLine();
        }
        scanner.nextLine();
        return num;
    }

}

