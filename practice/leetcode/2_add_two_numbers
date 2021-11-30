// https://leetcode-cn.com/problems/add-two-numbers/
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode() : val(0), next(nullptr) {}
 *     ListNode(int x) : val(x), next(nullptr) {}
 *     ListNode(int x, ListNode *next) : val(x), next(next) {}
 * };
 */
class Solution {
public:
    ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {
        ListNode* ret = new ListNode(0);
        ListNode* first = ret;
        int next_val = 0;
        while (l1 != NULL and l2 != NULL) {
            int cur_val = l1->val + l2->val + next_val;
            next_val = 0;
            if (cur_val > 9) {
                cur_val = cur_val - 10;
                next_val += 1;
            }
            ListNode* cur = new ListNode(cur_val);
            first->next = cur;
            first = cur;
            l1 = l1->next;
            l2 = l2->next;
        }
        while (l1 != NULL) {
            int cur_val = l1->val + next_val;
            next_val = 0;
            if (cur_val > 9) {
                cur_val = cur_val - 10;
                next_val += 1;
            }
            ListNode* cur = new ListNode(cur_val);
            first->next = cur;
            first = cur;
            l1 = l1->next;
        }
        while (l2 != NULL) {
            int cur_val = l2->val + next_val;
            next_val = 0;
            if (cur_val > 9) {
                cur_val = cur_val - 10;
                next_val += 1;
            }
            ListNode* cur = new ListNode(cur_val);
            first->next = cur;
            first = cur;
            l2 = l2->next;
        }
        if (next_val > 0) {
            ListNode* cur = new ListNode(next_val);
            first->next = cur;
        }
        return ret->next;
    }
};