package problems.miscellaneous;

// https://leetcode.com/problems/sum-root-to-leaf-numbers/

class TreeNode {
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

public class SumRootLeafNumbers {
    public int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }

    int dfs(TreeNode root, int sum) {
        return sum
                + (root.left != null ? dfs(root.left, sum*10):0)
                + (root.right != null ? dfs(root.right, sum):0)
                + root.val;
    }

    private static class IntHolder {
        int num;

        IntHolder() {
            num=0;
        }
    }

    public static void main(String... args) {
        System.out.println();
    }
}
