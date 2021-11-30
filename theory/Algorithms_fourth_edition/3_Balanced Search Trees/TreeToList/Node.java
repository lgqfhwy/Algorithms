//  This is the simple Node class from which the tree and list
//  are built. This does not have any methods -- it's just used
//  as dumb storage by TreeList.
//  The code below tries to be clear where it treats a Node pointer
//  as a tree vs. where it is treated as a list.
public class Node {
    int data;
    Node small;
    Node large;
    public Node(int data) {
        this.data = data;
        small = null;
        large = null;
    }
    public static void main(String[] args) {
        ;
    }
}