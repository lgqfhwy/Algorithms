// https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode() : val(0), left(nullptr), right(nullptr) {}
 *     TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
 *     TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
 * };
 */
class Solution {
public:
    vector<int> inorderTraversal(TreeNode* root) {
        if (!root) {
            return {};
        }
        stack<TreeNode*> st;
        st.push(root);
        vector<int> ret;
        TreeNode* cur = NULL;
        while (!st.empty()) {
            cur = st.top();
            while (cur && cur->left) {
                st.push(cur->left);
                TreeNode* cur2 = cur;
                cur = cur->left;
                cur2->left = NULL;        
            }
            cur = st.top();
            st.pop();
            ret.emplace_back(cur->val);
            cur = cur->right;
            if (cur) {
                st.push(cur);
            }
        }
        return ret;

    }
};


class Solution {
public:
    vector<int> inorderTraversal(TreeNode* root) {
        if (!root) {
            return {};
        }
        stack<TreeNode*> st;
        vector<int> ret;
        while (root  || !st.empty()) {
            while (root) {
                st.push(root);
                root = root->left;
            }
            root = st.top();
            st.pop();
            ret.emplace_back(root->val);
            root = root->right;
        }
        return ret;

    }
};
