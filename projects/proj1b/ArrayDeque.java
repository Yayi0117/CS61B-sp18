public class ArrayDeque <T> implements Deque<T> {
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
        if (size!=0) {
            if ((nextFirst > nextLast) || size == items.length) {
                System.arraycopy(items, (nextFirst + 1), a, 0, (items.length - (nextFirst + 1)));
                System.arraycopy(items, 0, a, (items.length - (nextFirst + 1)), (nextFirst + 1));
            } else {
                System.arraycopy(items, (nextFirst + 1), a, 0, size);
            }
            nextLast = size;
            nextFirst = cap -1;
        }else{
            nextLast = 0;
            nextFirst = 0;
        }
        items = a;
    }

    @Override
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
    @Override
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

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
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
        if ((usageRatio<0.25)&&(usageRatio!=0)){
            resize(size*4);
        }else  if (usageRatio==0) {
            resize(8);
        }
    }

    @Override
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
            size = size - 1;
            if (items.length >= 16) {
                spaceCheck();
            }
            if (size==0){
                nextLast = nextFirst;
            }
            return result;
        } else {
            return null;
        }
    }

    @Override
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
            size = size - 1;
            if (items.length >= 16) {
                spaceCheck();
            }
            if (size==0){
                nextFirst = nextLast;
            }
            return result;
        }else{
            return null;
        }
    }

    @Override
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

}
