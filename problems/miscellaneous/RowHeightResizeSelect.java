package problems.miscellaneous;

import java.util.stream.IntStream;

// Amazon interview Question
// Write a data structure that has
//  1) Fixed number of rows
//  2) Height of any row can be adjusted efficiently
//  3) Row index can be identified efficiently from pixel y co-ordinate

public class RowHeightResizeSelect {

    Row root;
    Row[] rowsArray;

    RowHeightResizeSelect(final int numberOfRows) {
        int[] temp = new int[numberOfRows];
        rowsArray = new Row[numberOfRows];

        for(int i=0; i<numberOfRows; i++) {
            temp[i]=i;
        }
        root = sortedArrayToBST(temp, 0, numberOfRows-1);
        computeInitialSum(root);
    }

    /**
     * takes o(log n) time
     * @param y coordinate of the pixel (starting from 1)
     * @return row number (0 indexed)
     */
    public int select(int y) {
        if(y > root.sum) throw new RuntimeException("Invalid size");
        return select(y, this.root);
    }

    int select(int v, Row root) {
        if(root.left!=null) {
            if(v<=root.left.sum)
                return select(v, root.left);
            else if(v <= (root.height + root.left.sum))
                return root.index;
            else
                return select(v-(root.left.sum+ root.height), root.right);
        } else {
            if(v <= root.height) return root.index;
            else
                return select(v- root.height, root.right);
        }
    }

    /**
     * Resize a given row to new height
     * takes o(log n) time
     * @param row index, it's new height
     */
    public void resize(int row, int newHeight) {
        Row rowNode = rowsArray[row];
        int diff = newHeight-rowNode.height;
        rowNode.height = newHeight;
        incrementSumsForSelfAndParent(rowNode, diff);
    }

    private void incrementSumsForSelfAndParent(Row row, int diff) {
        row.sum+=diff;
        if(row.parent!=null)
            incrementSumsForSelfAndParent(row.parent, diff);
    }


    private int computeInitialSum(Row root) {
        if(root==null) return 0;
        root.sum = computeInitialSum(root.left) + computeInitialSum(root.right) + root.height;
        return root.sum;
    }

    private static class Row {
        int index;
        int sum;
        int height;

        Row left;
        Row right;
        Row parent;

        Row(int index, int height) {
            this.index=index;
            this.height = height;
        }
    }

    /**
     * Arrange the input indexs (0..n-1) in a balanced tree format
     *
     * @return root node
     */
    private Row sortedArrayToBST(int[] arr, int start, int end) {

        /* Base Case */
        if (start > end) {
            return null;
        }

        /* Get the middle element and make it root */
        int mid = (start + end) / 2;
        Row node = new Row(arr[mid], 1);
        rowsArray[mid] = node;

        /* Recursively construct the left subtree and make it
         left child of root */
        node.left = sortedArrayToBST(arr, start, mid - 1);
        if(node.left!=null)
            node.left.parent = node;

        /* Recursively construct the right subtree and make it
         right child of root */
        node.right = sortedArrayToBST(arr, mid + 1, end);
        if(node.right!=null)
            node.right.parent = node;

        return node;
    }

    /**
     * Driver program
     *
     * @param args
     */
    public static void main(String[] args) {
        RowHeightResizeSelect rowHeightResizeSelect = new RowHeightResizeSelect(10);

        IntStream.range(1, 11).forEach(i -> System.out.println(rowHeightResizeSelect.select(i)));
        rowHeightResizeSelect.resize(1, 3);
        rowHeightResizeSelect.resize(8, 6);
        System.out.println("resized");
        IntStream.range(1, 18).forEach(i -> System.out.println(rowHeightResizeSelect.select(i)));
    }
}
