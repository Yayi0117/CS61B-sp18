/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int maxLength = 0;
        String[] copy = new String[asciis.length];
        int copyIndex = 0;
        for (String string : asciis) {
            if (string.length() > maxLength) {
                maxLength = string.length();
            }
            copy[copyIndex] = string;
            copyIndex++;
        }
        for (int d = maxLength - 1; d >= 0; d--) {
            sortHelperLSD(copy, d);
        }
        return copy;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        int max = 255;
        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (String string : asciis) {
            if ((string.length() - 1) < index) {
                counts[0]++;
            } else {
                int i = (int) string.charAt(index);
                counts[i]++;
            }
        }
        // counting sort that uses start position calculation
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }
        String[] copied = new String[asciis.length];
        for (int i = 0; i < asciis.length; i += 1) {
            copied[i] = asciis[i];
        }
        for (int i = 0; i < asciis.length; i += 1) {
            String string = copied[i];
            int item = 0;
            if ((string.length() - 1) >= index) {
                item = (int) string.charAt(index);
            }
            int place = starts[item];
            asciis[place] = string;
            starts[item] += 1;
        }
    }


    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
