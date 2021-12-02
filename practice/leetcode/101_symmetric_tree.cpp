// https://leetcode-cn.com/problems/symmetric-tree/

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
    bool isSymmetric(TreeNode* root) {
        if (!root) {
            return true;
        }
        if (!root->left && !root->right) {
            return true;
        }
        if (!root->left || !root->right) {
            return false;
        }
        if (root->left->val != root->right->val) {
            return false;
        }
        return is_two_symmetric(root->left->left, root->right->right) && is_two_symmetric(root->left->right, root->right->left);
    }
    bool is_two_symmetric(TreeNode* left, TreeNode* right) {
        if (!left && !right) {
            return true;
        }
        if (!left || !right) {
            return false;
        }
        if (left->val != right->val) {
            return false;
        }
        return is_two_symmetric(left->left, right->right) && is_two_symmetric(left->right, right->left);
    }
};


class Solution {
public:
    bool isSymmetric(TreeNode* root) {
        return is_two_symmetric(root, root);
    }
    bool is_two_symmetric(TreeNode* left, TreeNode* right) {
        if (!left && !right) {
            return true;
        }
        if (!left || !right) {
            return false;
        }
        if (left->val != right->val) {
            return false;
        }
        return is_two_symmetric(left->left, right->right) && is_two_symmetric(left->right, right->left);
    }
};