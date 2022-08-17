package treeandgraph.helpers;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// * Definition for a binary tree node.
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
 

public class TreeFromInAndPost {
    int index = 0;
    HashMap<Integer,Integer> map = new HashMap<>();
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        index = postorder.length-1;
        for(int i=0;i<postorder.length;i++){
            map.put(inorder[i],i);
        }

        return build(postorder,0,index);
    }
    public TreeNode build(int[] postorder,int start,int end){
        if(start>end)
            return null;

        int curr = postorder[index];
        int i = map.get(curr);
        index--;
        TreeNode root = new TreeNode(curr);
        root.right = build(postorder,i+1,end);
        root.left = build(postorder,start,i-1);

        return root;
    }

    public static void main(String[] args) {
        int[] ino = new int[]{1,2,3,4};
        int[] post = new int[]{1,2,3,4};
        new TreeFromInAndPost().buildTree(ino, post);
    }
}
