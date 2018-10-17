import java.util.Random;

/**
 * Created by Mehdi on 16/10/2018 for the MyItinerary project.
 */
public class MyItinerary {

    private int width=0,height=0;
    private Point head;
    private enum Moves {UP, DOWN, LEFT, RIGHT} //to calculate the new rotate right coordinates.

    /*The hint provided in the ex, suggests using the previous hash implementation to find cycles on a path.
     * Keeping a set of all the nodes have seen so far and testing to see if the next node is in that set would be
     * a perfectly correct solution. It would run fast as well.
     * However it would use enough extra space to make a copy of the linked list.
     * Allocating that much memory is prohibitively expensive for large lists. // Inefficient solution
     * Efficient solution would use either Brent's Or Floyd's algorithm
     */

    public MyItinerary() {
    }
    public int widthOfItinerary() { return Math.abs(width); }
    public int heightOfItinerary() { return Math.abs(height); }

    public void rotateRight() {
    // TODO: next point is affected not the origin;  start from origin until you reach last.next points to null.
        if (head.getMove() == Moves.UP)
            head.setMove(Moves.RIGHT);
        else if (head.getMove() == Moves.RIGHT)
            head.setMove(Moves.DOWN);
        else if (head.getMove() == Moves.LEFT)
            head.setMove(Moves.UP);
        else head.setMove(Moves.LEFT);

    }

    /*
     * Searching for the smallest power of two 2i that is larger than both λ and μ. For i = 0, 1, 2, ...,
     * the algorithm compares X__2i−1 with each subsequent sequence value up to the next power of two,
     * stopping when it finds a match. It has two advantages compared to the tortoise and hare algorithm:
     * it finds the correct length λ of the cycle directly, rather than needing to search for it in a subsequent stage,
     * and its steps involve only one evaluation of f rather than three
     */
    public String getIntersections() {

    }

    private void makeMove() {
        Random rn = new Random();
        int pickMove = rn.nextInt(Moves.values().length);
        head.setMove(Moves.values()[pickMove]);
        Point move = new Point(50, 50);
        if (head.getMove() == Moves.UP){
            move.setY(head.getY() + 1);
            height++;
        }
        else if (head.getMove() == Moves.DOWN){
            move.setY(head.getY() - 1);
            height--;
        }
        else if (head.getMove() == Moves.RIGHT){
            move.setX(head.getX() + 1);
            width++;
        }
        else{
            move.setX(head.getX() - 1);
            width--;
        }
        /* Make next of new Node as head */
        move.setNext(head);
        /* Move the head to point to new Node */
        head = move;
    }

    private class Point {
        private Point toNext = null;
        private Moves myDirection = null;
        private int x;
        private int y;
        public Point(int x1, int y1) {
            this.x = x1;
            this.y = y1;
        }
        public Moves getMove() { return myDirection; }
        public Point getToNext() { return toNext; }
        public void setNext(Point target) { toNext = target; }
        public void setMove(Moves action) { myDirection = action; }
        public int getX() { return x; }
        public int getY() { return y; }
        public void setX(int x) { this.x = x; }
        public void setY(int y) { this.y = y; }
    }
}
