package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer<>(6);
        arb.enqueue(33.1); // 33.1 null null  null
        arb.enqueue(44.8); // 33.1 44.8 null  null
        arb.enqueue(62.3); // 33.1 44.8 62.3  null
        arb.enqueue(-3.4); // 33.1 44.8 62.3 -3.4
        double actual1 = arb.dequeue();     // 44.8 62.3 -3.4  null (returns 33.1)
        double expect1 = 33.1;
        assertEquals(expect1,actual1,0);
        arb.enqueue(33.1); // 44.8 62.3 -3.4 33.1
        arb.enqueue(30.4); // 44.8 62.3 -3.4 33.1 30.4
        arb.enqueue(27.4); // 44.8 62.3 -3.4 33.1 30.4 27.4
        arb.dequeue(); // 62.3 -3.4 33.1 30.4 27.4
        double actual2 = arb.peek();
        double expect2 = 62.3;
        assertEquals(expect2,actual2,0);
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
