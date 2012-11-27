package c02dt.sudoku.solver;

/*
 * Copyright (C) 2005 Stan Chesnutt.  All rights reserved.  You are welcome
 * to reuse this code in any project, commercial or otherwise, as long as
 * you mention my name in the documentation for the program (perhaps in
 * an "about" box, or a credit page, etc).
 *
 * comments/questions to chesnutt at gmail dot com
 *
 * $Header: /home/cvs//dancinglinks/DancingLinksSudoku.java,v 1.3 2006/01/03 23:29:45 chesnutt Exp $
 */
import java.util.List;
import java.util.ArrayList;


public class DancingLinksSudoku
{
    DancingLinksArena dla;
    SudokuSolutionHandler handler;

    private class Handler implements SolutionHandler {
        public void handleSolution(int rowIndex[]) {
            int solution[][] = new int[9][9];

            for (int i = 0; i < 81; i++) {
                int value = rowIndex[i];
                int digit, row, column;

                digit = value % 9;
                value = value / 9;
                column = value % 9;
                value = value / 9;
                row = value % 9;

                solution[row][column] = digit + 1;
            }

            handler.handleSolution(solution);
        }
    }

    public DancingLinksSudoku(int [][]puzzle, SudokuSolutionHandler handler) {
        /*
         * The data row for the Sudoku sparse matrix looks like:
         *  <1-81>: the cell position in question: (row * 9 + column)
         *        <1-81>: the row constraint (row# * 9 + digit)
         *              <1-81>: the column constraint (column# * 9 + digit)
         *                    <1-81>: the 3x3 box constraint (box# * 9 + digit)
         *
         * yielding a grand total of 324 columns
         */
        int labels[];
        int rowData[];
        List givenList = new ArrayList();

        labels = new int[324];

        for (int i = 0; i < 324; i++)
            labels[i] = i + 1;

        dla = new DancingLinksArena(labels);
        rowData = new int[4];

        for (int row = 0; row < 9; row++)
            for (int column = 0; column < 9; column++)
                for (int digit = 0; digit < 9; digit++) {
                    int boxrow, boxcol;
                    boolean isGiven;
                    Node newRow;

                    /*
                     * See if the square is already filled with the
                     * digit of interest.  If so, the row that
                     * describes this value will be removed later
                     * (also removing any other interfering rows)
                     */
                    isGiven = (puzzle[row][column] == (digit + 1));

                    /*
                     * Compute the four constraint column numbers
                     */
                    rowData[0] = 1 + (row * 9 + column);
                    rowData[1] = 1 + 81 + (row * 9 + digit);
                    rowData[2] = 1 + 81 + 81 + (column * 9 + digit);
                    boxrow = row / 3;
                    boxcol = column / 3;
                    rowData[3] = 1 + 81 + 81 + 81 +
                        ((boxrow * 3 + boxcol) * 9 + digit);

                    /*
                     * Add this row to the sparse matrix
                     */
                    newRow = dla.addInitialRow(rowData);

                    /*
                     * And, if it is one of the "givens", add it to our
                     * collection of constraints
                     */
                    if (isGiven) {
                        givenList.add(newRow);
                    }
                }

        /*
         * Remove all of the "givens".  They are removed after all of
         * the entries are added, so that the interfering rows can be
         * removed as well.
         */
        dla.removeInitialSolutionSet(givenList);

        this.handler = handler;
    }

    public void solveit() {
        dla.solve(new Handler());
    }
}
