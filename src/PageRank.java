import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;

/**
 * PageRank class
 *  - contains MINSCORE, MAXSCORE, array of 4 scores (4 factor scores)
 * total score that will be used for each website, index, and pageRank
 *  - has constructor, setters and getters to access the private variables,
 *  - generate scores randomly, compute total score, and print all scores
 */

public class PageRank {
    //the Score for each factor range is 0-25
    private static int MAXSCORE = 25;
    private static int MINSCORE = 0;
    private static int NUM_FACTOR = 4;
    private int [] scores = new int[NUM_FACTOR];
    private int totalScore;
    private int index = 0; //insertion order
    private int pageRank;  //pagerank 1 - highest score

    /** Constructor */
    public PageRank() { }

    /** Setters */
    public void setOneScore(int index, int value) { this.scores[index] = value; }

    public void setScore(int[] scores) { this.scores = scores; }

    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }

    public void setIndex(int index) { this.index = index; }

    public void setPageRank(int pageRank) { this.pageRank = pageRank; }

    /** Getters */
    public static int getMAXSCORE() { return MAXSCORE; }

    public static int getMINSCORE() { return MINSCORE; }

    public int getOneScore(int index) { return scores[index]; }
    
    public int[] getAllScores() { return scores; }

    public int getTotalScore() { return totalScore; }

    public int getIndex() { return index; }

    public int getPageRank() { return pageRank; }

    /**
     * calTotalScore() method compute the totalScore by adding all 4 scores.
     * @return none
     * @time_complexity O(1)
     */
    public void calTotalScore() {
        this.totalScore = scores[0]+scores[1]+scores[2]+scores[3]; 
    }

    /**
     * generateScore() method is used to randomly generate 4 scores
     * from 0-2 for 4 scores in scores array and compute the
     * totalScore by adding all 4 scores.
     * @return none
     * @time_complexity O(n)
     */
    public void generateScore(){
        for (int i = 0; i < NUM_FACTOR; i++) {
            setOneScore(i, (int)(MINSCORE+Math.random()*(MAXSCORE)));
//            setTotalScore(i+1);
        }
        calTotalScore();
    }

    /**
     * print() method is used StringBuffer to display the score of 4 factors
     * and the total score.
     * @return none
     * @time_complexity O(n)
     */
    public void print(){
        StringBuffer buff = new StringBuffer();
//        buff.append("\n - ");
//        for (int i = 0; i < NUM_FACTOR; i++)
//        {
//            buff.append("Factor "+ (i+1)).append(": ").append(scores[i]).append(" - ");
//        }
        buff.append(" - Total Score: ").append(totalScore).append("\n");
        System.out.print(buff);
    }
}
