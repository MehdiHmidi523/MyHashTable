public class MyHashTable<AnyType> {

    private static final int DEFAULT_SIZE = 18;
    private HashEntry<AnyType>[] myTable; // The myTable of elements
    private int currentSize;     // The number of occupied cells

    public MyHashTable() {
        this(DEFAULT_SIZE);
    }

    public MyHashTable(int size) {
        createEntryCells(size);
        clear(); // textbook advices this type of precaution.
    }

    public void insert(AnyType x) {
        int currentPos = findPos(x);
        if (isActive(currentPos))
            return;
        myTable[currentPos] = new HashEntry<AnyType>(x, true);

        if (++currentSize > myTable.length / 2)
            rehash();
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
        int currentPos = myhash(x);

        while (myTable[currentPos] != null && !myTable[currentPos].element.equals(x)) {
            currentPos += offset;
            offset += 2;
            if (currentPos >= myTable.length)
                currentPos -= myTable.length;
        }
        return currentPos;
    }

    public void remove(AnyType x) {
        int currentPos = findPos(x);
        if (isActive(currentPos))
            myTable[currentPos].isAlive = false;
    }

    public boolean contains(AnyType x) {
        int currentPos = findPos(x);
        return isActive(currentPos);
    }

    private boolean isActive(int currentPos) {
        return myTable[currentPos] != null && myTable[currentPos].isAlive;
    }

    public void clear() {
        currentSize = 0;
        for (int i = 0; i < myTable.length; i++) myTable[i] = null;
    }

    private int myhash(AnyType x) {
        int hashVal = x.hashCode();

        hashVal %= myTable.length;
        if (hashVal < 0)
            hashVal += myTable.length;

        return hashVal;
    }

    private class HashEntry<AnyType> {
        public AnyType element;   // the element
        public boolean isAlive;  // false if marked deleted

        public HashEntry(AnyType e) {
            this(e, true);
        }

        public HashEntry(AnyType e, boolean i) {
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
            if (num % i == 0) return false;
        return true;
    }
}
