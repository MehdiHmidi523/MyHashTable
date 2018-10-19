/**
 * Created by Mehdi on 19/10/2018 for the MyHashTable project.
 */
public class Point {

    public Point next;
    private int x, y, pos;

    public Point(int x1, int y1) {
        this.x = x1;
        this.y = y1;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    int getPos() {
        return pos;
    }

    void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Point && ((Point) o).x == this.x && ((Point) o).y == this.y;
    }

    @Override
    public int hashCode() {
        int tmp = (y + ((x + 1) / 2));
        return x + (tmp * tmp);//bijective algorithm
    }
}

