package c02dt.sudoku.solver;

/*
 * Copyright (C) 2005, 2006 Stan Chesnutt.  All rights reserved.  You
 * are welcome to reuse this code in any project, commercial or
 * otherwise, as long as you mention my name in the documentation for
 * the program (perhaps in an "about" box, or a credit page, etc).
 *
 * comments/questions to chesnutt at gmail dot com
 *
 * $Header: /home/cvs//dancinglinks/DancingLinksArena.java,v 1.3 2006/01/03 23:29:45 chesnutt Exp $
 */
import java.util.Iterator;
import java.util.List;


class DancingLinksArena {
    ColumnNode firstColumn;
    int rowCount;
    SolutionHandler handler;
    Node solution[];
    int initialSolutionIndex;

    DancingLinksArena (int labels[]) {
        ColumnNode columns[] = new ColumnNode[labels.length];

        for (int i = 0; i < labels.length; i++) {
            assert(labels[i] > 0);
            columns[i] = new ColumnNode(labels[i]);
            columns[i].setRight(null);

            if (i > 0) {
                columns[i].setLeft(columns[i - 1]);
                columns[i - 1].setRight(columns[i]);
            }
        }

        firstColumn = new ColumnNode(0);
        columns[0].setLeft(firstColumn);
        firstColumn.setRight(columns[0]);
        columns[labels.length - 1].setRight(firstColumn);
        firstColumn.setLeft(columns[labels.length - 1]);
        solution = new Node[labels.length];
        initialSolutionIndex = 0;
    }

    /*
     * debug
     */
    ColumnNode getFirstColumn() {
        return((ColumnNode) firstColumn.getRight());
    }

    void removeColumn(ColumnNode columnHead) {
        Node scanner = columnHead.getDown();

        /*
         * Unsnap the elements of each row in the column
         */
        while (scanner != columnHead) {
            Node rowTraveller = scanner.getRight();

            /*
             * remove this row
             */
            while (rowTraveller != scanner) {
                rowTraveller.getUp().setDown(rowTraveller.getDown());
                rowTraveller.getDown().setUp(rowTraveller.getUp());
                rowTraveller.getColumn().decrementSize();
                rowTraveller = rowTraveller.getRight();
            }

            scanner = scanner.getDown();
        }

        /*
         * Now remove the column
         */
        columnHead.getLeft().setRight(columnHead.getRight());
        columnHead.getRight().setLeft(columnHead.getLeft());
    }

    void reinsertColumn(ColumnNode columnNode) {
        Node scanner = columnNode.getUp();

        /*
         * Iterate through the rows
         */
        while (scanner != columnNode) {
            Node rowTraveller = scanner.getLeft();

            while (rowTraveller != scanner) {
                rowTraveller.getUp().setDown(rowTraveller);
                rowTraveller.getDown().setUp(rowTraveller);
                rowTraveller.getColumn().incrementSize();
                rowTraveller = rowTraveller.getLeft();
            }

            scanner = scanner.getUp();
        }

        /*
         * put the column back in the column-header list
         */
        columnNode.getLeft().setRight(columnNode);
        columnNode.getRight().setLeft(columnNode);
    }

    /*
     * Remove the row.  For each element in the row, traverse the
     * corresponding column and remove the rows in that column.  A
     * side-effect of removing the first column is that this row will
     * be removed.
     */
    void removeRow(Node rowHead) {
        Node scanner = rowHead;

        do {
            Node next = scanner.getRight();

            removeColumn(scanner.getColumn());
            scanner = next;
        } while (scanner != rowHead);
    }

    void reinsertRow(Node rowHead) {
        Node scanner = rowHead;

        do {
            scanner = scanner.getLeft();
            reinsertColumn(scanner.getColumn());
        } while (scanner != rowHead);
    }

    Node addInitialRow(int labels[]) {
        Node result = null;

        if (labels.length != 0) {
            Node prev = null;
            Node first = null;
            boolean found = false;

            rowCount = rowCount + 1;

            for (int i = 0; i < labels.length; i++) {
                Node node = new Node(rowCount, labels[i]);
                ColumnNode searcher;

                assert(labels[i] > 0);
                node.setLeft(prev);
                node.setRight(null);

                if (prev != null)
                    prev.setRight(node);
                else
                    first = node;

                /*
                 * slow search for column
                 */
                searcher = firstColumn;

                do {
                    if (searcher.getLabel() == labels[i]) {
                        node.setColumn(searcher);
                        searcher.addAtEnd(node);
                        found = true;
                    }

                    searcher = (ColumnNode) searcher.getRight();
                } while (searcher != firstColumn);

                if (found == false) {
                    System.out.println("Can't find header for " +
                                       searcher.getLabel());
                    assert(found);
                }

                prev = node;
            }

            /*
             * "prev" now points to the last node.  "first" points
             * to the first.  Complete the circular list
             */
            first.setLeft(prev);
            prev.setRight(first);
            result = first;
        }

        return(result);
    }

    void solveRecurse(int solutionIndex) {
        ColumnNode nextColumn = (ColumnNode) firstColumn.getRight();

        if (nextColumn != firstColumn) {
            /*
             * This is a nonterminal column.  March down the column,
             * and remove/recurse any rows.
             *
             * If there are no rows in this column, there is no
             * recursion and the method completes.  Empty columns mean
             * that rows which were previously removed caused a "dead
             * end".
             */
            Node row = nextColumn.getDown();

            while (row != nextColumn) {
                removeRow(row);
                solution[solutionIndex] = row;
                solveRecurse(solutionIndex + 1);
                reinsertRow(row);
                row = row.getDown();
            }
        } else {
            /*
             * If all columns have been consumed, a solution has been
             * found.  Report that solution, and exit this method
             * invocation.
             */
            int solutionRowNumbers[] = new int[solutionIndex];

            for (int i = 0; i < solutionIndex; i++) {
                solutionRowNumbers[i] = solution[i].getRowNumber() - 1;
            }

            handler.handleSolution(solutionRowNumbers);
        }
    }

    void solve(SolutionHandler handler) {
        this.handler = handler;
        solveRecurse(initialSolutionIndex);
    }

    /*
     * Used to remove portions of the possible solution space which
     * are already "known" to be part of the solution.  The provided
     * array lists all such rows.
     */
    void removeInitialSolutionSet(List solutions) {
        Iterator listIterator = solutions.iterator();

        while (listIterator.hasNext()) {
            Node row = (Node) listIterator.next();

            removeRow(row);
            solution[initialSolutionIndex++] = row;
        }
    }
}
