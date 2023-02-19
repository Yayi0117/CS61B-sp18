public class ArrayDeque<T>{
    private T[] items;
    private int size;
    private int nextFirst, nextLast;

    public ArrayDeque(){
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 0;
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize(int cap) {
        T[] a = (T[]) new Object[cap];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    public void addFirst(T item){
        if (size == items.length) {
            resize(size * 2);
        }
        if (isEmpty()){
            nextLast = nextLast+1;
            if (nextLast == items.length){
                nextLast = 0;
            }
        }
        items[nextFirst] = item;
        nextFirst = nextFirst - 1;
        if (nextFirst<0){
            nextFirst = items.length-1;
        }
        size = size + 1;
    }
    public void addLast(T item){
        if (size == items.length) {
            resize(size * 2);
        }
        if (isEmpty()){
            nextFirst = nextFirst - 1;
            if (nextFirst<0){
                nextFirst = items.length-1;
            }
        }
        items[nextLast] = item;
        nextLast = nextLast + 1;
        if (nextLast == items.length){
            nextLast = 0;
        }
        size = size + 1;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        int f = nextFirst;
        int l = nextLast;
        while((f+1)!=l){
            if ((f+1)== items.length){
                f = -1;
            }
            System.out.print(items[f+1]+" ");
            f = f+1;
        }
    }

    private void spaceCheck(){
        double usageRatio = size/items.length;
        if (usageRatio<0.25){
            resize(size*4);
        }
    }

    public T removeFirst(){
        T result;
        if ((nextFirst+1)==items.length){
            nextFirst = 0;
        }else {
            nextFirst = nextFirst + 1;
        }
        result = items[nextFirst];
        items[nextFirst] = null;
        if (items.length>=16){
            spaceCheck();
        }
        size = size-1;
        return result;
    }

    public T removeLast(){
        T result;
        if ((nextLast == 0)&(!isEmpty())){
            nextLast = items.length-1;
        }else {
            nextLast = nextLast - 1;
        }
        result = items[nextLast];
        items[nextLast] = null;
        if (items.length>=16){
            spaceCheck();
        }
        size = size-1;
        return result;
    }
    public T get(int index){
        return items[index];
    }

    /**
     * public static void main(String[] args) {
     *           ArrayDeque<Integer> test = new ArrayDeque<>();
     *           test.addFirst(8);
     *           test.addLast(9);
     *           test.addLast(10);
     *           test.addLast(11);
     *           System.out.println(test.get(3));
     *           test.printDeque();
     *           int x = test.removeFirst();
     *           int y = test.removeLast();
     *       }
      */
}