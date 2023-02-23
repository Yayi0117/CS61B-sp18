import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {
    @Test
    public void testStudentArrayDeque() {
        for (int i = 0; i < 50; i += 1) {
            String message = "";
            StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
            ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
            Integer actual;
            Integer expect;
            double numberBetweenZeroAndOne1 = StdRandom.uniform();
            if (numberBetweenZeroAndOne1 < 0.5) {
                int random1 = StdRandom.uniform(9);
                while (random1!=0){
                    student.addLast(random1);
                    solution.addLast(random1);
                    message += String.format("addLast(%d)\n",random1);
                    random1--;
                }
            } else {
                int random2 = StdRandom.uniform(9);
                while (random2!=0){
                    student.addFirst(random2);
                    solution.addFirst(random2);
                    message += String.format("addFirst(%d)\n",random2);;
                    random2--;
                }
            }
            double numberBetweenZeroAndOne2 = StdRandom.uniform();
            if (numberBetweenZeroAndOne2 < 0.5) {
                int random3 = StdRandom.uniform(9);
                while ((random3!=0) && (!student.isEmpty())){
                    actual = student.removeLast();
                    expect = solution.removeLast();
                    if (actual == null) {
                        // Skip this iteration of the loop
                        continue;
                    }
                    message += String.format("removeLast(): %d\n",actual);
                    assertEquals(message,expect,actual);
                    random3--;
                }
            } else {
                int random4 = StdRandom.uniform(9);
                while ((random4!=0) && (!student.isEmpty())){
                    actual = student.removeFirst();
                    expect = solution.removeFirst();
                    if (actual == null) {
                        // Skip this iteration of the loop
                        continue;
                    }
                    message += String.format("removeFirst(): %d\n",actual);
                    assertEquals(message,expect,actual);
                    random4--;
                }
            }
        }
    }
}
