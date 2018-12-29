/**
 * URL class
 *  - stores the data of a website (Title, URL, domainName, PageRank object, color (for RBT)
 *  and left, right, parent node for implementing BST)
 *  - has constructor, setters and getters to access the private variables,
 * and print the value of the object.
 */

public class URL {

    private String title;
    private String url;
    private String domainName;
    private int color; //
    private PageRank score;
    private URL left, right, parent;

    /** Constructor */
    public URL(){
        this(1);
    }

    public URL(int color){
        PageRank newScore = new PageRank();
        if (color == 1 ) newScore.generateScore();
        this.score = newScore;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = color;
    }

    public URL(String title, String url, String domainName, int index){
        this.title = title;
        this.url = url;
        this.domainName = domainName;
        PageRank newScore = new PageRank();
        newScore.generateScore();
        newScore.setIndex(index);
        this.score = newScore;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = 1;
    }

    /** Setters */
    public void setTitle(String title) { this.title = title; }

    public void setUrl(String url) { this.url = url; }

    public void setDomainName(String domainName) { this.domainName = domainName; }

    public void setScores(PageRank score) { this.score = score; }

    public void setOneScore(int index, int value) { this.score.setOneScore(index, value); }

    public void setTotalScore(int total) { this.score.setTotalScore(total); }

    public void setPageRank(int pageRank) { this.score.setPageRank(pageRank); }

    public void setLeft(URL left) { this.left = left; }

    public void setRight(URL right) { this.right = right; }

    public void setParent(URL parent){ this.parent = parent; }

    public void setColor(int color) { this.color = color; }

    /** Getters */
    public String getTitle() { return title; }

    public String getUrl() { return url; }

    public String getDomainName() { return domainName; }

    public PageRank getScores() { return score; }

    public int getTotalScore() { return score.getTotalScore(); }

    public int getPageRank() { return score.getPageRank();}

    public URL getLeft() { return left; }

    public URL getRight() { return right; }

    public URL getParent() { return parent; }

    public int getColor() { return color; }


    /**
     * buildPageRank() method set pagerank
     * @param rank - rank of the object
     * @time_complexity O(1)
     */
    public void buildPageRank(int rank){
        getScores().setPageRank(rank);
    }

    public String printColor(){
        if (color==1) return "RED";
        else if (color==0) return "BLACK";
        else return "Null";
    }
    /**
     * printOne() method is used StringBuffer to display Website title, url,
     * pagerank, index, each factors, and totalscore
     * @time_complexity O(n)
     */
    public void printOne(){
        StringBuffer buff = new StringBuffer();
        buff.append("\nTitle: ").append(title);
        buff.append("\nURL: ").append(url);//.append("\n");
        buff.append("\nColor: ").append(printColor());
        buff.append("\nPage Rank: ").append(getScores().getPageRank());
        buff.append(" - Index: ").append(getScores().getIndex());
        System.out.print(buff);
        score.print(); //O(n)
    }

    /**
     * printOne() method is used StringBuffer to display Website title,
     * url, domain name, pagerank, and index
     * @time_complexity O(n)
     */
    public void print_Domain(){
        StringBuffer buff = new StringBuffer();
        buff.append("\nTitle: ").append(title);
        buff.append("\nDomain name: ").append(domainName);
        buff.append(" - URL: ").append(url);
        buff.append("\nColor: ").append(printColor());
        buff.append("\nPage Rank: ").append(getScores().getPageRank());
        buff.append(" - Index: ").append(getScores().getIndex());
        System.out.print(buff);
        score.print(); //O(n)
    }

    /**
     * printOne() method is used StringBuffer to display Website title, url
     * pagerank, index, and domain name
     * @time_complexity O(n)
     */
    public void print_New(){
        StringBuffer buff = new StringBuffer();
        buff.append("Title: ").append(title);
        buff.append("\nDomain name: ").append(domainName);
        buff.append("\nURL: ").append(url);
        buff.append("\nColor: ").append(printColor());
        buff.append("\nPage Rank: ").append(getScores().getPageRank());
        buff.append(" - Index: ").append(getScores().getIndex());
        System.out.print(buff);
        score.print(); //O(n)
    }
}
