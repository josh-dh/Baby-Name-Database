package project5;

import java.util.ArrayList;

/**
 * AVLTree implementation based off of BST recursive implementation; works with Name class
 * @author Joshua Donelly-Higgins
 * @author Joanna Klukowska
 */
public class AVLTree<T extends Comparable<T>>{

    //BEGIN CODE FROM BST_RECURSIVE

    // root of the tree
    protected Node<T> root;
    // current number of nodes in the tree
    protected int numOfElements;
    //helper variable used by the remove methods
    private boolean found;

    /**
     * Default constructor that creates an empty tree.
     */
    public AVLTree() {
        this.root = null;
        numOfElements = 0;
    }

    /**
     * Remove the item from the tree. If item is null the tree remains unchanged. If
     * item is not found in the tree, the tree remains unchanged.
     *
     * @param target the item to be removed from this tree
     */
    public boolean remove(T target)
    {
        root = recRemove(target, root);
        if (found) numOfElements--;
        return found;
    }

    /*
     * Actual recursive implementation of remove method: find the node to remove.
     *
     * @param target the item to be removed from this tree
     */
    private Node<T> recRemove(T target, Node<T> node)
    {
        if (node == null)
            found = false;
        else if (target.compareTo(node.data) < 0)
            node.left = recRemove(target, node.left);
        else if (target.compareTo(node.data) > 0)
            node.right = recRemove(target, node.right );
        else {
            node = removeNode(node);
            found = true;
        }
        return node;
    }

    /*
     * Actual recursive implementation of remove method: perform the removal.
     *
     * @param target the item to be removed from this tree
     * @return a reference to the node itself, or to the modified subtree
     */
    private Node<T> removeNode(Node<T> node)
    {
        T data;
        if (node.left == null)
            return node.right ;
        else if (node.right  == null)
            return node.left;
        else {
            data = getPredecessor(node.left);
            node.data = data;
            node.left = recRemove(data, node.left);
            return node;
        }
    }

    /*
     * Returns the information held in the rightmost node of subtree
     *
     * @param subtree root of the subtree within which to search for the rightmost node
     * @return returns data stored in the rightmost node of subtree
     */
    private T getPredecessor(Node<T> subtree)
    {
        if (subtree==null) throw new NullPointerException("getPredecessor called with an empty subtree");
        Node<T> temp = subtree;
        while (temp.right  != null)
            temp = temp.right ;
        return temp.data;
    }

    /**
     * Determines the number of elements stored in this BST.
     *
     * @return number of elements in this BSWith Putin returning to office after this election, Moscow appears poised to continue disrupting world politics for years to come.T
     */
    public int size() {
        return numOfElements;
    }

    /**
     * Returns a string representation of this tree using an inorder traversal .
     * @see java.lang.Object#toString()
     * @return string representation of this tree
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        inOrderPrint(root, s);
        return s.toString();
    }

    /*
     * Actual recursive implementation of inorder traversal to produce string
     * representation of this tree.
     *
     * @param tree the root of the current subtree
     * @param s the string that accumulated the string representation of this BST
     */
    private void inOrderPrint(Node<T> tree, StringBuilder s) {
        if (tree != null) {
            inOrderPrint(tree.left, s);
            s.append(tree.data.toString() + "  ");
            inOrderPrint(tree.right , s);
        }
    }

    /**
     * DO NOT MOFIFY THIS METHOD.
     * INCLUDE IT AS-IS IN YOUR CODE.
     *
     * Produces tree like string representation of this BST.
     * @return string containing tree-like representation of this BST.
     */
    public String toStringTreeFormat() {

        StringBuilder s = new StringBuilder();

        preOrderPrint(root, 0, s);
        return s.toString();
    }

    /*
     * DO NOT MOFIFY THIS METHOD.
     * INCLUDE IT AS-IS IN YOUR CODE.
     *
     * Actual recursive implementation of preorder traversal to produce tree-like string
     * representation of this tree.
     *
     * @param tree the root of the current subtree
     * @param level level (depth) of the current recursive call in the tree to
     *   determine the indentation of each item
     * @param output the string that accumulated the string representation of this
     *   BST
     */
    private void preOrderPrint(Node<T> tree, int level, StringBuilder output) {
        if (tree != null) {
            String spaces = "\n";
            if (level > 0) {
                for (int i = 0; i < level - 1; i++)
                    spaces += "   ";
                spaces += "|--";
            }
            output.append(spaces);
            output.append(tree.data);
            preOrderPrint(tree.left, level + 1, output);
            preOrderPrint(tree.right , level + 1, output);
        }
        // uncomment the part below to show "null children" in the output
        else {
            String spaces = "\n";
            if (level > 0) {
                for (int i = 0; i < level - 1; i++)
                    spaces += "   ";
                spaces += "|--";
            }
            output.append(spaces);
            output.append("null");
        }
    }

    /**
     * Node class is used to represent nodes in a binary search tree.
     * It contains a data item that has to implement Comparable interface
     * and references to left and right subtrees.
     *
     * @author Joanna Klukowska
     *
     * @param <T> a reference type that implements Comparable<T> interface
     */
    static class Node <T extends Comparable <T>>
            implements Comparable <Node<T>> {


        protected Node<T> left;  //reference to the left subtree
        protected Node<T> right; //reference to the right subtree
        protected T data;            //data item stored in the node

        protected int height;
        protected int desc; 		//num of descendants


        /**
         * Constructs a BSTNode initializing the data part
         * according to the parameter and setting both
         * references to subtrees to null.
         * @param data
         *    data to be stored in the node
         */
        protected Node(T data) {
            this.data = data;
            left = null;
            right = null;
            height = 1;
            desc = 0;
        }


        /* (non-Javadoc)
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        @Override
        public int compareTo(Node<T> other) {
            return this.data.compareTo(other.data);
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return data.toString();
        }

        /**
         * (new method)
         * avoids NullPointerExceptions to node.left.data by returning null if node.left is null
         * @return null or data
         * @author Joshua Donelly-Higgins
         */
        public T getLeftData() {
            if (left == null) return null;
            return left.data;
        }

        /**
         * (new method)
         * avoids NullPointerExceptions to node.right.data by returning null if node.right is null
         * @return null or data
         * @author Joshua Donelly-Higgins
         */
        public T getRightData() {
            if (right == null) return null;
            return right.data;
        }
    }

