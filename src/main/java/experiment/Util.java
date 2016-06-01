package experiment;

/**
 * Created by spirals on 12/04/16.
 */
public class Util {

    public static String getStringPerc(long nb, long total) {
        double perc = perc(nb, total);
        String ret = dash(perc);
        return ret + " " + String.format("%.2f", perc);
    }

    public static double perc(long nb, long total) {
        return (double) nb / (double) total * 100;
    }

    public static String dash(double perc) {
        String dash = "";
        for (int d = 0; d < perc / 5; d++) dash += "-";
        return dash;
    }

}
