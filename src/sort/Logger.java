package sort;

import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by spirals on 30/03/16.
 */
public class Logger {

    private static String format = "%-8s %-8s %-8s %-8s %-27s%n";

    public static double perc(int nb, int total) {
        return (double)nb / (double) total * 100;
    }

    static {
        try {
            FileWriter writer = new FileWriter("results/result_global" , false);
            writer.write(String.format(format, "Index","#Success", "#Failure", "#Total","%Success"));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(Result [] results, int valueToAdd) {
        try {

            FileWriter writerGlobal = new FileWriter("results/result_global" , true);
            FileWriter writerArray = new FileWriter("results/result_"+valueToAdd , false);

            int sumSuccess = 0;
            int sumFailure = 0;

            writerArray.write(String.format(format, "Array","#Success", "#Failure", "#Total","%Success"));

            for (int i = 0 ; i < results.length ; i++) {
                double perc = perc(results[i].numberOfSuccess, results[i].total());
                String dash = "";
                for (int d = 0 ; d < perc / 5 ; d++) dash += "-";
                writerArray.write(String.format(format, i, results[i].numberOfSuccess, results[i].numberOfFailure,
                        results[i].total(), dash+" "+String.format("%.2f", perc)));
                sumSuccess += results[i].numberOfSuccess;
                sumFailure += results[i].numberOfFailure;
            }

            double perc = perc(sumSuccess, (sumSuccess+sumFailure));
            String dash = "";
            for (int d = 0 ; d < perc / 5 ; d++) dash += "-";

            writerGlobal.write(String.format(format, valueToAdd, sumSuccess, sumFailure,(sumSuccess+sumFailure),
                    dash+" "+String.format("%.2f", perc)));

            writerGlobal.close();
            writerArray.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
