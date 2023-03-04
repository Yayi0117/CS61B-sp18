package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    private class bufferIterator implements Iterator<T> {
        private int pointer;
        public bufferIterator() {
            pointer = 0;
        }
        public boolean hasNext() {
            return (pointer != last);
        }
        public T next() {
            T returnItem = rb[pointer];
            if (pointer == (capacity - 1)) {
                pointer = 0;
            } else {
                pointer += 1;
            }
            return returnItem;
        }
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        } else {
            rb[last] = x;
            if (last == (capacity - 1)) {
                last = 0;
            } else {
                last += 1;
            }
            fillCount += 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer overflow");
        } else {
            T result = rb[first];
            rb[first] = null;
            if (first == (capacity - 1)) {
                first = 0;
            } else {
                first += 1;
            }
            fillCount -= 1;
            return result;
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (first == last) {
            throw new RuntimeException("Ring buffer overflow");
        } else {
            return rb[first];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new bufferIterator();
    }
}
