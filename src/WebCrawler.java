/**
 * WebCrawler class
 *  - Search for a keyword using webcrawler
 *  - Store data in OneSearch object
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;


public class WebCrawler{

    public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
    /**
     * Searching() method perform search with keyword input and store
     * and display top 30 websites into OneSearch object
     * @return OneSearch object
     * @exception URISyntaxException, IOException
     * @time_complexity O(lgn)
     */
    public OneSearch WebCrawler(Scanner scanner) throws URISyntaxException, IOException {
        int numResult = 40;

        ArrayList<URL> resultList = new ArrayList<URL>();
        RBT tree = new RBT();
        resultList.add(0, null);

        System.out.print("Please enter the search term: ");
        String searchKeyword = "";
        if (scanner.hasNextLine())
            searchKeyword = scanner.nextLine();

        String searchURL = GOOGLE_SEARCH_URL + "?q=" + searchKeyword + "&num=" + numResult;
        Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
        Elements results = doc.select("h3.r > a");

        int index = 1;
        for (Element result : results) {
            URL tempURL;
            String url = result.attr("href");
            String title = result.text();
            if (url.substring(7, 11).equals("http")) {
                url = url.substring(7, url.indexOf("&"));
                String domainName = getDomainName(url);
                tempURL = new URL(title, url, domainName, index);

                System.out.print("Index "+ index +":   "+ tempURL.getTotalScore() + "\n");

                resultList.add(index, tempURL);             //Add node to ArrayList
                tree.RBInsert(tempURL);                     //Add node to BST
                index++;
                if(index == 31) break;
            }
        }
        OneSearch oneSearch = new OneSearch(searchKeyword, resultList, tree, 1);
        tree.inorder();
        return oneSearch;
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
}

