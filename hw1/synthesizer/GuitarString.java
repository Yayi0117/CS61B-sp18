package synthesizer;
import java.util.HashSet;
import java.util.Set;

//Make sure this class is public
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;
    private int cap;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        cap = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer<>(cap);
        for (int i = 0; i < cap; i++)  {
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        while (!buffer.isEmpty()) {
            buffer.dequeue();
        }
        Set<Double> randomNumber = new HashSet<>();
        while (!buffer.isFull())  {
            double r = Math.random() - 0.5;
            while (randomNumber.contains(r)) {
                r = Math.random() - 0.5;
            }
            randomNumber.add(r);
            buffer.enqueue(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic() {
        double item = buffer.dequeue();
        double newItem = DECAY * ((item + buffer.peek()) / 2);
        buffer.enqueue(newItem);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
