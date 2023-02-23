import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {
    @Test
    public void testStudentArrayDeque() {
        String message = "";
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        /** @source StudentArrayDequeLauncher  */
        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne1 = StdRandom.uniform();
            Integer actual;
            Integer expect;
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
                while ((random3!=0) && (student.size()>1)){
                    Integer result = student.removeLast();
                    solution.removeLast();
                    message += String.format("removeLast():%d\n",result);
                    random3--;
                }
                actual = student.removeLast();
                expect = solution.removeFirst();
            } else {
                int random4 = StdRandom.uniform(9);
                while ((random4!=0) && (student.size()>1)){
                    Integer result = student.removeFirst();
                    solution.removeFirst();
                    message += String.format("removeFirst():%d\n",result);;
                    random4--;
                }
                actual = student.removeFirst();
                expect = solution.removeFirst();
            }
            assertEquals(message,actual,expect);
        }
    }
}
