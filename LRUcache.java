class LRUCache {

    public class CacheItem{
        CacheItem prev;
        CacheItem next;
        int value;
        int key;
        public CacheItem(int key, int value){
            this.key = key;
            this.value = value;
        }
    }
    
    CacheItem head;
    CacheItem tail;
    Map<Integer, CacheItem>map;
    int capacity;
    
    public LRUCache(int capacity) {
        head = null;
        tail = null;
        map = new HashMap<>();
        this.capacity = capacity;
    }
    //{3,3} - {2,2} - {4.4}
    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }else {
            //head - ... - cur.prev - cur - cur.next
            //cur - head - ... cur.prev - cur.next
            
            CacheItem cur = map.get(key);
            if(cur != head){
                if(cur == tail){
                    tail = tail.prev;
                }
                if(cur.prev != null ) cur.prev.next = cur.next;
                if(cur.next != null ) cur.next.prev =  cur.prev;
               
                cur.next = head;
                head.prev = cur;
                cur.prev = null;
                head = cur;
                
                
            }
            
                    
            return cur.value;
        }
    }
    
    public void put(int key, int value) {
        if(get(key) == -1){
            //insert
            CacheItem cur = new CacheItem(key,value);

            if(head == null ){
                head = cur;
                tail = cur;
            } else {
                cur.next = head;
                head.prev = cur;
                head = cur;
            }
            
            map.put(key, cur);
            if(map.size() > capacity) {
                map.remove(tail.key);
                tail.prev.next = null;
                tail = tail.prev;
                
            } else {
                
            }
            
        }else{
            //update
            map.get(key).value = value;
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */