import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        boolean test1 = palindrome.isPalindrome("noon");
        assertTrue(test1);
        boolean test2 = palindrome.isPalindrome("persiflage");
        assertFalse(test2);
        boolean test3 = palindrome.isPalindrome("aaaaaA");
        assertFalse(test3);
        boolean test4 = palindrome.isPalindrome("");
        assertTrue(test4);
        boolean test5 = palindrome.isPalindrome("a");
        assertTrue(test5);
        assertFalse(palindrome.isPalindrome("cat"));
    }

    @Test
    public void testNewIsPalindrome() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", cc));
        assertFalse(palindrome.isPalindrome("persiflage", cc));
        assertFalse(palindrome.isPalindrome("noon", cc));
        assertTrue(palindrome.isPalindrome("adhcb", cc));
        assertTrue(palindrome.isPalindrome("hjki", cc));
        assertFalse(palindrome.isPalindrome("Flake", cc));
        assertTrue(palindrome.isPalindrome("FlakE", cc));
    }
}