    //END CODE FROM BST_RECURSIVE

    /**
     * Gets height for a node, error-checking for null values
     * @param n node
     * @return height
     */
    private int getHeight(Node n) {
        if (n == null) return 0;
        return n.height;
    }

    /**
     * Adds item to tree, calling on private method
     * @param item Name to insert
     */
    public void add(T item) {
        if (item == null) return;
        root = add(item, root);
        numOfElements += 1;
    }

    /**
     * Inserts item into tree
     * Balances tree
     * @param parentNode node
     * @param data item to insert
     * @return
     */
    private Node<T> add(T data, Node<T> parentNode) {
        if (parentNode == null) return new Node<>(data); //create new node with item
        if (data.compareTo(parentNode.data) < 0) { //move left
            parentNode.left = add(data, parentNode.left);
        } else if (data.compareTo(parentNode.data) > 0) { //move right
            parentNode.right = add(data, parentNode.right);
        } else { //return parent if node is duplicate
            return parentNode;
        }

        //balance
        return balanceTree(data, parentNode);
    }

    /**
     * Method that finds the type of imbalance and balances the tree if necessary
     * auxillary method for add
     * @param data data input
     * @param parentNode parent node
     * @return the new parent node
     */
    private Node<T> balanceTree (T data, Node<T> parentNode) {

        //update height of ancestor node and set ancestor balance
        parentNode.height = Math.max(getHeight(parentNode.left), getHeight(parentNode.right)) + 1;
        int ancestorBalance = balance(parentNode);
        //fast return for balanced nodes
        if (ancestorBalance >= -1 && ancestorBalance <= 1) return parentNode;

        //balance tree
        //check for null
        if (parentNode.getLeftData() != null) {
            if (data.compareTo(parentNode.getLeftData()) < 0 && ancestorBalance > 1) { //case 1: left, left
                return rightRotate(parentNode);
            } else if (data.compareTo(parentNode.getLeftData()) > 0 && ancestorBalance > 1) { //case 3: left, right
                parentNode.left = leftRotate(parentNode.left);
                return rightRotate(parentNode);
            }
        }

        //check for null
        if (parentNode.getRightData() != null) {
            if (data.compareTo(parentNode.getRightData()) > 0 && ancestorBalance < -1) { //case 2: right, right
                return leftRotate(parentNode);
            } else if (data.compareTo(parentNode.getRightData()) < 0 && ancestorBalance < -1) { //case 4: right, left
                parentNode.right = rightRotate(parentNode.right);
                return leftRotate(parentNode);
            }
        }

        return parentNode;
    }

    /**
     * Given a node whose children have uneven height
     * Perform the correct rotation on the child with greater height
     * @param parent node with an unbalanced child
     */
    private int balance(Node parent) {
        if (parent == null) return 0;

        return getHeight(parent.left) - getHeight(parent.right);
    }

    /**
     * left rotation
     * @param n root node
     */
    private Node leftRotate(Node n) {
        //perform rotation
        Node node1 = n.right;
        Node node2 = node1.left;
        n.right = node2;
        node1.left = n;

        //update height
        n.height = 1 + Math.max(getHeight(n.left), getHeight(n.right));
        node1.height = 1 + Math.max(getHeight(node1.left), getHeight(node1.right));
        return node1;
    }

    /**
     * right rotation
     * @param n root node
     */
    private Node rightRotate(Node n) {
        //perform rotation
        Node node1 = n.left;
        Node node2 = node1.right;
        n.left = node2;
        node1.right = n;

        //update height
        n.height = 1 + Math.max(getHeight(n.left), getHeight(n.right));
        node1.height = 1 + Math.max(getHeight(node1.left), getHeight(node1.right));
        return node1;
    }

    /**
     * returns ArrayList of name objects with names that match
     * a binary search is used to find names since they are the primary key
     * @param name name to find
     * @param node current node
     * @param output output ArrayList
     * @return output
     */
    public ArrayList<Name> searchName (String name, Node<Name> node, ArrayList<Name> output) {
        if (node.data.getName().equals(name)) {
            output.add(node.data);
            if (node.right != null) searchName(name, node.right, output);
            if (node.left != null) searchName(name, node.left, output);
        } else {
            if (node.right != null && name.compareTo(node.data.getName()) > 0) { //search right
                searchName(name, node.right, output);
            }
            if (node.left != null && name.compareTo(node.data.getName()) < 0) { //search left
                searchName(name, node.left, output);
            }
        }
        return output;
    }

    /**
     * returns ArrayList of name objects with counties that match
     * a simple inorder traversal is used to find counties because county is not the primary key
     * @param county county to find
     * @param node current node
     * @param output output ArrayList
     * @return output
     */
    public ArrayList<Name> searchCounty (String county, Node<Name> node, ArrayList<Name> output) {
        if (node.left != null) searchCounty(county, node.left, output);
        if (node.data.getCounty().equals(county)) output.add(node.data);
        if (node.right != null) searchCounty(county, node.right, output);
        return output;
    }
}
