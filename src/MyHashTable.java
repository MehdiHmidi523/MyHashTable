/**
 * Created by Mehdi on 15/10/2018 for the MyHashTable project.
 */
public class MyHashTable<AnyTyp> {
    private AnyType[] nodes;
    private double loadFactor;
    private int limit, currentSize;

    /**
     * Hash implementation that uses the quadratic probing to deal with collision,
     */
    public MyHashTable() {

    }

    public MyHashTable(int limit) {
        nodes = null;
        currentSize = limit;
        resize(limit);
    }

    public void insert(AnyType element) {
        if (element == null)
            throw new NullPointerException("element is a null reference");

    }

    public void delete(AnyType element) {

    }

    public boolean contains(AnyType element) {
        return nodes[findPosition(element)] != null;
    }

    private void rehash() {
        //when either the load factor is higher than a limit passed as an argument in the constructor of MyHashTable
        // or when an insertion fails like when an insertion does not find an empty cell).
    }

    private void resize(int size) {

    }

    private void rehash(int size) {

    }

    private int hash(AnyType element) {
        int pos = Math.abs(element.hashCode());
        return pos % getTableSize();
    }

    /**
     * Compute the position of the obj
     * using quadratic probing.
     */
    private int findPosition(AnyType element) {
        if (element == null)
            throw new NullPointerException("Obj is a null reference object");
        return 0;
    }

    public int size() {
        return currentSize;
    }

    private int getTableSize() {
        return nodes.length;
    }


}
