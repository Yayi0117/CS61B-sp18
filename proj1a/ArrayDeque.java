public class ArrayDeque <T> {
    private T[] items;
    private int size;
    private int nextFirst, nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 0;
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize(int cap) {
        T[] a = (T[]) new Object[cap];
        if (nextFirst != items.length-1) {
            System.arraycopy(items,(nextFirst+1), a, 0, (items.length-(nextFirst+1)));
            System.arraycopy(items,0, a, (items.length-(nextFirst+1)), (nextFirst+1));
        }else{
            System.arraycopy(items,0, a, 0, size);
        }
        nextFirst = cap -1;
        nextLast = size;
        items = a;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (isEmpty()) {
            nextLast = nextLast + 1;
            if (nextLast == items.length) {
                nextLast = 0;
            }
        }
        items[nextFirst] = item;
        nextFirst = nextFirst - 1;
        if (nextFirst < 0) {
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
        double usageRatio = ((double) size)/items.length;
        if (usageRatio<0.25){
            resize(size*4);
        }
    }

    public T removeFirst(){
        T result;
        if (size!=0) {
            if ((nextFirst + 1) == items.length) {
                nextFirst = 0;
            } else {
                nextFirst = nextFirst + 1;
            }
            result = items[nextFirst];
            items[nextFirst] = null;
            if (items.length >= 16) {
                spaceCheck();
            }
            size = size - 1;
            if (size==0){
                nextLast = nextFirst;
            }
            return result;
        } else {
            return null;
        }
    }

    public T removeLast() {
        if (size!=0) {
            T result;
            if ((nextLast == 0) & (!isEmpty())) {
                nextLast = items.length - 1;
            } else {
                nextLast = nextLast - 1;
            }
            result = items[nextLast];
            items[nextLast] = null;
            if (items.length >= 16) {
                spaceCheck();
            }
            size = size - 1;
            if (size==0){
                nextFirst = nextLast;
            }
            return result;
        }else{
            return null;
        }
    }
    public T get(int index) {
        if (index<size) {
            if ((nextFirst + 1) == items.length) {
                return items[index];
            }else {
                int newIdex = nextFirst + 1 + index;
                if (newIdex >= items.length){
                    newIdex = newIdex - items.length;
                }
                return items[newIdex];
            }
        }else{
            return null;
        }
    }

     public static void main(String[] args) {
        /*
                ArrayDeque<Integer> test = new ArrayDeque<>();
                test.addFirst(8);
                test.addLast(9);
                test.addLast(10);
                test.addLast(11);
                test.addLast(12);
                test.addLast(13);
                test.addFirst(7);
                test.addFirst(6);
                test.addFirst(5);
                test.addLast(14);
                System.out.println(test.get(3));
                test.printDeque();
                int x = test.removeFirst();
                int y = test.removeLast();
         */
         ArrayDeque<Integer> test1 = new ArrayDeque<>();
         test1.addFirst(0);
         int a = test1.removeLast();
         test1.addFirst(2);
         int b = test1.removeLast();
            }
}
