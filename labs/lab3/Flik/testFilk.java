import static org.junit.Assert.*;

import org.junit.Test;

public class testFilk {
    @Test
    public void testIsSameNumber () {
        boolean test1 = Flik.isSameNumber(5, 5);
        assertTrue(test1);
        boolean test2 = Flik.isSameNumber(4,44);
        assertFalse((test2));
        boolean test3 = Flik.isSameNumber(127, 127);
        assertTrue(test3);
        boolean test4 = Flik.isSameNumber(128, 128);
        assertTrue(test4);
    }


}