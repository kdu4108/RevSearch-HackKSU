/**
 *  NOTE: THE ORIGINAL FILE WAS EDITED BY ME TO INSTEAD COUNT THE NUMBER OF
 *  INSTANCES OF A SUBSTRING IN A STRING (RATHER THAN FIND FIRST OCCURENCE).
 * 
 *  The <tt>BoyerMoore</tt> class finds the first occurrence of a pattern string
 *  in a text string.
 *  <p>
 *  This implementation uses the Boyer-Moore algorithm (with the bad-character
 *  rule, but not the strong good suffix rule).
 *  <p>
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/53substring">Section 5.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */

//import java.io.FileReader;
//import java.io.FileNotFoundException;

public class BoyerMoore {
    private final int R;     // the radix
    private int[] right;     // the bad-character skip array

    private char[] pattern;  // store the pattern as a character array
    private String pat;      // or as a string

    /**
     * Preprocesses the pattern string.
     *
     * @param pat the pattern string
     */
    public BoyerMoore(String pat) {
        this.R = 256;
        this.pat = pat;

        // position of rightmost occurrence of c in the pattern
        right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < pat.length(); j++)
            right[pat.charAt(j)] = j;
    }


    /**
     * Returns the index of the first occurrrence of the pattern string
     * in the text string.
     *
     * @param  txt the text string
     * @return the index of the first occurrence of the pattern string
     *         in the text string; N if no such match
     */
    public int search(String txt, int start) {
        int M = pat.length();
        int N = txt.length();
        int skip;
        for (int i = start; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M-1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i+j)) {
                    skip = Math.max(1, j - right[txt.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0) return i;    // found
        }
        return N;                       // not found
    }


    /**
     * Takes a pattern string and an input string as command-line arguments;
     * searches for the pattern string in the text string; and prints
     * the first occurrence of the pattern string in the text string.
     */
    public static void main(String[] args) { //throws FileNotFoundException {
 /*       String pat = args[0]; // pattern (searching for this)
        In in = new In("sample.txt");
        String txt = in.readAll();

        BoyerMoore boyermoore = new BoyerMoore(pat);
        
        int count = 0;
        int startindex = 0;
        int offset = boyermoore.search(txt, startindex);
        //if (offset != txt.length()) count++;
        while (offset < txt.length()) {
            startindex = offset + 1;
            offset = boyermoore.search(txt, startindex);
            count++;
        }
        
        System.out.println(count);*/
    }
}