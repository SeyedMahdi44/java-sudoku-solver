package c02dt.sudoku.solver;


/*
 * Copyright (C) 2005 Stan Chesnutt.  All rights reserved.  You are welcome
 * to reuse this code in any project, commercial or otherwise, as long as
 * you mention my name in the documentation for the program (perhaps in
 * an "about" box, or a credit page, etc).
 *
 * comments/questions to chesnutt at gmail dot com
 *
 * $Header: /home/cvs//dancinglinks/ColumnNode.java,v 1.2 2006/01/03 23:29:45 chesnutt Exp $
 */
class ColumnNode extends Node {
    int size;

    void addAtEnd(Node node) {
        Node end = getUp();

        node.setUp(end);
        node.setDown(this);
        end.setDown(node);
        this.setUp(node);
        size = size + 1;
    }

    ColumnNode(int label) {
        super(0, label);
        size = 0;
    }

    public String toString() {
        return(super.toString() + ", count " + size);
    }

    public int getCount() {
        return(size);
    }

    public void incrementSize() {
        size = size + 1;
    }

    public void decrementSize() {
        size = size - 1;
    }
}

