public class LinkedListDeque < T > {
    private class Items {
        public T item;
        public Items prev, next;

        public Items (T i, Items p, Items n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private Items sentinel;
    private int size;

    public LinkedListDeque(){
        sentinel = new Items(null,null,null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public void addFirst(T item){
        Items newNode = new Items(item,sentinel,sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size += 1;
    }

    public void addLast(T item){
        Items newNode = new Items(item,sentinel.prev,sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size += 1;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        Items current = sentinel.next;
        while(current!=sentinel){
            System.out.print(current.item+" ");
            current = current.next;
        }
    }

    public T removeFirst(){
        if (size!=0) {
            T result = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size = size - 1;
            return result;
        }else{
            return null;
        }
    }

    public T removeLast(){
        if (size!=0) {
            T result = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size = size - 1;
            return result;
        }else{
            return null;
        }
    }

    public T get(int index){
        if ((size==0)|(index>=size)){
            return null;
        }else{
            Items current = sentinel.next;
            int i = 0;
            while(i!=index){
                current = current.next;
                i +=1;
            }
            return current.item;
        }
    }

    private T helper(Items t, int index){
        if(index==0){
            return t.next.item;
        }else{
            return helper(t.next,index-1);
        }
    }
    public T getRecursive(int index){
        if ((size==0)|(index>=size)){
            return null;
        }else{
            return helper(sentinel, index);
        }
    }
/*
     public static void main(String[] args) {
          //LinkedListDeque<Integer> test = new LinkedListDeque<>();
          //test.addFirst(8);
          //test.addLast(9);
          //test.printDeque();
          //System.out.println(test.getRecursive(1));
         LinkedListDeque<Integer> test1 = new LinkedListDeque<>();
         test1.addLast(0);
         test1.addLast(1);
         int a = test1.removeFirst();
         test1.addLast(3);
         int b = test1.removeFirst();
         test1.addLast(4);
         int c = test1.removeLast();
     }

 */
}
