package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /*
     * that shows the flaw in the hashCode function.
     */
    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();
        List<Integer> params1 = new ArrayList<>();
        params1.add(1);
        params1.add(2);
        params1.add(3);
        Oomage ooA1 = new ComplexOomage(params1);
        deadlyList.add(ooA1);
        List<Integer> params2 = new ArrayList<>();
        params2.add(3);
        params2.add(2);
        params2.add(1);
        Oomage ooA2 = new ComplexOomage(params2);
        deadlyList.add(ooA2);
        List<Integer> params3 = new ArrayList<>();
        params3.add(1);
        params3.add(3);
        params3.add(2);
        Oomage ooA3 = new ComplexOomage(params3);
        deadlyList.add(ooA3);
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
