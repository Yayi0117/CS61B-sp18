public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> someDeque = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            someDeque.addLast(word.charAt(i));
        }
        return someDeque;
    }

    private boolean compareChar(Deque<Character> lld) {
        if (lld.size() > 1) {
            char first = lld.removeFirst();
            char last = lld.removeLast();
            if (first != last) {
                return false;
            }
            return compareChar(lld);
        } else {
            return true;
        }
    }

    public boolean isPalindrome(String word) {
        Deque<Character> lld = wordToDeque(word);
        return compareChar(lld);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        boolean result = true;
        for (int i = 0; i < (word.length() / 2); i++) {
            boolean cmp = cc.equalChars(word.charAt(i), word.charAt(word.length() - 1 - i));
            if (!cmp) {
                return false;
            }
        }
        return result;
    }
}
