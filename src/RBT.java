/**
 * RBT class
 * - Perform data manipulation
 * - Parameters: root of tree, one sentinel nil node and size of tree
 * - Two static final int for node color
 * - Functions: insert, delete, search, right/left rotate, minimum, inorder
 * /

/**
 * Properties of Red-Black Tree
 * 1. Every node is either red or black.
 * 2. The root is black.
 * 3. Every leaf (NIL) is black.
 * 4. If a node is red, then both its children are black.
 * 5. For each node, all simple paths from the node to descendant leaves contain the same number of black nodes.
 */
public class RBT{
    private static final int RED   = 1;
    private static final int BLACK = 0;
    private URL nil;
    private URL root;
    private int size;

    /** Constructor */
    RBT(){
        nil = new URL(BLACK);
        root = nil;
    }

    /** Setters */

    public void setSize(int size) { this.size = size; }

    public void setRoot(URL root) { this.root = root; }

    public URL getNil(){ return nil;}

    /** Getters */

    public URL getRoot() { return root; }

    public int getSize() { return size; }

    /**
     * leftRotate
     * @param x - left rotate on x
     * @time_complexity O(1)
     */
    public void leftRotate(URL x){
        URL y = x.getRight();                   //set y // Assume x.right != null
        x.setRight(y.getLeft());                //turn y's left subtree into x's right subtree
        if (y.getLeft() != nil)
            y.getLeft().setParent(x);
        y.setParent(x.getParent());             //link x's parent to y
        if (x.getParent() == nil)              //if x == root
            setRoot(y);
        else if (x == x.getParent().getLeft()) //if x is lef
               x.getParent().setLeft(y);
        else x.getParent().setRight(y);
        y.setLeft(x);                           //put x on y's left
        x.setParent(y);
    }
    /**
     * rightRotate
     * @param y - right rotate on y
     * @time_complexity O(1)
     */
    public void rightRotate(URL y){
        URL x = y.getLeft();
        y.setLeft(x.getRight());
        if (x.getRight() != nil)
            x.getRight().setParent(y);
        x.setParent(y.getParent());
        if (y.getParent() == nil)
            setRoot(x);
        else if (y == y.getParent().getRight())
            y.getParent().setRight(x);
        else y.getParent().setLeft(x);

        x.setRight(y);
        y.setParent(x);
    }
    /**
     * RBInsert
     * @param z - insert z to Red-Black Tree
     * @time_complexity O(lgn)
     */
    public void RBInsert(URL z){
        URL y = nil;
        URL x = root;
        while (x != nil){
            y = x;
            if (z.getTotalScore() < x.getTotalScore())
                x = x.getLeft();
            else x = x.getRight();
        }
        z.setParent(y);
        if (y == nil)
            setRoot(z);
        else if (z.getTotalScore() < y.getTotalScore())
            y.setLeft(z);
        else y.setRight(z);
        z.setLeft(nil);
        z.setRight(nil);
        z.setColor(RED);
        RBInsertFixup(z);
        /** Set Page Rank after insertion */
        size++;
        int tempSize = size;
        setPR(root);
        size = tempSize;
    }

