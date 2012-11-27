package c02dt.sudoku.solver;


/*
 * Copyright (C) 2005 Stan Chesnutt.  All rights reserved.  You are welcome
 * to reuse this code in any project, commercial or otherwise, as long as
 * you mention my name in the documentation for the program (perhaps in
 * an "about" box, or a credit page, etc).
 *
 * comments/questions to chesnutt at gmail dot com
 *
 * $Header: /home/cvs//dancinglinks/dlinks.java,v 1.2 2006/01/03 23:29:45 chesnutt Exp $
 */
public class dlinks
{
    void verifyOriginal(DancingLinksArena dla) {
        Node testNode, prev;
        ColumnNode column = dla.getFirstColumn();

        assert(column != null);
        assert(column.getRowNumber() == 0);
        assert(column.getLabel() == 1);
        assert(column.getCount() == 2);
        assert(column.getDown().verifyRowAndLabel(1, 1));
        assert(column.getRight().verifyRowAndLabel(0, 2));
        assert(column.getLeft().verifyRowAndLabel(0, 0));
        assert(column.getUp().verifyRowAndLabel(4, 1));
        assert(column.getColumn() == null);

        testNode = column.getDown();
        assert(testNode != null);
        assert(testNode.getRowNumber() == 1);
        assert(testNode.getLabel() == 1);
        assert(testNode.getLeft().verifyRowAndLabel(1, 2));
        assert(testNode.getUp() == column);
        assert(testNode.getDown().verifyRowAndLabel(4, 1));
        assert(testNode.getColumn() == column);
        assert(testNode.getRight().verifyRowAndLabel(1, 2));

        prev = testNode;
        testNode = testNode.getDown();
        assert(testNode.getUp() == prev);
        assert(testNode.getRowNumber() == 4);
        assert(testNode.getLabel() == 1);
        assert(testNode.getLeft().verifyRowAndLabel(4, 4));
        assert(testNode.getDown() == column);
        assert(testNode.getColumn() == column);
        assert(testNode.getRight().verifyRowAndLabel(4, 4));

        column = (ColumnNode) column.getRight();

        assert(column != null);
        assert(column.getRowNumber() == 0);
        assert(column.getLabel() == 2);
        assert(column.getCount() == 2);
        assert(column.getLeft().verifyRowAndLabel(0, 1));
        assert(column.getRight().verifyRowAndLabel(0, 3));
        assert(column.getDown().verifyRowAndLabel(1, 2));
        assert(column.getUp().verifyRowAndLabel(2, 2));
        assert(column.getColumn() == null);

        testNode = column.getDown();
        assert(testNode != null);
        assert(testNode.getRowNumber() == 1);
        assert(testNode.getLabel() == 2);
        assert(testNode.getLeft().verifyRowAndLabel(1, 1));
        assert(testNode.getRight().verifyRowAndLabel(1, 1));
        assert(testNode.getUp() == column);
        assert(testNode.getColumn() == column);

        prev = testNode;
        testNode = testNode.getDown();
        assert(testNode.getUp() == prev);
        assert(testNode.getRowNumber() == 2);
        assert(testNode.getLabel() == 2);
        assert(testNode.getLeft().verifyRowAndLabel(2, 4));
        assert(testNode.getDown() == column);
        assert(testNode.getColumn() == column);
        assert(testNode.getRight().verifyRowAndLabel(2, 3));

        column = (ColumnNode) column.getRight();

        assert(column != null);
        assert(column.getRowNumber() == 0);
        assert(column.getLabel() == 3);
        assert(column.getCount() == 2);
        assert(column.getLeft().verifyRowAndLabel(0, 2));
        assert(column.getRight().verifyRowAndLabel(0, 4));
        assert(column.getDown().verifyRowAndLabel(2, 3));
        assert(column.getUp().verifyRowAndLabel(3, 3));
        assert(column.getColumn() == null);

        testNode = column.getDown();
        assert(testNode != null);
        assert(testNode.getRowNumber() == 2);
        assert(testNode.getLabel() == 3);
        assert(testNode.getLeft().verifyRowAndLabel(2, 2));
        assert(testNode.getRight().verifyRowAndLabel(2, 4));
        assert(testNode.getDown().verifyRowAndLabel(3, 3));
        assert(testNode.getUp() == column);
        assert(testNode.getColumn() == column);

        prev = testNode;
        testNode = testNode.getDown();
        assert(testNode.getUp() == prev);
        assert(testNode.getRowNumber() == 3);
        assert(testNode.getLabel() == 3);
        assert(testNode.getLeft().verifyRowAndLabel(3, 4));
        assert(testNode.getRight().verifyRowAndLabel(3, 4));
        assert(testNode.getDown() == column);
        assert(testNode.getUp().verifyRowAndLabel(2, 3));
        assert(testNode.getColumn() == column);

        column = (ColumnNode) column.getRight();

        assert(column != null);
        assert(column.getRowNumber() == 0);
        assert(column.getLabel() == 4);
        assert(column.getCount() == 3);
        assert(column.getLeft().verifyRowAndLabel(0, 3));
        assert(column.getRight().verifyRowAndLabel(0, 0));
        assert(column.getDown().verifyRowAndLabel(2, 4));
        assert(column.getUp().verifyRowAndLabel(4, 4));
        assert(column.getColumn() == null);

        testNode = column.getDown();
        assert(testNode != null);
        assert(testNode.getRowNumber() == 2);
        assert(testNode.getLabel() == 4);
        assert(testNode.getLeft().verifyRowAndLabel(2, 3));
        assert(testNode.getRight().verifyRowAndLabel(2, 2));
        assert(testNode.getDown().verifyRowAndLabel(3, 4));
        assert(testNode.getUp() == column);
        assert(testNode.getColumn() == column);

        prev = testNode;
        testNode = testNode.getDown();
        assert(testNode.getUp() == prev);
        assert(testNode.getRowNumber() == 3);
        assert(testNode.getLabel() == 4);
        assert(testNode.getLeft().verifyRowAndLabel(3, 3));
        assert(testNode.getRight().verifyRowAndLabel(3, 3));
        assert(testNode.getDown().verifyRowAndLabel(4, 4));
        assert(testNode.getUp().verifyRowAndLabel(2, 4));
        assert(testNode.getColumn() == column);

        prev = testNode;
        testNode = testNode.getDown();
        assert(testNode.getUp() == prev);
        assert(testNode.getRowNumber() == 4);
        assert(testNode.getLabel() == 4);
        assert(testNode.getLeft().verifyRowAndLabel(4, 1));
        assert(testNode.getRight().verifyRowAndLabel(4, 1));
        assert(testNode.getDown() == column);
        assert(testNode.getUp().verifyRowAndLabel(3, 4));
        assert(testNode.getColumn() == column);
    }

