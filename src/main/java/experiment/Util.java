package experiment;

import perturbation.location.PerturbationLocation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by spirals on 12/04/16.
 */
public class Util {

    public static String getStringPerc(int nb, int total) {
        double perc = perc(nb, total);
        String ret = dash(perc);
        return ret + " " + String.format("%.2f", perc);
    }

    public static double perc(int nb, int total) {
        return (double) nb / (double) total * 100;
    }

    public static String dash(double perc) {
        String dash = "";
        for (int d = 0; d < perc / 5; d++) dash += "-";
        return dash;
    }

    public static void parseArgs(String[] args) {
        boolean expFound = false;
        for (int i = 0; i < args.length; i++) {
            if (expFound) {
                if (args[i].startsWith("-")) {
                    expFound = false;
                    i--;
                    break;
                }
                Runner.explorers.add(args[i]);
            } else if (args[i].startsWith("-")) {
                switch (args[i].substring(1)) {
                    case "size":
                        Runner.sizeOfEachTask = Integer.parseInt(args[i + 1]);
                        i++;
                        break;
                    case "nb":
                        Runner.numberOfTask = Integer.parseInt(args[i + 1]);
                        i++;
                        break;
                    case "time":
                        Runner.numberOfSecondsToWait = Integer.parseInt(args[i + 1]);
                        i++;
                        break;
                    case "v":
                    case "verbose":
                        Runner.verbose = true;
                        break;
                    case "exp":
                        expFound = true;
                        break;
                    case "help":
                    default:
                        usage();
                }
            } else
                usage();
        }
    }

    public static void usage() {
        System.out.println("options available : ");
        System.out.println("\t-size <integer> specify the size of each task");
        System.out.println("\t-nb <integer> specify the number of task");
        System.out.println("\t-time <integer> specify the number of seconds to wait until timeout");
        System.out.println("\t-v or -verbose to active Runner verbose mode");
        System.out.println("\t-exp <exp> specify the exp. If no exp is specified, the program will run all of it.");
        System.out.println("\tvalues available for <exp> are : addOne addN, BoolCall, IntRnd, BoolRnd");
        System.out.println("\t-help display this help");
        System.exit(-1);
    }
}
