import java.util.Random;

/**
 * Created by Mehdi on 16/10/2018 for the MyItinerary project.
 */
public class MyItinerary implements A2Itinerary<A2Direction>{

    private int width=0,height=0;
    private Point head;
    private int intersection = 0;

    /* The hint provided in the ex, suggests using the previous hash implementation to find cycles on a path.
     * Keeping a set of all the nodes have seen so far and testing to see if the next node is in that set would be
     * a perfectly correct solution. It would run fast as well.
     * However it would use enough extra space to make a copy of the linked list.
     * Allocating that much memory is prohibitively expensive for large lists. // Inefficient solution
     * Efficient solution would use either Brent's Or Floyd's algorithm
     */

    public MyItinerary() { }
    public int widthOfItinerary() { return Math.abs(width); }
    public int heightOfItinerary() { return Math.abs(height); }
    public A2Direction[] rotateRight() {
        String str= "the new path will take these Steps: ";
        // TODO: next point is affected not the origin;  start from origin until you reach last.next points to null.
        if (head.getMove() == A2Direction.UP)
           str+="RIGHT.";
        else if (head.getMove() == A2Direction.RIGHT)
            str+="DOWN.";
        else if (head.getMove() == A2Direction.LEFT)
            str+="UP.";
        else str+="LEFT.";

        return new A2Direction[0];
    }

    public int[] getIntersections() {

    }

    private boolean isLoopPresent(Point startNode){
        Point slowPointer = startNode; // Tortoise is at starting location.
        Point fastPointer = startNode; // Hare is at starting location.
        while(fastPointer!=null && fastPointer.getToNext()!=null){ // If ptr2 encounters NULL, it means there is no Loop in Linked list.
            slowPointer = slowPointer.getToNext(); // ptr1 moving one node at at time
            fastPointer = fastPointer.getToNext().getToNext(); // ptr2 moving two nodes at at time
            if(slowPointer==fastPointer) // if ptr1 and ptr2 meets, it means linked list contains loop.
                return true;
        }
        return false;
    }

    public void makeMove() {
        Random rn = new Random();
        int pickMove = rn.nextInt(A2Direction.values().length);
        head.setMove(A2Direction.values()[pickMove]);
        Point move = new Point(0, 0);
        if (head.getMove() == A2Direction.UP){
            move.setY(head.getY() + 1);
            height++;
        }
        else if (head.getMove() == A2Direction.DOWN){
            move.setY(head.getY() - 1);
            height--;
        }
        else if (head.getMove() == A2Direction.RIGHT){
            move.setX(head.getX() + 1);
            width++;
        }
        else{
            move.setX(head.getX() - 1);
            width--;
        }
        move.setNext(head);// Make next of new Node as head
        head = move;// Move the head to point to new Node
        if(isLoopPresent(head)){
            intersection++;
        }
    }

    private class Point {
        private Point toNext = null;
        private A2Direction myDirection = null;
        private int x;
        private int y;
        public Point(int x1, int y1) {
            this.x = x1;
            this.y = y1;
        }
        public A2Direction getMove() { return myDirection; }
        public Point getToNext() { return toNext; }
        public void setNext(Point target) { toNext = target; }
        public void setMove(A2Direction action) { myDirection = action; }
        public int getX() { return x; }
        public int getY() { return y; }
        public void setX(int x) { this.x = x; }
        public void setY(int y) { this.y = y; }
    }
}
