
/**
 * Created by Mehdi on 16/10/2018 for A2.
 */
public class MyHashTable<AnyType> implements A2HashTable<AnyType>{

    private static final int DEFAULT_SIZE = 18;
    private HashEntry<AnyType>[] myTable; // The myTable of elements
    private int currentSize;     // The number of occupied cells

    public MyHashTable() {
        this(DEFAULT_SIZE);
    }

    // Allen Weiss Text Book solution with tweaks.
    public MyHashTable(int size) {
        createEntryCells(size);
        clear();
    }

    public void insert(AnyType x) {
        int currentPos = findPos(x);
        if (isAlive(currentPos))
            return;
        myTable[currentPos] = new HashEntry<>(x, true);
        if (++currentSize > myTable.length / 2)  //==> load factor 0.5
            rehash();
    }

    @Override
    public void delete(AnyType element) {
        int currentPos = findPos(element);
        if (isAlive(currentPos))
            myTable[currentPos].isAlive = false;
    }

    private void rehash() {
        HashEntry<AnyType>[] oldArr = myTable;
        createEntryCells(nextPrime(2 * oldArr.length));
        currentSize = 0;
        for (int i = 0; i < oldArr.length; i++)
            if (oldArr[i] != null && oldArr[i].isAlive)
                insert(oldArr[i].element);
    }

    private int findPos(AnyType x) {
        int offset = 1;
        int currentPos = myHashfunc(x);
        while (myTable[currentPos] != null && !myTable[currentPos].element.equals(x)) {
            currentPos += offset;
            offset += 2;
            if (currentPos >= myTable.length)
                currentPos -= myTable.length;
        }
        return currentPos;
    }

    public boolean contains(AnyType x) {
        int currentPos = findPos(x);
        return isAlive(currentPos);
    }

    private boolean isAlive(int currentPos) {
        return myTable[currentPos] != null && myTable[currentPos].isAlive;
    }

    public void clear() {
        currentSize = 0;
        for (int i = 0; i < myTable.length; i++) myTable[i] = null;
    }

    private int myHashfunc(AnyType e) {
        int objHash = e.hashCode();
        objHash %= myTable.length;
        if (objHash < 0)
            objHash += myTable.length;
        return objHash;
    }

    private class HashEntry<AnyType> {
        AnyType element;   // the element
        boolean isAlive;  // false if marked deleted

        HashEntry(AnyType e) {
            this(e, true);
        }

        HashEntry(AnyType e, boolean i) {
            element = e;
            isAlive = i;
        }
    }

    private void createEntryCells(int arraySize) {
        myTable = new HashEntry[nextPrime(arraySize)];
    }

    //Internal method to find a prime number at least as large as n.
    private static int nextPrime(int n) {
        if (n <= 0) n = 3;
        if (n % 2 == 0) n++;
        for (; !isPrime(n); n += 2) ;
        return n;
    }

    private static boolean isPrime(int num) {
        if (num < 2) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i * i <= num; i += 2)
            if (num % i == 0)
                return false;
        return true;
    }
}
