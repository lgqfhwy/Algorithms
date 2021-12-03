// https://leetcode-cn.com/problems/single-number/
class Solution {
public:
    int singleNumber(vector<int>& nums) {
        int ret = 0;
        for (const int &n : nums) {
            ret = ret ^ n;
        }
        return ret;
    }
};