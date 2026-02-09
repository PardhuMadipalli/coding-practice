package problems.backtracking;

import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode.com/problems/path-sum-ii/?envType=problem-list-v2&envId=backtracking
 */
public class PathSumII {

    static class TreeNode {
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

    public static TreeNode buildTree(Integer[] nums) {
        if (nums == null || nums.length == 0 || nums[0] == null) {
            return null;
        }

        // Create the root node with the first element
        TreeNode root = new TreeNode(nums[0]);
        // Use a queue to keep track of parent nodes
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;
        while (i < nums.length && !queue.isEmpty()) {
            // Pop the front node from the queue to be the current parent
            TreeNode curr = queue.poll();

            // Process the left child
            if (i < nums.length) {
                Object leftVal = nums[i];
                if (leftVal != null) {
                    curr.left = new TreeNode((int) leftVal);
                    queue.add(curr.left); // Add the new left child to the queue
                }
                i++;
            }

            // Process the right child
            if (i < nums.length) {
                Object rightVal = nums[i];
                if (rightVal != null) {
                    curr.right = new TreeNode((int) rightVal);
                    queue.add(curr.right); // Add the new right child to the queue
                }
                i++;
            }
        }

        return root;
    }

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList<>();
        if (root != null) {
            List<TreeNode> curr = new ArrayList<>();
            curr.add(root);
            backtrack(root, root.val, targetSum, curr, res);
        }
        return res;
    }

    void backtrack(TreeNode node, int currSum, int targetSum, List<TreeNode> curr, List<List<Integer>> res) {
        if (isLeafNode(node)) {
            if (currSum == targetSum) {
                res.add(curr.stream()
                        .map(currNode -> currNode.val)
                        .toList());
            }
            return;
        }

        if (node.left != null) {
            curr.add(node.left);
            backtrack(node.left, currSum + node.left.val, targetSum, curr, res);
            curr.remove(curr.size() - 1);
        }

        if (node.right != null) {
            curr.add(node.right);
            backtrack(node.right, currSum + node.right.val, targetSum, curr, res);
            curr.remove(curr.size() - 1);
        }
      }

    boolean isLeafNode(TreeNode node) {
        return node.left == null && node.right == null;
    }

    public static void main(String[] args) {
        TreeNode root = buildTree(new Integer[]{5,4,8,11,null,13,4,7,2,null,null,5,1});
        int targetSum = 22;

//        TreeNode root = buildTree(new Integer[]{});
//        int targetSum = 0;

        System.out.println(new PathSumII().pathSum(root, targetSum));
    }
}
