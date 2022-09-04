

// https://leetcode.com/problems/recover-binary-search-tree/
// https://leetcode.com/problems/recover-binary-search-tree/discuss/2495501/C%2B%2B-oror-Easy-Soln-oror-Defined-variables
class Solution {
    struct {
        int val;
        TreeNode *left;
        TreeNode *right;
        TreeNode() : val(0), left(nullptr), right(nullptr) {}
        TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
        TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
    } TreeNode;

    public:
    TreeNode* first;
    TreeNode* middle;
    TreeNode* last;
    TreeNode* prev;
    void inorder(TreeNode* root)
    {
        if(root == NULL) return;
        inorder(root -> left);
        if(prev && (root->val < prev -> val))
        {
            if(first == NULL){
                first = prev;
                middle = root;
            }
            else last = root;
        }
        prev =root;
        inorder(root -> right);
    }
    void recoverTree(TreeNode* root)
    {
        first = middle = last = NULL;
        prev = new TreeNode(INT_MIN);
        inorder(root);
        if(first && last )swap(first->val, last->val);
        else swap(first->val,middle->val);

    }
};