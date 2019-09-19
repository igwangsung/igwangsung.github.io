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


class Solution {
    public int maxProduct(int[] nums) {
        int[][] d = new int[nums.length][2];
        
        d[0][0] = nums[0];
        d[0][1] = nums[0];
        
        for(int i=1; i < nums.length; i++){
            int c = nums[i];
            d[i][0] = Math.max(c, Math.max(c*d[i-1][0], c*d[i-1][1]));
            d[i][1] = Math.min(c, Math.min(c*d[i-1][0], c*d[i-1][1]));

        }
        
        
        int max = d[0][0];
        for(int i=1;i<nums.length;i++){
            if(max < d[i][0]) max = d[i][0];
        }
        
        
        return max;
    }
}



//merge k sorted list -> database external sort is similar
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
//우선순위 큐 : O(nlogk)
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists==null || lists.length==0) return null;
        ListNode ret = null;
        ListNode cur = null;
        //min heap

        PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.length,
                                                        (a,b)->a.val-b.val);
        
        for(ListNode node : lists){
            if(node != null) pq.offer(node);    
        }
        
        while(!pq.isEmpty()){
            ListNode node = pq.poll();
            if(node.next != null) pq.offer(node.next);
            if(ret == null){
                ret = node;
                cur = node;
            } else {
                cur.next = node;
                cur = node;
            }
        }
        
        return ret;
    }
}


//692. Top K Frequent Words
class Solution {
    public class WordCnt{
        int cnt;
        String word;
        WordCnt(String word){
            this.word = word;
            this.cnt = 1;
        }
    
    }
    
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, WordCnt> map = new HashMap<>();
       for(String word: words){
           if(map.containsKey(word)){
               map.get(word).cnt++;
           } else {
               map.put(word, new WordCnt(word));
           }
       }
        
        
        PriorityQueue<WordCnt> pq = new PriorityQueue<>(k, 
                 (a,b)->a.cnt-b.cnt!=0?a.cnt-b.cnt:b.word.compareTo(a.word));
        
        
        
        for(WordCnt wordCnt : map.values()){
            pq.offer(wordCnt);
            if(pq.size() > k){
                pq.poll();
            }
        }
        
        
        List<String> ret = new ArrayList<String>();
        while(!pq.isEmpty()){
            ret.add(0, pq.poll().word);
        }
        
        return ret;
        
        
    }
}


class Solution {
    public int uniquePaths(int m, int n) {
        int[][] d = new int[m][n];
        d[0][0] = 1;

        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                d[i][j] = d[i][j-1] + d[i-1][j];
            }
        }

        return d[m-1][n-1];       
    }
}



//#886. Possible Bipartition
class Solution {
    public boolean possibleBipartition(int N, int[][] dislikes) {
        List<List<Integer> > adjList = new ArrayList<>();

        for(int i = 0; i < N; i++){
            adjList.add(new ArrayList<>());
        }

        boolean[] visited = new boolean[N];
        boolean[] color = new boolean[N];

        for(int[] dislike : dislikes){
            int a = dislike[0] - 1;
            int b = dislike[1] - 1;

            adjList.get(a).add(b);
            adjList.get(b).add(a);

        }   

        for(int i =0; i < N; i++){
            if(!visited[i]){
                visited[i] = true;
                boolean res = isBipartiteDfs(i, adjList, visited, color);
                if(!res) return false;
            }
        }

        return true;
    }

    private boolean isBipartiteDfs(int cur, List<List<Integer> > adjList,
                boolean[] visited, boolean[] color) {
            for(int next : adjList.get(cur)){
                if(!visited[next]){
                    visited[next] = true;
                    color[next] = !color[cur];
                    boolean res = isBipartiteDfs(next, adjList, visited, color);
                    if(!res) return false;
                } else if(color[next] == color[cur]) {
                    return false;
                }
            }
        
        return true;
    }
} 
