/**
 * Created by Mehdi on 19/10/2018 for A2.
 */
public class MyMeasure implements A2Measure {
    //A Fibonacci Heap maintains a pointer to minimum value (which is the root of a tree).
    // All tree roots(upper level) are connected using circular doubly linked list,
    // so all of them can be accessed using single ‘min’ pointer.

    private FibonacciHeap arr1 = new FibonacciHeap();
    private FibonacciHeap arr2 = new FibonacciHeap();

    public MyMeasure(){
    //we need a data structure that allows duplication and orders the input in a way that keeps the minimum available for a speedy removal.

    }

    @Override public boolean isSameCollection(int[] array1, int[] array2) {
        if (array2.length != array1.length)
            return false;
        arr1.createHeap(array1);
        arr2.createHeap(array2);
        while(!arr1.isEmpty() && !arr2.isEmpty())
            if (arr1.removeMin() != arr2.removeMin())
                return false;
        return true;
    }
    @Override public int minDifferences(int[] array1, int[] array2) {
        if (array2.length != array1.length)
           return -99;
        int sqrtSum = 0;
        arr1.createHeap(array1); //clear heap in this method before any instructions
        arr2.createHeap(array2);
        while(!arr1.isEmpty() && !arr2.isEmpty()) 
            sqrtSum += ((arr2.removeMin() - arr1.removeMin()) * (arr2.removeMin() - arr1.removeMin()));//  the squared sum of the differences: ∑(x2-x1)2
        return sqrtSum;
    }

    @Override public int[] getPercentileRange(int[] arr, int lower, int upper) {

        return new int[0];
    }

    private class FibonacciHeap { //Text book Solution: Introduction to Algorithms M.I.T
        private Node min;
        private int n;
        public void clear() { min = null;n = 0; }
        boolean isEmpty() { return min == null; }

        void createHeap(int[] array1) {

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

        public void insert(int x, double key) {
            Node node = new Node(x, key);
            if (min != null) {
                node.right = min;
                node.left = min.left;
                min.left = node;
                node.left.right = node;
                if (key < min.key) min = node;
            } else min = node;
            n++;
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
            n--;
            return popPop.data;
        }
        
        public FibonacciHeap union(FibonacciHeap H1, FibonacciHeap H2) {
            FibonacciHeap H = new FibonacciHeap();
            if (H1 != null && H2 != null) {
                H.min = H1.min;
                if (H.min != null) {
                    if (H2.min != null) {
                        H.min.right.left = H2.min.left;
                        H2.min.left.right = H.min.right;
                        H.min.right = H2.min;
                        H2.min.left = H.min;
                        if (H2.min.key < H1.min.key) {
                            H.min = H2.min;
                        }
                    }
                } else {
                    H.min = H2.min;
                }
                H.n = H1.n + H2.n;
            }
            return H;
        }

        class Node {
            private int data;
            private double key;
            private Node parent;
            private Node child;
            private Node right;
            private Node left;
            private int degree;

            Node(int data, double key) {
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
