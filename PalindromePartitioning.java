public class PalindromePartitioning {

    static boolean isPalindrome(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i++) != s.charAt(j--))
                return false;
        }
        return true;
    }

    static int minCuts(String s) {
        int n = s.length();
        int[] cuts = new int[n];

        for (int i = 0; i < n; i++) {
            cuts[i] = i;
            for (int j = 0; j <= i; j++) {
                if (isPalindrome(s, j, i)) {
                    cuts[i] = j == 0 ? 0 : Math.min(cuts[i], cuts[j - 1] + 1);
                }
            }
        }
        return cuts[n - 1];
    }

    public static void main(String[] args) {
        String s = "aab";
        System.out.println("Min cuts for \"" + s + "\": " + minCuts(s));

        s = "racecarannakayak";
        System.out.println("Min cuts for \"" + s + "\": " + minCuts(s));
    }
}