    /**
     * RBInsertFixup restores the red-black properties after insertion
     * @param z -
     * @time_complexity O(1)
     */
    public void RBInsertFixup(URL z){
        while (z.getParent().getColor() == RED){    //z.p = Black - does not violate
            if (z.getParent() == z.getParent().getParent().getLeft()){  //z.p is the left subtree
                URL y = z.getParent().getParent().getRight();           // y = z's uncle
                if (y.getColor() == RED){                               //Case 1
                    z.getParent().setColor(BLACK);                      //z's parents and uncle -> black
                    y.setColor(BLACK);
                    z.getParent().getParent().setColor(RED);            //z's grandpa to red - if root change to black at the end
                    z = z.getParent().getParent();                      //move pointer
                } else {
                    if (z == z.getParent().getRight()){                 //Case 2 - z is right child
                        z = z.getParent();
                        leftRotate(z);
                    }
                    z.getParent().setColor(BLACK);                      //Case 3
                    z.getParent().getParent().setColor(RED);
                    rightRotate(z.getParent().getParent());
                }

            } else if (z.getParent() == z.getParent().getParent().getRight()){                                                    //z.p is the right subtree
                URL y = z.getParent().getParent().getLeft();
                if (y.getColor() == RED){                               //Case 1
                    z.getParent().setColor(BLACK);
                    y.setColor(BLACK);
                    z.getParent().getParent().setColor(RED);
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getLeft()){                 //Case 2
                        z = z.getParent();
                        rightRotate(z);
                    }
                    z.getParent().setColor(BLACK);                      //Case 3
                    z.getParent().getParent().setColor(RED);
                    leftRotate(z.getParent().getParent());
                }
            }

        }
        root.setColor(BLACK);
    }
    /**
     * treeSearch method search for a node given pageRan
     * @param node - root of the tree
     * @param key_rank - key of the node searching for
     * @return node - if found, null - if not found
     * @time_complexity O(lgn)
     */
    private URL treeSearchPageRank(URL node, int key_rank){ //Pass in tree root
        if (node == null || key_rank == node.getPageRank())
            return node;
        if (key_rank > node.getPageRank())
            return treeSearchPageRank(node.getLeft(), key_rank);
        else return treeSearchPageRank(node.getRight(), key_rank);
    }

    /**
     * treeSearch method search for a node given total score
     * @param node - root of the tree
     * @param score - key of the node searching for
     * @return node - if found, null - if not found
     * @time_complexity O(lgn)
     */
    private URL treeSearchTotalScore(URL node, int score){ //Pass in tree root
        if (node == null || score == node.getTotalScore())
            return node;
        if (score < node.getTotalScore())
            return treeSearchTotalScore(node.getLeft(), score);
        else return treeSearchTotalScore(node.getRight(), score);
    }

    /**
     * RBSearch method calls treeSearch for total score or pagerank
     * @param key - key of the node searching for
     * @return node - if found, null - if not found
     * @time_complexity O(lgn)
     */

    public URL RBSearch(int key, String action){
        if (action == "P") return treeSearchPageRank(root, key);
        else if (action == "S") return treeSearchTotalScore(root, key);
        else return null;
    }

    public void RBTransplant(URL u, URL v){
        if (u.getParent() == nil)
            root = v;
        else if (u == u.getParent().getLeft())
            u.getParent().setLeft(v);
        else u.getParent().setRight(v);
        v.setParent(u.getParent());
    }

    /**
     * RBDelete delete a node in a RB Tree
     * @param z - delete node z
     * @time_complexity O(lgn)
     */
    public void RBDelete(URL z){
        URL y = z; URL x;
        int y_originalColor = y.getColor();
        if (z.getLeft() == nil){
            x = z.getRight();
            RBTransplant(z, z.getRight());
        }
        else if (z.getRight() == nil){
            x = z.getLeft();
            RBTransplant(z, z.getLeft());
        }
        else{
            y = treeMinimum(z.getRight());
            y_originalColor = y.getColor();
            x = y.getRight();
            if (y.getParent() == z)
                x.setParent(y);
            else {
                RBTransplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
            RBTransplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            y.setColor(z.getColor());
        }
        if (y_originalColor == BLACK)
            RBDeleteFixup(x);
        /** Set Page Rank after insertion */
        size--;
        int tempSize = size;
        setPR(root);
        size = tempSize;
    }

    /**
     * RBDeleteFixup restores the red-black properties after deletion
     * @param x - node deleted
     * @time_complexity O(lgn)
     */
    public void RBDeleteFixup(URL x){
        URL w;
        while (x != root && x.getColor() == BLACK){
            if (x == x.getParent().getLeft()) {                                             //x is left child
                w = x.getParent().getRight();
                if (w.getColor() == RED) {                                                  //Case 1
                    w.setColor(BLACK);
                    x.getParent().setColor(RED);
                    leftRotate(x.getParent());
                    w = x.getParent().getRight();
                }
                if (w.getLeft().getColor() == BLACK && w.getRight().getColor() == BLACK) {
                    w.setColor(RED);                                                        //Case 2
                    x = x.getParent();
                } else {
                    if (w.getRight().getColor() == BLACK) {
                        w.getLeft().setColor(BLACK);                                        //Case 3
                        w.setColor(RED);
                        rightRotate(w);
                        w = x.getParent().getRight();
                    }
                    w.setColor(x.getParent().getColor());                                   //Case 4
                    x.getParent().setColor(BLACK);
                    w.getRight().setColor(BLACK);
                    leftRotate(x.getParent());
                    x = root;
                }
            }
            else if (x == x.getParent().getRight()) {                                       //x is right child
                w = x.getParent().getLeft();
                if (w.getColor() == RED) {                                                  //Case 1
                    w.setColor(BLACK);
                    x.getParent().setColor(RED);
                    rightRotate(x.getParent());
                    w = x.getParent().getLeft();
                }
                if (w.getRight().getColor() == BLACK && w.getLeft().getColor() == BLACK) {
                    w.setColor(RED);                                                        //Case 2
                    x = x.getParent();
                } else {
                    if (w.getLeft().getColor() == BLACK) {
                        w.getRight().setColor(BLACK);                                       //Case 3
                        w.setColor(RED);
                        leftRotate(w);
                        w = x.getParent().getLeft();
                    }
                    w.setColor(x.getParent().getColor());                                   //Case 4
                    x.getParent().setColor(BLACK);
                    w.getLeft().setColor(BLACK);
                    rightRotate(x.getParent());
                    x = root;
                }
            }
        }
        x.setColor(BLACK);
    }


    /**
     * setPR method set PageRank through traversal
     * @param node - root of the tree
     * @time_complexity O(n)
     */
    public void setPR(URL node){
        if (node != nil)
        {
            setPR(node.getLeft());
            node.setPageRank(size);
            size--;
            setPR(node.getRight());
        }
    }

    /**
     * inorderTreeWalk method traverse the tree
     * @param node - root of the tree
     * @time_complexity O(n)
     */
    public void inorderTreeWalk(URL node){
        if (node != nil)
        {
            inorderTreeWalk(node.getLeft());
            node.printOne();
            inorderTreeWalk(node.getRight());
        }
    }

    /**
     * inorder method calls inorderTreeWalk method
     * @time_complexity O(n)
     */
    public void inorder(){ inorderTreeWalk(root); }

    /**
     * preorderTreeWalk method traverse the tree
     * @param node - root of the tree
     * @time_complexity O(n)
     */
    public void preorderTreeWalk(URL node){
        if (node != nil)
        {
            node.printOne();
            preorderTreeWalk(node.getLeft());
            preorderTreeWalk(node.getRight());
        }
    }

    /**
     * preorder method calls preorderTreeWalk method
     * @time_complexity O(n)
     */
    public void preorder(){ preorderTreeWalk(root); }

    /**
     * postorderTreeWalk method traverse the tree
     * @param node - root of the tree
     * @time_complexity O(n)
     */
    public void postorderTreeWalk(URL node){
        if (node != nil)
        {
            postorderTreeWalk(node.getLeft());
            postorderTreeWalk(node.getRight());
            node.printOne();
        }
    }

    /**
     * postorder method calls postorderTreeWalk method
     * @time_complexity O(n)
     */
    public void postorder(){ postorderTreeWalk(root); }


    /**
     * treeMinimum method find the node with minimum total score
     * @param node - root of the tree
     * @return node with minimum total score
     * @time_complexity O(h)
     */
    public URL treeMinimum(URL node){
        while (node.getLeft()!= nil)
            node = node.getLeft();
        return node;
    }
}
