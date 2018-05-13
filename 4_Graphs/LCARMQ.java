// Find LCA in Binary Tree using RMQ
// Lowest Common Ancestor (LCA) of two nodes u and v in a rooted tree T is defined 
// as the node located farthest from the root that has both u and v as descendants.

// Range Minimum Query (RMQ) is used on arrays to find the position of an element with 
// the minimum value between two specified indices. Different approaches for solving 
// RMQ have been discussed here and here. In this article, Segment Tree based approach 
// is discussed. With segment tree, preprocessing time is O(n) and time to for range 
// minimum query is O(Logn). The extra space required is O(n) to store the segment tree.

// Reduction of LCA to RMQ:
// The idea is to traverse the tree starting from root by an Euler tour (traversal without 
// lifting pencil), which is a DFS-type traversal with preorder traversal characteristics.

// Algorithm:
// 1.Do a Euler tour on the tree, and fill the euler, level and first occurrence arrays.
// 2.Using the first occurrence array, get the indices corresponding to the two nodes which will be the corners of the range in the level array that is fed to the RMQ algorithm for the minimum value.
// 3.Once the algorithm return the index of the minimum level in the range, we use it to determine the LCA using Euler tour array.
// http://www.geeksforgeeks.org/find-lca-in-binary-tree-using-rmq/

public class LCARMQ {

    private static final int maxNum = 10000;

    int[] dataSequence; // the Euler tour of the data sequence
    int[] depthSequence;    // the depth sequence corresponding to the data.
    int num;
    RMQSegmentTreeIndex rmq;

    public static class Node {
        Node left, right;
        int data;

        public Node(int item) {
            data = item;
        }
    }

    public LCARMQ(Node root) {
        dataSequence = new int[maxNum];
        depthSequence = new int[maxNum];
        int depth = 0;  // the data depth
        num = 0;    // the data number 
        num = buildSequence(root, depth, num);
        rmq = new RMQSegmentTreeIndex(depthSequence, num);
    }

    private int buildSequence(Node root, int depth, int num) {
        if (root == null)
            return num;
        if (root.left != null) {
            dataSequence[num] = root.data;
            depthSequence[num] = depth;
            dataSequence[num + 1] = root.left.data;
            depthSequence[num + 1] = depth + 1;
            num = buildSequence(root.left, depth + 1, num + 2);
        }
        if (root.right != null) {
            dataSequence[num] = root.data;
            depthSequence[num] = depth;
            dataSequence[num + 1] = root.right.data;
            depthSequence[num + 1] = depth + 1;
            num = buildSequence(root.right, depth + 1, num + 2);
        }
        return num;
    }

    public int LCAQuery(int i, int j) {
        int k, h;
        for (k = 0; k < num; k++) {
            if (dataSequence[k] == i) {
                break;
            }
        }
        for (h = 0; h < num; h++) {
            if (dataSequence[h] == j) {
                break;
            }
        }
        if (k != num && h != num) {
            int n = rmq.RMQIndex(k, h);
            return dataSequence[n];
        }
        else {
            System.out.printf("Index not in array");
            return -1;
        }
    }

    public static void main(String[] args) {
        Node x = new Node(1);
        Node y = new Node(2);
        x.left = y;
        y = new Node(3);
        x.right = y;
        Node root = x;
        LCARMQ lca = new LCARMQ(root);
        System.out.println(lca.LCAQuery(2, 3));

    }


}