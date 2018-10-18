import java.util.Arrays;
import java.util.Random;

/**
 * Created by Mehdi on 16/10/2018 for the MyItinerary project.
 */
public class MyItinerary implements A2Itinerary<A2Direction>{
    private int width=0,height=0,intersects=0,arrows=0;
    private Point head; // only moves forward.
    private int[] intersection = new int[100];
    private MyHashTable<Point> collider = new MyHashTable<>(300);
    private A2Direction[] IgotTheMoves = new A2Direction[1000];

    public static void main(String[] args){
        MyItinerary simulate= new MyItinerary();
        for(int i=0;i<20;i++)
            simulate.makeMove();
        System.out.println(Arrays.toString(simulate.getIntersections()));
        System.out.println(simulate.widthOfItinerary());
        System.out.println(simulate.heightOfItinerary());

    }

    /* The hint provided in the ex, suggests using the previous hash implementation to find cycles on a path.
     * Keeping a set of all the nodes that have seen so far and testing to see if the next node is in that set would be
     * a perfectly correct solution. It would run fast as well.
     * However it would use enough extra space to make a copy of the linked list.
     * Allocating that much memory is prohibitively expensive for large lists. // Inefficient solution
     * Efficient solution would use either Brent's Or Floyd's algorithm and intuitively keep track of intersections.
     */

    public MyItinerary() {
        head = new Point(0,0);
        collider.insert(head);
    }

    public int widthOfItinerary() { return Math.abs(width); }
    public int heightOfItinerary() { return Math.abs(height); }
    // TODO: next point is affected not the origin;  start from origin until you reach last.next points to null
    public A2Direction[] rotateRight() {
        String str= "the new path will take these Steps: ";
        /*if (head.getMove() == A2Direction.UP)
           str+="RIGHT.";
        else if (head.getMove() == A2Direction.RIGHT)
            str+="DOWN.";
        else if (head.getMove() == A2Direction.LEFT)
            str+="UP.";
        else str+="LEFT.";
*/
        return new A2Direction[0];
    }
    public int[] getIntersections() {
        return intersection;
    }

    /*Simulates a random path and keeps track of directions and intersections.*/
    public void makeMove() {
        Random rn = new Random();
        int pickMove = rn.nextInt(A2Direction.values().length);
        Point move = new Point(0, 0);
        IgotTheMoves[arrows++]=A2Direction.values()[pickMove]; // ==> for the rotate right method.

        if (IgotTheMoves[arrows-1]== A2Direction.UP){
            move.setY(head.getY() + 1);
            move.setX(head.getX());
            height++;
        }
        else if (IgotTheMoves[arrows-1] == A2Direction.DOWN){
            move.setY(head.getY() - 1);
            move.setX(head.getX());
            height--;
        }
        else if (IgotTheMoves[arrows-1] == A2Direction.RIGHT){
            move.setX(head.getX() + 1);
            move.setY(head.getY());
            width++;
        }
        else{
            move.setX(head.getX() - 1);
            move.setY(head.getY());
            width--;
        }

        if (!collider.contains(move)) collider.insert(move); // ==>  keeps a list of intersections
        else intersection[intersects++]= arrows;
        head = move;
    }

    private class Point {
        int x, y;
        Point(int x1, int y1) {
            this.x = x1;
            this.y = y1;
        }

        int getX() { return x; }
        int getY() { return y; }
        void setX(int x) { this.x = x; }
        void setY(int y) { this.y = y; }
    }
}
