/**
 * Probing table implementation of hash tables.
 * Note that all "matching" is based on the equals method.
 *
 * @author Mark Allen Weiss
 */
public class MyHashTable<AnyType> {

    public MyHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    public MyHashTable(int size) {
        createEntryCells(size);
        clear(); // textbook advices this type of precaution.
    }

    public void insert(AnyType x) {
        int currentPos = findPos(x);
        if (isActive(currentPos))
            return;
        array[currentPos] = new HashEntry<AnyType>(x, true);

        if (++currentSize > array.length / 2)
            rehash();
    }

    private void rehash() {
        HashEntry<AnyType>[] oldArr = array;

        createEntryCells(nextPrime(2 * oldArr.length));
        currentSize = 0;

        for (int i = 0; i < oldArr.length; i++)
            if (oldArr[i] != null && oldArr[i].isAlive)
                insert(oldArr[i].element);
    }

    private int findPos(AnyType x) {
        int offset = 1;
        int currentPos = myhash(x);

        while (array[currentPos] != null && !array[currentPos].element.equals(x)) {
            currentPos += offset;
            offset += 2;
            if (currentPos >= array.length)
                currentPos -= array.length;
        }
        return currentPos;
    }

    public void remove(AnyType x) {
        int currentPos = findPos(x);
        if (isActive(currentPos))
            array[currentPos].isAlive = false;
    }

    public boolean contains(AnyType x) {
        int currentPos = findPos(x);
        return isActive(currentPos);
    }

    private boolean isActive(int currentPos) {
        return array[currentPos] != null && array[currentPos].isAlive;
    }

    public void clear() {
        currentSize = 0;
        for (int i = 0; i < array.length; i++) array[i] = null;
    }

    private int myhash(AnyType x) {
        int hashVal = x.hashCode();

        hashVal %= array.length;
        if (hashVal < 0)
            hashVal += array.length;

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

    private static final int DEFAULT_TABLE_SIZE = 11;

    private HashEntry<AnyType>[] array; // The array of elements
    private int currentSize;              // The number of occupied cells

    private void createEntryCells(int arraySize) {
        array = new HashEntry[nextPrime(arraySize)];
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
