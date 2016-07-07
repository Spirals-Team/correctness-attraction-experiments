

package lcs;


public class LCS {
    public static java.lang.String lcs(java.lang.String a, java.lang.String b) {
        int[][] lengths = new int[(a.length()) + 1][(b.length()) + 1];
        for (int i = 0; i < (a.length()); i++)
            for (int j = 0; j < (b.length()); j++)
                if ((a.charAt(i)) == (b.charAt(j)))
                    lengths[(i + 1)][(j + 1)] = (lengths[i][j]) + 1;
                else
                    lengths[(i + 1)][(j + 1)] = java.lang.Math.max(lengths[(i + 1)][j], lengths[i][(j + 1)]);
                
        java.lang.StringBuffer sb = new java.lang.StringBuffer();
        for (int x = a.length(), y = b.length(); (x != 0) && (y != 0);) {
            if ((lengths[x][y]) == (lengths[(x - 1)][y]))
                x--;
            else if ((lengths[x][y]) == (lengths[x][(y - 1)]))
                y--;
            else {
                assert (a.charAt((x - 1))) == (b.charAt((y - 1)));
                sb.append(a.charAt((x - 1)));
                x--;
                y--;
            }
        }
        return sb.reverse().toString();
    }
}

