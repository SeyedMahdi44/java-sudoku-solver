package c02dt.sudoku.solver;


/*
 * Copyright (C) 2005 Stan Chesnutt.  All rights reserved.  You are welcome
 * to reuse this code in any project, commercial or otherwise, as long as
 * you mention my name in the documentation for the program (perhaps in
 * an "about" box, or a credit page, etc).
 *
 * comments/questions to chesnutt at gmail dot com
 *
 * $Header: /home/cvs//dancinglinks/Node.java,v 1.2 2006/01/03 23:29:45 chesnutt Exp $
 */

/*
 * Implement the node object which populates the sparse matrix
 */
class Node {
    Node left;
    Node right;
    Node up;
    Node down;
    ColumnNode column;
    int rowNumber;
    int label;

    public String labelOrNull(Node node) {
        if (node == null)
            return("NULL");
        else
            return(node.getFullLabel());
    }

    public String getFullLabel() {
        return("row " + rowNumber + ", label " + label);
    }

    public String toString() {
        return("Node " + getFullLabel() + ", left is (" +
               labelOrNull(getLeft()) + "), right is (" +
               labelOrNull(getRight()) + "), down is (" +
               labelOrNull(getDown()) + "), up is (" +
               labelOrNull(getUp()) + "), column is (" +
               labelOrNull(getColumn()) + ")");
    }

    void setLeft(Node node) {
        left = node;
    }

    Node getLeft() {
        return(left);
    }

    void setRight(Node node) {
        right = node;
    }

    Node getRight() {
        return(right);
    }

    void setUp(Node node) {
        up = node;
    }

    Node getUp() {
        return(up);
    }

    void setDown(Node node) {
        down = node;
    }

    Node getDown() {
        return(down);
    }

    void setColumn(ColumnNode node) {
        column = node;
    }

    ColumnNode getColumn() {
        return(column);
    }

    int getLabel() {
        return(label);
    }

    int getRowNumber() {
        return(rowNumber);
    }

    /*
     * Sanity check
     */
    boolean verifyRowAndLabel(int row, int label) {
        return((getRowNumber() == row) && (getLabel() == label));
    }

    /*
     * Create a self-referential node
     */
    Node(int rowNumber, int label) {
        this.setLeft(this);
        this.setRight(this);
        this.setUp(this);
        this.setDown(this);
        this.rowNumber = rowNumber;
        this.label = label;
    }
}
