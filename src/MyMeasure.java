/**
 * Created by Mehdi on 19/10/2018 for A2.
 */
//A Fibonacci Heap maintains a pointer to minimum value (which is the root of a tree).
// All tree roots(upper level) are connected using circular doubly linked list,
// so all of them can be accessed using single ‘min’ pointer.
public class MyMeasure implements A2Measure {
    private FibonacciHeap arr1 = new FibonacciHeap();
    private FibonacciHeap arr2 = new FibonacciHeap();

    public static void main(String[] args){
        MyMeasure s = new MyMeasure(); //we need a data structure that allows duplication and orders the input in a way that keeps the minimum available for a speedy removal.
       if(s.isSameCollection(new int[]{10, 1, 7, 10}, new int[]{1, 10, 7, 10}))
           System.out.println("isSameCollection([10,1,7,10], [1, 10, 7,10]) returns true ");
       if(s.isSameCollection(new int[]{10,1,7,9}, new int[]{1, 10, 7,10}))
           System.out.println("evaluation is false which is correct! 1");
       if(s.isSameCollection(new int[]{10,1,7}, new int[]{1, 7, 7,10}))
            System.out.println("evaluation is false which is correct! 2");

    }

    @Override
    public boolean isSameCollection(int[] array1, int[] array2) {
        if (array2.length != array1.length)
            return false;
        arr1.createHeap(array1);
        arr2.createHeap(array2);
        while (!arr1.isEmpty() && !arr2.isEmpty()){
            int k1=arr1.removeMin();
            int k2= arr2.removeMin();
            if (k1 != k2) return false;
        }
        return true;
    }

    @Override
    public int minDifferences(int[] array1, int[] array2) {
        if (array2.length != array1.length)
            return -99;
        int sqrtSum = 0;
        arr1.createHeap(array1); //clear heap in this method before any instructions
        arr2.createHeap(array2);
        while (!arr1.isEmpty() && !arr2.isEmpty())
            sqrtSum += ((arr2.removeMin() - arr1.removeMin()) * (arr2.removeMin() - arr1.removeMin()));//  the squared sum of the differences: ∑(x2-x1)2
        return sqrtSum;
    }

    @Override
    public int[] getPercentileRange(int[] arr, int lower, int upper) {
        if (lower > upper) throw new IllegalArgumentException();
        int upperPercent = (int) (arr.length * (upper / 100.0));
        int lowPercent = (int) (arr.length * (lower / 100.0));
        //an array to keep the values within range wanted
        int[] range = new int[upperPercent - lowPercent];
        //the heap is already sorted! Nodes come out in increasing order by value
        arr1.createHeap(arr);
        int n = arr1.heapSize;
        int index = 0;
        for (int i = 0; i < n; i++){
            int x= arr1.removeMin();
            if(i>=lowPercent && i<=upperPercent)
                range[index++]=x;
        }
        return range;
    }

    private class FibonacciHeap { //Text book Solution: Introduction to Algorithms M.I.T
        private Node min;
        private int heapSize;

        public void clear() {
            min = null;
            heapSize = 0;
        }

        boolean isEmpty() {
            return min == null;
        }

        void createHeap(int[] array1) {
            clear();
            for (int i = 0; i < array1.length; i++)
                insert(i, array1[i]);
            consolidate();
        }

        //the consolidate operation, which is the only complex bit of code in a Fibonacci Heap,
        // as everything else is fairly trivial.
        private void consolidate() {
            Node[] degree_array = new Node[40];
            Node start = min;
            Node w = min;// For each root list node look for others of the same degree.
            do {
                Node x = w;
                Node nextW = w.right;
                int d = x.degree;
                while (degree_array[d] != null) {
                    Node y = degree_array[d];
                    if (x.key > y.key) {
                        Node temp = y;
                        y = x;
                        x = temp;
                    }
                    if (y == start)
                        start = start.right;
                    if (y == nextW)
                        nextW = nextW.right;
                    y.link(x);
                    degree_array[d] = null;
                    d++;
                }
                degree_array[d] = x;
                w = nextW;// Move forward through list.
            } while (w != start);
            min = start;
            for (Node a : degree_array)
                if (a != null && a.key < min.key)
                    min = a;
            //Do not waste time rebuilding the root list at the end of consolidate;
            // the order of the root elements is of no consequence to the algorithm.
        }

        public void insert(int x, int key) {
            Node node = new Node(x, key);
            if (min != null) {
                node.right = min;
                node.left = min.left;
                min.left = node;
                node.left.right = node;
                if (key < min.key) min = node;
            } else min = node;
            heapSize++;
        }

        int removeMin() {
            Node popPop = min;
            if (popPop == null)
                return -99;
            if (popPop.child != null) {
                popPop.child.parent = null;
                for (Node x = popPop.child.right; x != popPop.child; x = x.right)
                    x.parent = null;
                Node minleft = min.left;
                Node popchildleft = popPop.child.left;
                min.left = popchildleft;
                popchildleft.right = min;
                popPop.child.left = minleft;
                minleft.right = popPop.child;
            }
            popPop.left.right = popPop.right;
            popPop.right.left = popPop.left;
            if (popPop == popPop.right)
                min = null;
            else {
                min = popPop.right;
                consolidate();
            }
            heapSize--;
            return popPop.key;
        }

        class Node {
            private int data;
            private int key;
            private Node parent;
            private Node child;
            private Node right;
            private Node left;
            private int degree;

            Node(int data, int key) {
                this.data = data;
                this.key = key;
                right = this;
                left = this;
            }

            /* Make this node a child of the given parent node.*/
            void link(Node parent) {
                left.right = right;
                right.left = left;
                this.parent = parent;
                if (parent.child == null) {
                    parent.child = this;
                    right = this;
                    left = this;
                } else {
                    left = parent.child;
                    right = parent.child.right;
                    parent.child.right = this;
                    right.left = this;
                }
                parent.degree++;
            }
        }
    }
}
