//#22
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ret = new ArrayList<>();
        process(n, 0,0,"",ret);
        return ret;
    }
    
    public void process(int n, int numOpen, int numClosed, String str, List<String> ret){
        //termination
        if(numOpen == n && numClosed == n){
            ret.add(str);
            return;
        }
        
        
        //recurse next
        if(numOpen < n ) {
            process(n, numOpen+1, numClosed, str + "(" , ret);
        }
        
        if(numOpen > numClosed){
            process(n, numOpen, numClosed+1, str + ")", ret);
        }
    }
}

//#46
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();
        backtrack(nums,ret, tmp);
        return ret;
    }
    
    
    public void backtrack(int[] nums, List<List<Integer>> ret, List<Integer> tmp){
        //base line
        if(tmp.size() == nums.length){
            ret.add(new ArrayList<Integer>(tmp));
            //ret.add(tmp);
            return;
        }
        //recurse next
        for(int num : nums ){
            if(tmp.contains(num)) continue;
            tmp.add(num);
            backtrack(nums, ret, tmp);
            tmp.remove(tmp.size()-1);
        }
    }
}


//#215
class Solution {
    
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    public int findKthLargest(int[] nums, int k) {
        
        for(int num : nums){
            if(pq.size() < k){
                pq.offer(num);
            } else {
                if(num > pq.peek()){
                    pq.poll();
                    pq.offer(num);
                }
            }
        }
        
        
        return pq.peek();
    }
}



class Solution {
    public boolean isSymmetric(TreeNode root) {
        if(root == null) return true;
        return compare(root.left, root.right);        
    }
    public boolean compare(TreeNode a, TreeNode b){
        if(a==null && b==null) return true;
        if(a==null || b==null) return false;
        if(a.val != b.val) return false;    
        //계속 탐색을 해야한다.
        return compare(a.left, b.right) && compare(a.right, b.left);
    }
}
