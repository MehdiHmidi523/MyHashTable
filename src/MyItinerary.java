import java.util.Arrays;
import java.util.Random;

/**
 * Created by Mehdi on 16/10/2018 for the MyItinerary project.
 */
public class MyItinerary implements A2Itinerary<A2Direction> {
    private int miW = 0, miH = 0, maW = 0, maH = 0, width = 0, height = 0, intersects = 0, arrows = 0;
    private Point head;
    private Point[][] grid = new Point[100][100];
    private int[] intersection = new int[50];
    private A2Direction[] theMoves = new A2Direction[50];
    MyHashTable<Point> collide = new MyHashTable<>();

    public static void main(String[] args) {
        A2Direction[] array = new A2Direction[10];
        array[0] = A2Direction.LEFT;
        array[1] = A2Direction.DOWN;
        array[2] = A2Direction.RIGHT;
        array[3] = A2Direction.DOWN;
        array[4] = A2Direction.LEFT;
        array[5] = A2Direction.UP;
        array[6] = A2Direction.LEFT;
        array[7] = A2Direction.UP;
        array[8] = A2Direction.RIGHT;
        array[9] = A2Direction.UP;

        MyItinerary simulate = new MyItinerary();
        simulate.moves(array);
        System.out.println(simulate.toString());
        System.out.println("Rotate right results in: " + Arrays.toString(simulate.rotateRight()));
        System.out.println("Intersections:" + Arrays.toString(simulate.getIntersections()) + " !");
        System.out.println("Width is:" + simulate.widthOfItinerary());
        System.out.println("Height is:" + simulate.heightOfItinerary());
    }

    MyItinerary() {
        head = new Point(grid.length / 2, grid.length / 2);
    }

    public int widthOfItinerary() {
        return Math.abs(miW) + maW;
    }

    public int heightOfItinerary() {
        return Math.abs(miH) + maH;
    }

    public A2Direction[] rotateRight() {
        A2Direction[] rotation = new A2Direction[arrows];
        for (int i = 0; i < arrows; i++) {
            if (theMoves[i] == A2Direction.UP)
                rotation[i] = A2Direction.RIGHT;
            else if (theMoves[i] == A2Direction.RIGHT)
                rotation[i] = A2Direction.DOWN;
            else if (theMoves[i] == A2Direction.LEFT)
                rotation[i] = A2Direction.UP;
            else rotation[i] = A2Direction.LEFT;
        }
        return rotation;
    }

    public int[] getIntersections() {
        int[] displayIntersects = new int[intersects];
        System.arraycopy(intersection, 0, displayIntersects, 0, intersects);
        return displayIntersects;
    }

    private void moves(A2Direction[] example) {
        for (A2Direction anExample : example) {
            theMoves[arrows++] = anExample;
            Point move = new Point(0, 0);
            pointManipulation(move);
            move.next = head;
            head = move;
            if(collide.contains(head))
                intersection[intersects++]=head.getPos();
            collide.insert(head);
        }
    }

    public String toString() {
        StringBuilder linkedList = new StringBuilder();
        Point tmp = head;
        while (tmp.next != null) {
            linkedList.append("(").append(tmp.getX()).append(",").append(tmp.getY()).append(") . <---");
            tmp = tmp.next;
        }
        linkedList.append("Head is this point");
        return linkedList.toString();
    }

    /*Simulates a random path and keeps track of directions and intersections.*/
    private void randomMove() {
        Random rn = new Random();
        int pickMove = rn.nextInt(A2Direction.values().length);
        Point move = new Point(0, 0);
        theMoves[arrows++] = A2Direction.values()[pickMove]; // ==> for the rotate right method.
        pointManipulation(move);
        move.next = head;
        head = move;
    }


    private int init(int i) {
        if (i > 0) {
            grid = new Point[2 * i][2 * i];
            intersection = new int[i];
            theMoves = new A2Direction[i];
            return i;
        } else return 20; //for simplicity's sake.
    }

    private void pointManipulation(Point move) {
        if (theMoves[arrows - 1] == A2Direction.UP) {
            move.setY(head.getY() + 1);
            move.setX(head.getX());
            height++;
        } else if (theMoves[arrows - 1] == A2Direction.DOWN) {
            move.setY(head.getY() - 1);
            move.setX(head.getX());
            height--;
        } else if (theMoves[arrows - 1] == A2Direction.RIGHT) {
            move.setX(head.getX() + 1);
            move.setY(head.getY());
            width++;
        } else {
            move.setX(head.getX() - 1);
            move.setY(head.getY());
            width--;
        }
        if (height < miH) miH = height;
        if (height > maH) maH = height;
        if (width < miW) miW = width;
        if (width > maW) maW = width;
        move.setPos(arrows - 1);
    }

}