    void testit() {
        DancingLinksArena dla;
        int labels[] = {1, 2, 3, 4};
        int row1[] = {1, 2};
        int row2[] = {2, 3, 4};
        int row3[] = {3, 4};
        int row4[] = {1, 4};
        ColumnNode columnNode;

        /*
         * create sparse matrix
         */
        dla = new DancingLinksArena(labels);
        dla.addInitialRow(row1);
        dla.addInitialRow(row2);
        dla.addInitialRow(row3);
        dla.addInitialRow(row4);

        /*
         * verify topology
         */
        verifyOriginal(dla);

        /*
         * get the head of row 1, and remove it
         */
        Node firstRow = dla.getFirstColumn().getDown();

        dla.removeRow(firstRow);

        /*
         * Verify that only one row remains
         */
        columnNode = dla.getFirstColumn();
        assert(columnNode.getCount() == 1);
        assert(columnNode.getRowNumber() == 0);
        assert(columnNode.getLabel() == 3);
        assert(columnNode.getLeft().verifyRowAndLabel(0, 0));
        assert(columnNode.getRight().verifyRowAndLabel(0, 4));
        assert(columnNode.getDown().verifyRowAndLabel(3, 3));
        assert(columnNode.getUp().verifyRowAndLabel(3, 3));

        /*
         * verify that the removed row is undisturbed
         */
        assert(firstRow.verifyRowAndLabel(1, 1));
        assert(firstRow.getLeft().verifyRowAndLabel(1, 2));
        assert(firstRow.getRight().verifyRowAndLabel(1, 2));
        assert(firstRow.getDown().verifyRowAndLabel(4, 1));
        assert(firstRow.getUp().verifyRowAndLabel(0, 1));

        /*
         * put it back in
         */
        dla.reinsertRow(firstRow);
        verifyOriginal(dla);
    }

    public static void main(String args[]) {
        dlinks dl = new dlinks();

        dl.testit();
        System.out.println("dlinks unit test passed!");
    }
}
