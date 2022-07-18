package problems.miscellaneous;

// https://leetcode.com/problems/house-robber-iii/
public class HouseRobberThree {

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int rob(TreeNode root) {
        int[] rootans = solve(root);
        return Math.max(rootans[0], rootans[1]);
    }

    /**
     * 0th value is when the root node is included. 1st value is when root node is not included.
     */
    private int[] solve(TreeNode root) {
        if(root == null) return new int[]{0,0}; // When node is null

        if(root.left == null && root.right==null) { // When it's a leaf node
            return new int[]{root.val, 0};
        }

        int[] rightSolve = solve(root.right);
        int[] leftSolve = solve(root.left);

        return new int[]{
                // When root is included, you MUST NOT take value where left or right child is included.
                root.val + leftSolve[1] + rightSolve[1],

                // When root is not included, we can take the left child or not, we have two choices. We choose max.
                // Similarly, for the right child.
                Math.max(leftSolve[0], leftSolve[1]) + Math.max(rightSolve[0], rightSolve[1])};
    }
}
