// https://leetcode-cn.com/problems/merge-two-sorted-lists/
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
    ListNode* mergeTwoLists(ListNode* list1, ListNode* list2) {
        ListNode* ret = new ListNode(0);
        ListNode* first = ret;
        while (list1 != NULL && list2 != NULL) {
            if (list1->val < list2->val) {
                first->next = list1;
                list1 = list1->next;
            } else {
                first->next = list2;
                list2 = list2->next;
            }
            first = first->next;
        }
        while (list1) {
            first->next = list1;
            list1 = list1->next;
            first = first->next;
        }
        while (list2) {
            first->next = list2;
            list2 = list2->next;
            first = first->next;
        }
        return ret->next;
    }
};