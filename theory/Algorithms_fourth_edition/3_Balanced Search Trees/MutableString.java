// Mutable string. Create a data type that supports the following operations on a string:
// get(int i), insert(int i, char c), and delete(int i), where get returns the ith character 
// of the string, insert inserts the character c and makes it the ith character, and delete 
// deletes the ith character. Use a binary search tree.
// Hint: Use a BST (with key = real number between 0 and 1, value = character) so that the 
// inorder traversal of the tree yields the characters in the appropriate order. Use select() 
// to find the ith element. When inserting a character at position i, choose the real number 
// to be the average of the keys currently at positions i-1 and i.
public class MutableString {

    private BST<Integer, Character> bst;

    public MutableString() {
        bst = new BST<Integer, Character>();
    }

    // Returns true if this symbol table is empty
    // false otherwise
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    // Returns the number of key-value pairs in this symbol table.
    public int size() {
        return bst.size();
    }

    // Does this symbol table contains the given character ?
    public boolean contains(Character chr) {
        for (Integer i : bst.keys()) {
            if (bst.get(i).equals(chr))
                return true;
        }
        return false;
    }

    // get the ith Character
    public Character get(int i) {
        return bst.get(i);
    }

    // insert the Character as i th element.
    public void insert(int i, Character c) {
        bst.put(i, c);
    }

    // insert the Character as the last element
    public void insert(Character c) {
        bst.put(bst.size(), c);
    }

    // delete the ith Character
    public void delete(int i) {
        bst.delete(i);
    }

    public Iterable<Integer> keys() {
        return bst.keys();
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Integer k : bst.keys()) {
            str.append(bst.get(k));
        }
        return str.toString();
    }

    public static void main(String[] args) {
        Character[] chr = {'h', 'o', 'l', 'l', 'o', 'w'};
        MutableString ms = new MutableString();
        for (int i = 0; i < 6; i++) {
            ms.insert(chr[i]);
        }
        for (Integer k : ms.keys()) {
            StdOut.print(ms.get(k));
        }
        StdOut.println();
        ms.insert(1, 'h');
        StdOut.println(ms.toString());
    }


}