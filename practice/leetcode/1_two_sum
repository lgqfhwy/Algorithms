// https://leetcode-cn.com/problems/two-sum/
class Solution {
public:
    vector<int> twoSum(vector<int>& nums, int target) {
        unordered_map<int,int> m;
        for (int i = 0; i < nums.size(); ++i) {
            int cur = target - nums[i];
            if (m.count(cur)) {
                return {i, m[cur]};
            } else {
                m[nums[i]] = i;
            }
        }
        return {};
    }
};